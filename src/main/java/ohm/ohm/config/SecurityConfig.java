package ohm.ohm.config;


import lombok.RequiredArgsConstructor;
import ohm.ohm.jwt.JwtAccessDeniedHandler;
import ohm.ohm.jwt.JwtAuthenticationEntry;
import ohm.ohm.jwt.JwtSecurityConfig;
import ohm.ohm.jwt.TokenProvider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //@PreAuthorize어노테이션 사용을 위해 선언
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;

    //에러 반환 클래스들 선언후 주입
    private final JwtAuthenticationEntry jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(
            TokenProvider tokenProvider,
            JwtAuthenticationEntry jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }


    // BCryptPasswordEncoder 라는 패스워드 인코더 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //h2-console 하위 모든 요청들과 파비콘 관련 요청은 Spring Security 로직을ㅇ수행하지 않도록
    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(
                        "/h2/**"
                        , "/favicon.ico"
                        , "/error"
                );
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .exceptionHandling()
                //Exception을 핸들링할때 우리가 만들었던 클래스들을 추가해준다.
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .headers()
                .frameOptions()
                .sameOrigin()


                //세션을 사용하지 않음
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)


                .and()

                .authorizeRequests()
                .antMatchers(
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/v2/api-docs",
                        "/webjars/**").permitAll()
                .antMatchers("/api/gym/time/{gymId}","/api/gym/price/{gymId}","/api/gym/avg/{gymId}","/api/gym", "/api/gyms", "/api/gym/name/{gymName}", "/api/gym/{gymId}", "/api/gym/count/{gymId}","/api/gym/code/{code}").permitAll()
                .antMatchers("/api/gym/count-increase/{gymId}", "/api/gym/count-decrease/{gymId}").permitAll()
                .antMatchers( "/api/manager/code/{code}","/api/ceo/code/{code}","/api/manager/image/{managerId}","/api/manager","/api/ceo", "/api/manager/login", "/api/trainer/{gymId}", "/api/manager/findall/{gymId}", "/api/manager/{managerId}").permitAll()
                .antMatchers("/api/post/{gymId}", "/api/posts/{gymId}").permitAll()
                .antMatchers("/api/admin/findall/{gymId}","/api/admin/{managerId}","/api/admin/login", "/api/admin/image/{managerId}","/api/admin").permitAll()
                .antMatchers("/api/ceo/code/{code}", "/api/ceo").permitAll()
                .antMatchers("/api/question/{gymId}","/api/question/{questionId}","/api/question/all/{gymId}").permitAll()
                .anyRequest().authenticated() // 나머지 경로는 jwt 인증 해야함

                .and()
                .apply(new JwtSecurityConfig(tokenProvider));
    }


}
