package br.com.dastec.gerenciasalao.config

import br.com.dastec.gerenciasalao.models.enums.Role
import br.com.dastec.gerenciasalao.repositories.UserRepository
import br.com.dastec.gerenciasalao.security.AuthenticationFilter
import br.com.dastec.gerenciasalao.security.AuthorizationFilter
import br.com.dastec.gerenciasalao.security.CustomAuthenticationEntryPoint
import br.com.dastec.gerenciasalao.security.JwtUtil
import br.com.dastec.gerenciasalao.services.UserDetailsUserModelService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val userRepository: UserRepository,
    private val userDetailsUserModelService: UserDetailsUserModelService,
    private val jwtUtil: JwtUtil,
    private val customEntryPoint: CustomAuthenticationEntryPoint
):  WebSecurityConfigurerAdapter(){

    private val PUBLIC_POST_MATCHERS = arrayOf<String>(
    )

    private val ADMIN_MATCHERS = arrayOf<String>(
        "/api/v1/admin/"
    )

    private val PUBLIC_MATCHERS = arrayOf<String>(
        "/api/v1/salon",
        "/api/v1/user",

    )

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth
            .userDetailsService(userDetailsUserModelService)
            .passwordEncoder(bCryptPasswordEncoder())
    }

    override fun configure(http: HttpSecurity) {
        http.cors().and().csrf().disable()
        http.authorizeHttpRequests()
            .antMatchers(*PUBLIC_MATCHERS).permitAll()
            .antMatchers(HttpMethod.POST, *PUBLIC_POST_MATCHERS).permitAll()
            .antMatchers(*ADMIN_MATCHERS).hasAnyAuthority(Role.ADMIN.description)
            .anyRequest().authenticated()
        http.addFilter(AuthenticationFilter(authenticationManager(), userRepository, jwtUtil))
        http.addFilter(AuthorizationFilter(authenticationManager(), userDetailsUserModelService, jwtUtil))
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.exceptionHandling().authenticationEntryPoint(customEntryPoint)
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder{
        return BCryptPasswordEncoder()
    }
}