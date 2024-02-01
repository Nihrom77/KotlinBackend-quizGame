package ru.sber.quizgame.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import ru.sber.quizgame.dto.GameDto
import ru.sber.quizgame.dto.UserDto
import ru.sber.quizgame.service.LobbyService
import ru.sber.quizgame.service.UserService
import java.security.Principal
import javax.validation.Valid


@Controller
class AuthController(
        var userService: UserService,
        var lobbyService: LobbyService
) {

    /**
     * Главная страница
     * @param principal - Текущий пользователь
     */
    @GetMapping(value = ["/index", "/"])
    fun home(principal: Principal?, model: Model): String {
        if (principal != null) {

            model.addAttribute("username", principal.name)
            model.addAttribute("room", GameDto("", ""))
            model.addAttribute("allGameScores", lobbyService.getAllScores())
            return "main"
        } else {
            return "loginregister"
        }
    }

    /**
     * Страница авторизации
     */
    @GetMapping("login")
    fun login(): String {
        return "login"
    }

    /**
     * Страница регистрации
     */
    @GetMapping("/register")
    fun register(model: Model): String {
        val userDto = UserDto("", "", "", "")
        model.addAttribute("user", userDto)
        return "register"
    }

    /**
     * Обработчик действия регистрации
     */
    @PostMapping("/register/save")
    fun registerSave(@Valid @ModelAttribute("user") userDto: UserDto,
                     bindingResult: BindingResult,
                     model: Model): String {
        val existingUser = userService.findByUsername(userDto.username)
        if (existingUser != null) {
            //Уже существует пользователь с таким username
            bindingResult.rejectValue("username", "",
                    "There is already an account registered with the same username");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }
}