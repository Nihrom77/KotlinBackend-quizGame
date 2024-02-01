package ru.sber.quizgame.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.sber.quizgame.dto.UserDto
import ru.sber.quizgame.persistence.entity.User
import ru.sber.quizgame.persistence.repository.UserRepository
import ru.sber.quizgame.security.UserAuthorityGroup

@Service
class UserServiceImpl(
        val userRepository: UserRepository,
        val passwordEncoder: PasswordEncoder
) : UserService {

    override fun saveUser(userDto: UserDto): User? {
        val user = User(username = userDto.username,
                password = passwordEncoder.encode(userDto.password),
                userAuthorityGroup = UserAuthorityGroup.ROLE_USER,
                firstName = userDto.firstName,
                lastName = userDto.lastName)

        userRepository.save(user)
        return user
    }

    override fun findByUsername(userName: String): User? {
        return userRepository.findByUsername(userName)
    }

    override fun findAll(): List<User> {
        return userRepository.findAll()
    }
}