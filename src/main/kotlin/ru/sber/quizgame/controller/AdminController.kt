package ru.sber.quizgame.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import ru.sber.quizgame.dto.UserDto
import ru.sber.quizgame.service.UserService

@Controller
class AdminController(
        var userService: UserService
) {

    /**
     * Вывести список всех пользователей
     */
    @GetMapping("admin/users")
    fun users(model: Model): String {
        val userDtoList = userService.findAll()
                .map { UserDto(it.username, "", it.firstName, it.lastName) }
        model.addAttribute("users", userDtoList)
        return "users"
    }
}