package com.museum.ticketsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/auth/**", "/api/orders/create", "/api/order/view/**","/refund.html","/register.html", "/login.book.html", "/orders.html", "/css/**", "/js/**").permitAll() // 确保这些路径允许匿名访问
                        .anyRequest().authenticated() // 其他请求需要认证
                )
                .formLogin(form -> form
                        .loginPage("/login.book.html") // 自定义登录页面
                        .defaultSuccessUrl("/orders.html", true) // 登录成功后重定向
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login.book.html") // 退出后重定向到登录页面
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable()); // 禁用 CSRF 保护

        return http.build();
    }

}
