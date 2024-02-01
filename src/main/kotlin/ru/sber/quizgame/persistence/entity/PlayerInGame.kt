package ru.sber.quizgame.persistence.entity

import javax.persistence.*

@Entity
@Table(name = "player_in_game")
data class PlayerInGame(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @ManyToOne
        @JoinColumn(name = "player_id", referencedColumnName = "id")
        var player: User,

        @ManyToOne
        @JoinColumn(name = "game_id", referencedColumnName = "id")
        var game: Game
)