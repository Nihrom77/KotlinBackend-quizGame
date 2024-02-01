package ru.sber.quizgame.dto

data class QuestionDto(
        val text: String? = null,
        val answers: List<String> = emptyList(),
        val score: Int? = null
)