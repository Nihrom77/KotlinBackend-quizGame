package ru.sber.quizgame.persistence.entity

import javax.persistence.*

@Entity
@Table(name = "question")
data class Question(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        var questionText: String,

        var type: String,
        var topic: String,
        var imageId: String? = null,

        @ManyToOne(optional = true)
        @JoinColumn(name = "correct_answer_id", referencedColumnName = "id")
        var correctAnswer: QuestionAnswer?,

        @OneToMany(mappedBy = "question", fetch = FetchType.EAGER, cascade = [CascadeType.ALL], targetEntity = QuestionAnswer::class, orphanRemoval = true)
        var answerList: MutableList<QuestionAnswer> = mutableListOf()
)