package com.example.FARMACIA.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/login", "/registro", 
				"/detalle-ventas", "/historial-stock", "/metodos-pago", "/productos",
				"/clientes","/proveedores","/usuarios","/ventas","/ventas/detalle",
				"/ventas/crear-con-detalles","/dashboard/totales",
				"/categorias","/css/**", "/js/**", "/img/**").permitAll()
				.anyRequest().authenticated()
			)
			.csrf().disable() // Deshabilitar CSRF para pruebas
			
			
			.logout(logout -> logout.permitAll());
		return http.build();
	}
	 @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                    .allowedOrigins("http://127.0.0.1:5500", "http://localhost:5500")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*");
            }
        };
    }
}
