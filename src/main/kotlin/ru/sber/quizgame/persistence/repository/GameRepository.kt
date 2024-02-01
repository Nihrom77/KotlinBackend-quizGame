package ru.sber.quizgame.persistence.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.sber.quizgame.persistence.entity.Game

interface GameRepository : JpaRepository<Game, Long> {

    fun findByLink(link: String): Game?

    @Query("select game from Game game where game.endDate is not null")
    fun findComplete(var1: Pageable): Page<Game>
}