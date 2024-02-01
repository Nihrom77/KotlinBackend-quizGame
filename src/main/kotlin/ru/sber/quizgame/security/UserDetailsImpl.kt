package ru.sber.quizgame.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import ru.sber.quizgame.persistence.entity.User

class UserDetailsImpl : UserDetails {

    private val authority: GrantedAuthority
    private val user: User

    constructor(user: User) {
        this.user = user
        authority = SimpleGrantedAuthority(user.userAuthorityGroup.name)
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf(authority)

    override fun getPassword(): String = user.password

    override fun getUsername(): String = user.username

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = !user.locked

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}