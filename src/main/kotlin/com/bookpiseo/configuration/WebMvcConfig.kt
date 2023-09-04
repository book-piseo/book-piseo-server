package com.bookpiseo.configuration

import com.bookpiseo.interceptor.SessionInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig(private val sessionInterceptor: SessionInterceptor) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(sessionInterceptor)
                .addPathPatterns("/**") // 모든 요청에 대해 Interceptor 적용
                .excludePathPatterns("/api/login", "/api/home/**", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html"
                ) // 예외 URL 설정
    }
}