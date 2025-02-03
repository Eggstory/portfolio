package com.spring.portfolio.config;

import com.spring.portfolio.config.oauth.OAuth2SuccessHandler;
import com.spring.portfolio.config.oauth.OAuth2UserService;
import com.spring.portfolio.dto.Role;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인으로 등록됨
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
// securedEnabled => Secured 애노테이션 사용 여부, prePostEnabled => PreAuthorize 어노테이션 사용 여부.
// (권한 처리 어노테이션을 붙일 수 있게 할지 말지 권한 설정 = @Secure , @PreAuthorize, @PostAuthorize 사용하게)
@AllArgsConstructor
public class SecurityConfig {

    private final PrincipalDetailsService principalDetailsService;
    private final OAuth2UserService OAuth2UserService;
    private final CustomLoginSuccessHandler customLoginSuccessHandler;
    private final CustomLoginFailureHandler customLoginFailureHandler;
//    private final AuthenticationConfiguration authenticationConfiguration;

    private final OAuth2SuccessHandler oAuth2SuccessHandler;
//    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    
//    얘들 사용해보려고 했는데 일단은 없이도 되서 주석처리
//    private final CustomAuthenticationProvider customAuthenticationProvider;
//
//    @Bean
//    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
//        AuthenticationManagerBuilder authenticationManagerBuilder =
//                http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.authenticationProvider(authProvider);
//        return authenticationManagerBuilder.build();
//    }


//    // jwt 유튜브 영상
//    //
//    private final AuthenticationConfiguration authenticationConfiguration;
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
//
//        return  configuration.getAuthenticationManager();
//    }
//    //
//    // 여기까지


    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(
                        AntPathRequestMatcher.antMatcher("/assets/**"),
                        AntPathRequestMatcher.antMatcher("/images/**"),
                        AntPathRequestMatcher.antMatcher("/error/**"),
                        AntPathRequestMatcher.antMatcher("/denied"),
                        AntPathRequestMatcher.antMatcher("/favicon.ico")
                );
    }

    @Bean
    // SecurityFilterChain : 보안설정을 적용하는 곳  /  HttpSecurity : 그 설정이 되어 있는 곳  /  AbstractHttpConfigurer : 그걸 만드는 방법
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.userDetailsService(principalDetailsService).passwordEncoder(passwordEncoder());
//
//        // AuthenticationManager 생성
//        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
        
        // csrf와 cors에 쓰인 람다랑 AbstractHttpConfigurer 방식 두가지 중 아무거나 쓰면 될듯
        http    // csrf : cookie를 사용하지 않으면 꺼도됨 (cookie를 사용할 경우 httpOnly(XSS 방어), sameSite(CSRF 방어)로 방어해야 한다.)
                .csrf((csrfConfig) ->
                        csrfConfig.disable()
                )
                // cors : 프론트와 연결 시 따로 설정 필요
                .cors(AbstractHttpConfigurer::disable)
                /*
                // OAuth2 사용할때 logout과 fomrLogin 막을때 사용 // 저~~ 아래에 formLogin()코드와 logout()코드 지워야함 // 나머지는 잘 모르겠음
                .logout().disable() // 로그아웃 사용 X
                .formLogin().disable() // 폼 로그인 사용 X
                .httpBasic(AbstractHttpConfigurer::disable) // 기본 인증 로그인 비활성화
                .headers(c -> c.frameOptions(
                        FrameOptionsConfig::disable).disable()) // X-Frame-Options 비활성화
                .sessionManagement(c ->
                        c.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용하지 않음
                 */
                .headers((headerConfig) ->
                        headerConfig.frameOptions(frameOptionsConfig ->
                                frameOptionsConfig.disable()
                        )
                )
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers(PathRequest.toH2Console()).permitAll()
                                .requestMatchers(
                                        AntPathRequestMatcher.antMatcher("/board/write"),
                                        AntPathRequestMatcher.antMatcher("/board/writeAction"),
                                        AntPathRequestMatcher.antMatcher("/board/replyDelete"),
                                        AntPathRequestMatcher.antMatcher("/board/replyAction"),
                                        AntPathRequestMatcher.antMatcher("/myInfo"),
                                        AntPathRequestMatcher.antMatcher("/myInfo/editAction")
                                )
                                .hasAnyAuthority(Role.USER.name(), Role.ADMIN.name(), Role.MASTER.name())
                                .requestMatchers(
                                        AntPathRequestMatcher.antMatcher("/admins/**")
                                )
                                .hasAnyAuthority(Role.ADMIN.name(), Role.MASTER.name())
                                .requestMatchers(
                                        AntPathRequestMatcher.antMatcher("/login/**"),
                                        AntPathRequestMatcher.antMatcher("/join"),
                                        AntPathRequestMatcher.antMatcher("/"),
                                        AntPathRequestMatcher.antMatcher("/joinAction"),
                                        AntPathRequestMatcher.antMatcher("/loginAction"),
                                        AntPathRequestMatcher.antMatcher("/board/**"),
                                        AntPathRequestMatcher.antMatcher("/board/**"),
                                        AntPathRequestMatcher.antMatcher("/findId"),
                                        AntPathRequestMatcher.antMatcher("/findPw")
                                )
                                .permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin((formLogin) ->
                        formLogin
                                .loginPage("/login")
                                .usernameParameter("memberMail")
                                .passwordParameter("memberPw")
                                .loginProcessingUrl("/loginAction")
//                                .defaultSuccessUrl("/")   //이게 여기있으면 아래꺼 못탐
                                .failureUrl("/login")
                                .successHandler(customLoginSuccessHandler)
                                .failureHandler(customLoginFailureHandler)
                )
                .userDetailsService(principalDetailsService)

                .logout((logoutConfig) ->
                        logoutConfig
                                .logoutRequestMatcher(AntPathRequestMatcher.antMatcher("/logoutAction"))
                                .deleteCookies("JSESSIONID")
                                .invalidateHttpSession(true)
                                .logoutSuccessUrl("/")
                )
                .oauth2Login((oauth2) -> oauth2 // OAuth2 로그인 구성을 위한 설정을 가져옴
                        .loginPage("/oauth2/authorization/google") // 권한 접근 실패 시 로그인 페이지로 이동
//                        .defaultSuccessUrl("http://localhost:8090") // 로그인 성공 시 이동할 페이지
                        .failureUrl("/oauth2/authorization/google") // 로그인 실패 시 이동 페이지
                        // 사용자 정보 엔드포인트에 대한 설정 추가 // 사용자 서비스 구성(로그인 성공 후 사용자 정보 처리)
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint.userService(OAuth2UserService))
                        .successHandler(oAuth2SuccessHandler)
                )

                // jwt 관련 설정
//                .addFilterBefore(tokenAuthenticationFilter,
//                        UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(new TokenExceptionFilter(), tokenAuthenticationFilter.getClass()) // 토큰 예외 핸들링
        
// 위랑 별개 코드임
//                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration)), UsernamePasswordAuthenticationFilter.class);

        
                // 인증 예외 핸들링
//                .exceptionHandling((exceptions) -> exceptions
//                        .authenticationEntryPoint(new CustomeAuthenticationEntryPoint())
//                        .accessDeniedHandler(new CustomAccessDeniedHandler()))
                        ;

        return http.build();
    }


//    protected void configure(AuthenticationManagerBuilder auth) throws  Exception {
//        auth.authenticationProvider(authenticationProvider());
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        return new CustomAuthenticationProvider();
//    }


//    @Bean
//    public AuthenticationManager authenticationManager() throws Exception {
//        ProviderManager providerManager = (ProviderManager) authenticationConfiguration.getAuthenticationManager();
//        providerManager.getProviders().add(this.customAuthenticationProvider);
//        return authenticationConfiguration.getAuthenticationManager();
//    }


//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }

    // 프론트랑 백엔드 포트가 다를때 cors 허용해주는거
    /*
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:3000")  // 프론트엔드 URL
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowCredentials(true);
            }
        };
    }

     */



}
