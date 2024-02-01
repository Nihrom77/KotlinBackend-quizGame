package ru.sber.quizgame

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class QuizGameApplication

fun main(args: Array<String>) {
	runApplication<QuizGameApplication>(*args)
}
