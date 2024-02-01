package ru.sber.quizgame.dto

import java.time.LocalDateTime

data class GameScoreDto(
        val position: Int?,
        val roomName: String?,
        val endDate: LocalDateTime?,
        val score: Int?
)