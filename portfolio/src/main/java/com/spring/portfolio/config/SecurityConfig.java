package com.spring.portfolio.config;

import com.spring.portfolio.dto.Role;
import com.spring.portfolio.service.MemberDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인으로 등록됨
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
// securedEnabled => Secured 애노테이션 사용 여부, prePostEnabled => PreAuthorize 어노테이션 사용 여부.
// (권한 처리 어노테이션을 붙일 수 있게 할지 말지 권한 설정 = @Secure , @PreAuthorize, @PostAuthorize 사용하게)
@AllArgsConstructor
public class SecurityConfig {

    private MemberDetailsService memberDetailsService;
    private CustomLoginSuccessHandler customLoginSuccessHandler;
    private CustomLoginFailureHandler customLoginFailureHandler;
    
//    얘들 사용해보려고 했는데 일단은 없이도 되서 주석처리
//    private CustomAuthenticationProvider authProvider;
//
//    @Bean
//    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
//        AuthenticationManagerBuilder authenticationManagerBuilder =
//                http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.authenticationProvider(authProvider);
//        return authenticationManagerBuilder.build();
//    }

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(AntPathRequestMatcher.antMatcher("/assets/**"),
                        AntPathRequestMatcher.antMatcher("/images/**")
                );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.userDetailsService(memberDetailsService).passwordEncoder(passwordEncoder());

        http
                .csrf((csrfConfig) ->
                        csrfConfig.disable()
                )
                .headers((headerConfig) ->
                        headerConfig.frameOptions(frameOptionsConfig ->
                                frameOptionsConfig.disable()
                        )
                )
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers(PathRequest.toH2Console()).permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/board/write"),
                                        AntPathRequestMatcher.antMatcher("/board/writeAction"),
                                        AntPathRequestMatcher.antMatcher("/board/replyAction"))
                                .hasAnyRole(Role.USER.name(), Role.ADMIN.name(), Role.MASTER.name())
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/admins/**"))
                                .hasAnyRole(Role.ADMIN.name(), Role.MASTER.name())
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/login/**"),
                                        AntPathRequestMatcher.antMatcher("/"),
                                        AntPathRequestMatcher.antMatcher("/joinAction"),
                                        AntPathRequestMatcher.antMatcher("/loginAction"),
                                        AntPathRequestMatcher.antMatcher("/board/**"),
                                        AntPathRequestMatcher.antMatcher("/join/**"))
                                .permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin((formLogin) ->
                        formLogin
                                .loginPage("/login")
                                .usernameParameter("memberMail")
                                .passwordParameter("memberPw")
                                .loginProcessingUrl("/loginAction")
                                .defaultSuccessUrl("/")
                                .failureUrl("/login")
                                .successHandler(customLoginSuccessHandler)
                                .failureHandler(customLoginFailureHandler)
                )
                .userDetailsService(memberDetailsService)
                .logout((logoutConfig) ->
                        logoutConfig
                                .logoutRequestMatcher(AntPathRequestMatcher.antMatcher("/logoutAction"))
                                .deleteCookies("JSESSIONID")
                                .invalidateHttpSession(true)
                                .logoutSuccessUrl("/")
                );

        return http.build();
    }
}
