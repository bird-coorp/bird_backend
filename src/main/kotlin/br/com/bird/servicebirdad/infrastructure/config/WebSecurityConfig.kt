package br.com.bird.servicebirdad.infrastructure.config

import br.com.bird.servicebirdad.infrastructure.adapter.controller.interceptor.ProfileInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@EnableWebMvc
@Configuration
class WebSecurityConfig(
    private val profileInterceptor: ProfileInterceptor
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        super.addInterceptors(registry)
        registry.addInterceptor(profileInterceptor)
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        super.addCorsMappings(registry)
        registry
            .addMapping("/**")
            .allowedHeaders("*")
            .allowedOrigins("*")
            .exposedHeaders(
                "Authorization",
                "companyId",
                "application",
                "Access-Control-Allow-Methods",
                "Access-Control-Allow-Headers",
                "Access-Control-Max-Age",
                "Access-Control-Request-Headers",
                "Access-Control-Request-Method"
            )
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
            .allowCredentials(false)
            .maxAge(3600)
    }
}