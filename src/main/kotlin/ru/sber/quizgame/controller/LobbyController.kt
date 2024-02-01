package ru.sber.quizgame.controller

import mu.KLogging
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.sber.quizgame.dto.GameDto
import ru.sber.quizgame.exception.BusinessException
import ru.sber.quizgame.persistence.entity.User
import ru.sber.quizgame.service.LobbyService
import ru.sber.quizgame.service.UserService
import java.security.Principal


@Controller
@RequestMapping("game")
class LobbyController(
        private val lobbyService: LobbyService,
        var userService: UserService
) {

    companion object : KLogging()


    @PostMapping("/createRoom")
    fun createRoom(principal: Principal, model: Model, @ModelAttribute("room") gameDto: GameDto): String {
        val updatedGameDto = lobbyService.createGame(currentUser(principal), gameDto.roomName ?: "Безымянные")
        model.addAttribute("game", updatedGameDto)
        return "redirect:/game/room?roomLink=${updatedGameDto.roomLink}"

    }

    @PostMapping("/joinRoom")
    fun joinRoom(principal: Principal, model: Model, @ModelAttribute("room") gameDto: GameDto): String {
        model.addAttribute("allGameScores", lobbyService.getAllScores())
        if (gameDto.roomLink == null) {
            return "redirect:/main?error"
        }
        return try {
            val updatedGameDto = lobbyService.joinRoom(currentUser(principal), gameDto.roomLink)
            model.addAttribute("game", updatedGameDto)

            "redirect:/game/room/?roomLink=${updatedGameDto.roomLink}"
        } catch (e: BusinessException) {
            "redirect:/main?error"
        }

    }

    @GetMapping("room")
    fun game(principal: Principal, model: Model, @RequestParam roomLink: String): String {
        model.addAttribute("username", principal.name)
        model.addAttribute("allGameScores", lobbyService.getAllScores())
        try {
            val updatedGameDto = lobbyService.joinRoom(currentUser(principal), roomLink)
            model.addAttribute("game", updatedGameDto)

        } catch (e: BusinessException) {
            return "redirect:/main?error"
        }

        return "game/room"
    }

    private fun currentUser(principal: Principal): User =
            userService.findByUsername(principal.name)
                    ?: throw UsernameNotFoundException("User ${principal.name} not found")
}