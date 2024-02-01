package ru.sber.quizgame.service

import mu.KLogging
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.sber.quizgame.dto.GameDto
import ru.sber.quizgame.dto.GameScoreDto
import ru.sber.quizgame.exception.BusinessException
import ru.sber.quizgame.persistence.entity.Game
import ru.sber.quizgame.persistence.entity.PlayerInGame
import ru.sber.quizgame.persistence.entity.User
import ru.sber.quizgame.persistence.repository.GameRepository
import ru.sber.quizgame.persistence.repository.PlayerInGameRepository
import java.util.*

@Service
class LobbyService(
        var gameRepository: GameRepository,
        var playerInGameRepository: PlayerInGameRepository
) {

    companion object : KLogging()

    /**
     * Создания новой игровой комнаты
     */
    fun createGame(captain: User, roomName: String): GameDto {
        val link = UUID.randomUUID().toString()
        val game = Game(
                link = link,
                name = roomName,
                player = captain,
                startDate = null,
                endDate = null
        )
        gameRepository.save(game)

        playerInGameRepository.save(PlayerInGame(player = captain, game = game))

        return GameDto(link, roomName, listOf(captain.username))
    }

    fun joinRoom(player: User, link: String): GameDto {
        val game = gameRepository.findByLink(link) ?: throw BusinessException("Room with link $link not found")

        if (playerInGameRepository.findByGameAndPlayer(game, player) != null) {
            logger.info { "User ${player.username} already in game ${game.name}" }
        } else {
            playerInGameRepository.save(PlayerInGame(player = player, game = game))
        }
        val playerNames = playerInGameRepository.findByGame(game)
                .map { it.player.username }

        return GameDto(game.link, game.name, playerNames)
    }

    /**
     * Проверка наличия пользоваталя в команде
     */
    fun isInRoom(player: User, link: String): Boolean {
        val game = gameRepository.findByLink(link) ?: throw BusinessException("Room with link $link not found")
        return playerInGameRepository.findByGameAndPlayer(game, player) != null
    }

    fun getAllScores(): List<GameScoreDto> {
        val games = gameRepository.findComplete(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "score")))

        return games.mapIndexed { index, game -> GameScoreDto(index + 1, game.name, game.endDate, game.score) }
    }

}