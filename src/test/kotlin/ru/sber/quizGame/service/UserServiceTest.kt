package ru.sber.quizGame.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.sber.quizGame.AbstractTestContainersIntegrationTest
import ru.sber.quizgame.dto.UserDto
import ru.sber.quizgame.persistence.repository.UserRepository
import ru.sber.quizgame.service.UserService
import javax.persistence.EntityNotFoundException

class UserServiceTest : AbstractTestContainersIntegrationTest() {

    @Autowired
    private lateinit var userService: UserService



    @Test
    fun saveUser() {
        val userDto = UserDto(username = "usr",
                firstName = "F",
                lastName = "L",
                password = "pass")
        userService.saveUser(userDto)

        val foundUser = userRepository.findByUsername(userDto.username) ?: throw EntityNotFoundException()
        assertEquals(userDto.username, foundUser.username)
        assertEquals(userDto.firstName, foundUser.firstName)
        assertEquals(userDto.lastName, foundUser.lastName)

        assertNotNull(foundUser.password)
        assertNotEquals(userDto.password, foundUser.password)//Пароль зашифрован
    }
}