package ru.sber.quizgame.service

import ru.sber.quizgame.dto.UserDto
import ru.sber.quizgame.persistence.entity.User

interface UserService {

    fun saveUser(userDto: UserDto): User?

    fun findByUsername(userName: String): User?

    fun findAll(): List<User>

}