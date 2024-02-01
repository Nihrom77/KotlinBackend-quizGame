package ru.sber.quizgame.persistence.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.sber.quizgame.persistence.entity.Question

interface QuestionRepository : JpaRepository<Question, Long> {
}