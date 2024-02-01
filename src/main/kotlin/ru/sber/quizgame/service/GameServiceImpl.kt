package ru.sber.quizgame.service

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.sber.quizgame.dto.AnswerDto
import ru.sber.quizgame.dto.QuestionDto
import ru.sber.quizgame.persistence.entity.Question
import ru.sber.quizgame.persistence.repository.GameRepository
import ru.sber.quizgame.persistence.repository.QuestionRepository
import java.time.LocalDateTime

@Transactional
@Service
class GameServiceImpl(
        val questionRepository: QuestionRepository,
        val gameRepository: GameRepository,
        val sessions: MutableMap<String, GameSession> = mutableMapOf()
) : GameService {

    override fun nextQuestion(answerDto: AnswerDto): QuestionDto {
        val link = answerDto.roomLink
        var session = sessions[link]

        session = if (session != null) {

            checkAnswer(session, answerDto)
            session
        } else {
            createSession(link)
        }


        if (session.currentQuestion < session.questions.size) {
            return QuestionDto(
                    text = "${session.currentQuestion + 1} / ${session.questions.size}: " + session.questions[session.currentQuestion].questionText,
                    answers = session.questions[session.currentQuestion].answerList.map { it.answerText })
        } else {
            //Последний вопрос
            gameRepository.findByLink(link)?.let {
                it.score = session.score
                it.endDate = LocalDateTime.now()
                gameRepository.save(it)
            }
            return QuestionDto(text = "Вы ответили правильно на ${session.score} из ${session.questions.size} вопросов.")
        }
    }

    private fun prepareQuestionsForGame(): List<Question> =
            questionRepository.findAll(Pageable.ofSize(10)).toList()


    private fun checkAnswer(session: GameSession, answerDto: AnswerDto) {
        if (session.questions[session.currentQuestion].correctAnswer?.answerText == answerDto.answer) {
            session.score++
        }
        session.currentQuestion++
    }

    private fun createSession(link: String): GameSession {
        val newSession = GameSession(prepareQuestionsForGame())
        sessions[link] = newSession
        gameRepository.findByLink(link)?.let {
            it.startDate = LocalDateTime.now()
            gameRepository.save(it)
        }
        return newSession
    }


}

class GameSession(
        var questions: List<Question>,
        var currentQuestion: Int = 0,
        var score: Int = 0
)