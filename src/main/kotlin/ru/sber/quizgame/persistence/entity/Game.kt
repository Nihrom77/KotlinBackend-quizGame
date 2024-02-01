package ru.sber.quizgame.persistence.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "game")
data class Game(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        var link: String,
        var name: String,

        @ManyToOne
        @JoinColumn(name = "captain_id", referencedColumnName = "id")
        var player: User,

        var startDate: LocalDateTime?,
        var endDate: LocalDateTime?,

        var score: Int = 0,

        @OneToMany(mappedBy = "game", cascade = [CascadeType.ALL], targetEntity = PlayerInGame::class, orphanRemoval = true)
        var playerList: MutableList<PlayerInGame> = mutableListOf()
)