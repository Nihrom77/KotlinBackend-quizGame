package ru.sber.quizgame.persistence.entity

import javax.persistence.*

@Entity
@Table(name = "question_answer")
data class QuestionAnswer(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @ManyToOne
        @JoinColumn(name = "question_id", referencedColumnName = "id")
        var question: Question?,

        var answerText: String
)