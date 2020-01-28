package com.estn.economy.core

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.provisioning.InMemoryUserDetailsManager

/**
 * Written by estn on 22.01.2020.
 */
@Configuration
class WebSecurityConfiguration : WebSecurityConfigurerAdapter() {

    private val passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    private val ACTUATOR_ROLE = "ACTUATOR"

    @Bean // authentication - who is who
    fun userDetailsManager(): UserDetailsService {
        val user = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("password"))
                .roles(ACTUATOR_ROLE)
                .build()

        return InMemoryUserDetailsManager(user)
    }

    // authorization who has access to what
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .mvcMatchers("/actuator/**").hasRole(ACTUATOR_ROLE)
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .and()
                .httpBasic()
    }
}