package ru.sber.quizgame.controller

import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sber.quizgame.dto.AnswerDto
import ru.sber.quizgame.dto.QuestionDto
import ru.sber.quizgame.exception.BusinessException
import ru.sber.quizgame.persistence.entity.User
import ru.sber.quizgame.service.GameService
import ru.sber.quizgame.service.LobbyService
import ru.sber.quizgame.service.UserService
import java.security.Principal

@RestController
@RequestMapping("game/questions")
class QuestionRestController(
        val gameService: GameService,
        val lobbyService: LobbyService,
        val userService: UserService
) {

    @PostMapping("next")
    fun nextQuestion(principal: Principal, @RequestBody answerDto: AnswerDto): QuestionDto {
        if (!lobbyService.isInRoom(currentUser(principal), answerDto.roomLink)) {
            throw BusinessException("User ${principal.name} is not in room ${answerDto.roomLink}")
        }

        return gameService.nextQuestion(answerDto)
    }

    private fun currentUser(principal: Principal): User =
            userService.findByUsername(principal.name)
                    ?: throw UsernameNotFoundException("User ${principal.name} not found")
}