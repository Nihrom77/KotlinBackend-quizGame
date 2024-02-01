package ru.sber.quizGame

import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.request.RequestPostProcessor
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.containers.PostgreSQLContainer
import ru.sber.quizgame.QuizGameApplication
import ru.sber.quizgame.persistence.repository.*

@SpringBootTest(classes = [QuizGameApplication::class])
@EnableConfigurationProperties
abstract class AbstractTestContainersIntegrationTest {

    @Autowired
    protected lateinit var userRepository: UserRepository

    @Autowired
    protected lateinit var gameRepository: GameRepository

    @Autowired
    protected lateinit var questionRepository: QuestionRepository

    @Autowired
    protected lateinit var questionAnswerRepository: QuestionAnswerRepository

    @Autowired
    protected lateinit var playerInGameRepository: PlayerInGameRepository


    companion object {
        const val POSTGRES_IMAGE: String = "postgres:11-alpine"

        const val POSTGRES_USER: String = "user"
        const val POSTGRES_PASSWORD: String = "password"
        const val POSTGRES_IDENTITY_DB_NAME = "identity"


        private val identityContainer = createTestContainer(POSTGRES_IDENTITY_DB_NAME)

        class SpecifiedPostgreSQLContainer(image: String) : PostgreSQLContainer<SpecifiedPostgreSQLContainer>(image)

        private fun createTestContainer(databaseName: String) =
                SpecifiedPostgreSQLContainer(POSTGRES_IMAGE)
                        .withUsername(POSTGRES_USER)
                        .withPassword(POSTGRES_PASSWORD)
                        .withDatabaseName(databaseName)
                        .withPrivilegedMode(true)
                        .withCommand("postgres -N 2000")

        @DynamicPropertySource
        @JvmStatic
        fun registerPgProperties(registry: DynamicPropertyRegistry) {
            fillDataSourceProperty(registry, POSTGRES_IDENTITY_DB_NAME, identityContainer)

        }

        private fun fillDataSourceProperty(registry: DynamicPropertyRegistry, database: String, container: SpecifiedPostgreSQLContainer) {
            registry.add("spring.datasource.url") { container.jdbcUrl }
            registry.add("spring.datasource.username") { container.username }
            registry.add("spring.datasource.password") { container.password }
        }

        init {
            identityContainer.start()
        }
    }

    @Transactional
    @BeforeEach
    fun clearDb() {
        playerInGameRepository.deleteAll()
//        questionRepository.deleteAll()
//        questionAnswerRepository.deleteAll()
        gameRepository.deleteAll()
        userRepository.deleteAll()
    }

    /**
     * Тестовый spring-security пользователь
     */
    fun rob(username: String): RequestPostProcessor {
        return user(username).roles("USER")
    }


}