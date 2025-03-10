package br.com.bird.servicebirdad.infrastructure.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.POST
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration

@Configuration
@EnableWebSecurity
class SecurityConfigurations(
    private val userDetailsService: UserDetailsService,
    private val securityFilter: SecurityFilter
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        val corsCustomizer: Customizer<CorsConfigurer<HttpSecurity>> =
            Customizer<CorsConfigurer<HttpSecurity>> { cors ->
                cors
                    .configurationSource {
                        val config = CorsConfiguration()
                        config.allowedOrigins = listOf("*")
                        config.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                        config.allowedHeaders = listOf("*")
                        config.exposedHeaders = listOf("Authorization")
                        config.maxAge = 3600L
                        config
                    }
            }

        http
            .cors(corsCustomizer)
            .csrf { it.disable() }
            .sessionManagement {
                it.sessionCreationPolicy(STATELESS)
            }
            .authorizeHttpRequests {
                it
                    .requestMatchers(GET, "/").permitAll()
                    .requestMatchers(POST, "/sessions").permitAll()

                    .requestMatchers(GET, "/totem").permitAll()

                    // Sign-up methods
                    .requestMatchers(POST, "/companies").permitAll()

                    .anyRequest().authenticated()
            }
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun authenticationManager(): AuthenticationManager {
        return ProviderManager(
            listOf(DaoAuthenticationProvider().apply {
                setUserDetailsService(userDetailsService)
                setPasswordEncoder(passwordEncoder())
            })
        )
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}