package ru.sber.quizGame

import mu.KLogging
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

class QuizGameApplicationTests: AbstractTestContainersIntegrationTest() {

	companion object : KLogging()

	@Test
	fun contextLoads() {
		logger.info { "Test contextLoads" }
	}

}
