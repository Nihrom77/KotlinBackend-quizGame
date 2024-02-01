package ru.sber.quizgame.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.AnyRequestMatcher

/**
 * Примечание: Наследование от WebSecurityConfigurerAdapter - deprecated способ
 */
@Configuration
@EnableWebSecurity
class SpringSecurityConfig(
        var userDetailsService: UserDetailsService
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {

        /*
        hasRole("ADMIN") - текст не должен начинаться с "ROLE_"
        hasAuthority("ROLE_ADMIN")
         */

        return http.csrf().disable()
                .authorizeRequests {
                    it.requestMatchers(AntPathRequestMatcher("/register/**")).permitAll()
                            .requestMatchers(AntPathRequestMatcher("/index")).permitAll()
                            .requestMatchers(AntPathRequestMatcher("/")).permitAll()
                            .requestMatchers(AntPathRequestMatcher("/admin/users")).hasAuthority(UserAuthorityGroup.ROLE_ADMIN.name)
                            .requestMatchers(AntPathRequestMatcher("/game/**")).authenticated()
                            .requestMatchers(AnyRequestMatcher.INSTANCE).denyAll()
                }
                .formLogin {
                    it.loginPage("/login")
                            .loginProcessingUrl("/login")
                            .defaultSuccessUrl("/")//При успешном логине, будет редирект на эту страницу
                            .permitAll()
                }
                .logout {
                    it.logoutRequestMatcher(AntPathRequestMatcher("/logout")).permitAll()
                }
                .build()
    }

    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
    }

}