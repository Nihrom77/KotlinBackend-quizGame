package ru.sber.quizGame.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.sber.quizGame.AbstractTestContainersIntegrationTest
import ru.sber.quizgame.dto.AnswerDto
import ru.sber.quizgame.dto.UserDto
import ru.sber.quizgame.service.GameService
import ru.sber.quizgame.service.LobbyService
import ru.sber.quizgame.service.UserService

class GameServiceTest : AbstractTestContainersIntegrationTest() {

    @Autowired
    private lateinit var gameService: GameService

    @Autowired
    private lateinit var lobbyService: LobbyService

    @Autowired
    private lateinit var userService: UserService


    @Test
    fun getNextQuestion() {

        val user1 = userService.saveUser(UserDto("usr1", "pass", "F", "L"))!!
        val user2 = userService.saveUser(UserDto("usr2", "pass", "F", "L"))!!


        val gameDto = lobbyService.createGame(user1, "Команда 5")
        lobbyService.joinRoom(user2, gameDto.roomLink!!)

        var game = gameRepository.findByLink(gameDto.roomLink!!)
        assertEquals(gameDto.roomName, game?.name)
        assertEquals(gameDto.roomLink, game?.link)
        assertNull(game?.startDate)//Игра еще не началась
        assertNull(game?.endDate)// Игра еще не закончилась

        //Начинаем игру
        var responseQuestion = gameService.nextQuestion(AnswerDto(gameDto.roomLink!!, null))
        assertNotNull(responseQuestion.text)


        game = gameRepository.findByLink(gameDto.roomLink!!)
        assertNotNull(game?.startDate)//Игра еще не началась
        assertNull(game?.endDate)

        while (responseQuestion.answers.isNotEmpty()) {
            responseQuestion = gameService.nextQuestion(AnswerDto(gameDto.roomLink!!, responseQuestion.answers[0]))
        }

        //Игра завершилась
        game = gameRepository.findByLink(gameDto.roomLink!!)
        assertNotNull(game?.endDate)

    }
}