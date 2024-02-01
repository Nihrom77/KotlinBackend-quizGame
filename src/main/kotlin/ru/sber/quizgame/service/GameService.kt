package ru.sber.quizgame.service

import ru.sber.quizgame.dto.AnswerDto
import ru.sber.quizgame.dto.QuestionDto

interface GameService {

    fun nextQuestion(answerDto: AnswerDto): QuestionDto
}