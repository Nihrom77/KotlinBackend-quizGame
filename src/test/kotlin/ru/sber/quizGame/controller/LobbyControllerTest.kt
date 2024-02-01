package ru.sber.quizGame.controller

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.HttpMethod
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import ru.sber.quizGame.AbstractTestContainersIntegrationTest
import ru.sber.quizgame.dto.UserDto
import ru.sber.quizgame.service.LobbyService
import ru.sber.quizgame.service.UserService

@AutoConfigureMockMvc
class LobbyControllerTest : AbstractTestContainersIntegrationTest() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var lobbyService: LobbyService

    @Autowired
    private lateinit var userService: UserService

    @Test
    fun getLobby() {
        val user = userService.saveUser(UserDto("usr", "pass", "firstName", "lastName")) ?: throw RuntimeException("")
        val user2 = userService.saveUser(UserDto("usr2", "pass", "firstName", "lastName")) ?: throw RuntimeException("")
        val dto = lobbyService.createGame(user, "Команда 7")
        lobbyService.joinRoom(user2, dto.roomLink!!)

        val reqBuilder = MockMvcRequestBuilders.request(HttpMethod.GET, "/game/room")
                .param("roomLink", dto.roomLink!!)
                .with(user(user.username).roles("USER"))

        val result = mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
                .andReturn()
                .response
                .contentAsString

        assertNotNull(result)

        assertTrue(result.startsWith("<!DOCTYPE html>"))
        assertTrue(result.contains(dto.roomName!!))
        assertTrue(result.contains("<td>usr</td>"))//Список участников
        assertTrue(result.contains("<td>usr2</td>"))//Список участников
    }
}