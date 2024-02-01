package ru.sber.quizgame.persistence.entity

import ru.sber.quizgame.security.UserAuthorityGroup
import javax.persistence.*

@Entity
@Table(name = "person")//Таблица 'user' - системная в postgres
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        var username: String,
        var password: String,

        var firstName: String,
        var lastName: String,

        var locked: Boolean = false,

        @Enumerated(value = EnumType.STRING)
        var userAuthorityGroup: UserAuthorityGroup = UserAuthorityGroup.ROLE_USER
)