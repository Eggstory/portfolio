package com.spring.portfolio.config;

import com.spring.portfolio.dto.Role;
import com.spring.portfolio.entity.Member;
import com.spring.portfolio.service.MemberDetailsService;
import com.spring.portfolio.store.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인으로 등록됨
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
// securedEnabled => Secured 애노테이션 사용 여부, prePostEnabled => PreAuthorize 어노테이션 사용 여부.
// (권한 처리 어노테이션을 붙일 수 있게 할지 말지 권한 설정 = @Secure , @PreAuthorize, @PostAuthorize 사용하게)
@AllArgsConstructor
public class SecurityConfig {

    final private MemberRepository memberRepository;
    private MemberDetailsService memberDetailsService;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(new AntPathRequestMatcher("/assets/**"),
                        new AntPathRequestMatcher("/images/**")
                );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

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
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/board/**"))
                                .hasAnyRole(Role.USER.name(), Role.ADMIN.name(), Role.MASTER.name())
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/admins/**"))
                                .hasAnyRole(Role.ADMIN.name(), Role.MASTER.name())
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/login/**"),
                                        AntPathRequestMatcher.antMatcher("/"),
                                        AntPathRequestMatcher.antMatcher("/joinAction"),
                                        AntPathRequestMatcher.antMatcher("/loginAction"),
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
                                .defaultSuccessUrl("/", true)
                                .successHandler((request, response, authentication) -> {
                                            if (!authentication.getAuthorities().toString().equals("[ROLE_USER]") && !authentication.getAuthorities().toString().equals("[ROLE_ADMIN]")) {
                                                response.sendRedirect("/denied");
                                            } else {
                                                response.sendRedirect("/main");
                                                List<Member> list = memberRepository.findByUserLoginId(request.getParameter("memberID"));
                                                Member entity = list.get(0);
                                                request.getSession().setAttribute("memberEntity", entity);
                                                request.getSession().setAttribute("memberMail", request.getParameter("memberMail"));
                                            }
                                        }
                                )
                                .failureUrl("/loginFrom?error")
                )
                .logout((logoutConfig) ->
                        logoutConfig
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logoutAction"))
                                .deleteCookies("JSESSIONID")
                                .invalidateHttpSession(true)
                                .logoutSuccessUrl("/")
                )
                .userDetailsService(memberDetailsService);

        return http.build();
    }


}
