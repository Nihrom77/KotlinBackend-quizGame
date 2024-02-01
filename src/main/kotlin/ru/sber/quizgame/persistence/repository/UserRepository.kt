package ru.sber.quizgame.persistence.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.sber.quizgame.persistence.entity.User

interface UserRepository : JpaRepository<User, Long> {

    fun findByUsername(username: String): User?
}