package ru.sber.quizgame.dto

data class GameDto(
        val roomLink: String?,
        val roomName: String?,
        val players: List<String> = emptyList()
)