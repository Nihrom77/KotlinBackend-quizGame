package ru.sber.quizgame.persistence.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.sber.quizgame.persistence.entity.Game
import ru.sber.quizgame.persistence.entity.PlayerInGame
import ru.sber.quizgame.persistence.entity.User

interface PlayerInGameRepository : JpaRepository<PlayerInGame, Long> {
    fun findByGameAndPlayer(game: Game, player: User): PlayerInGame?
    fun findByGame(game: Game): List<PlayerInGame>
}