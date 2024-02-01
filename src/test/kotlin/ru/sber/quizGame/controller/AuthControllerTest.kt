package ru.sber.quizGame.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `unauthorized GET request`() {
        val reqBuilder = MockMvcRequestBuilders.request(HttpMethod.GET, "/admin/users")

        mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"))
    }

    @WithMockUser(username = "some_user", password = "some_password", roles = ["USER"])
    @Test
    fun `authorized GET request forbidden`() {
        val reqBuilder = MockMvcRequestBuilders.request(HttpMethod.GET, "/admin/users")

        mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().`is`(HttpStatus.FORBIDDEN.value()))
    }

    @WithMockUser(username = "some_user", password = "some_password", roles = ["ADMIN"])
    @Test
    fun `authorized GET admin-request success`() {
        val reqBuilder = MockMvcRequestBuilders.request(HttpMethod.GET, "/admin/users")
        mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
    }

}