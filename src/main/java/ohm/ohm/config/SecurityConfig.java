//package ohm.ohm.config;
//
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@RequiredArgsConstructor
//@EnableWebSecurity
//public class SecurityConfig {
//
//    private final UserDetailsService userDetailsService;
//
//    @Bean
//    public BCryptPasswordEncoder encodePwe(){
//        return new BCryptPasswordEncoder();
//    }
//
//
////    @Bean
////    protected SecurityFilterChain configure(HttpSecurity http) throws Exception{
////        http.csrf().disable();
////        http.logout()
////                .logoutUrl("/logout");
////        http.authorizeRequests();
////    }
//}
