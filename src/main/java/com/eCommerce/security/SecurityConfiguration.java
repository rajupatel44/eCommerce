package com.eCommerce.security;//package com.eCommerce.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
////import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.web.SecurityFilterChain;
//
////import static org.springframework.security.config.Customizer.withDefaults;
//
//@Configuration
//public class SecurityConfiguration {
//
////    @Bean
////    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////        http.authorizeHttpRequests((authz) -> authz.anyRequest().authenticated()).httpBasic(withDefaults());
////        return http.build();
////    }
////
////
////    @Bean
////    public WebSecurityCustomizer webSecurityCustomizer() {
////        return (web) -> web
////                .ignoring()
////
////                .antMatchers("/authenticate"); // Allow unauthenticated access to /autho
////    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests((authz) -> authz
//                        .antMatchers(HttpMethod.POST,"/authenticate").permitAll() // Allow unauthenticated access to /autho
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(Customizer.withDefaults());
//
//        return http.build();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                .build();
//    }
//
//
//
//}


import com.eCommerce.controller.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//@EnableWebSecurity
@Configuration
public class  SecurityConfiguration{

    @Autowired
    private UserDetailsService uds;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, UserController controller) throws Exception {

        http.authorizeRequests()
                .antMatchers("/authenticate").permitAll()
//                .anyRequest().authenticated()
//                .antMatchers("/admin").hasAuthority("Admin")
//                .antMatchers("/mgr").hasAuthority("Manager")
//                .antMatchers("/emp").hasAuthority("Employee")
//                .antMatchers("/hr").hasAuthority("HR")
//                .antMatchers("/common").hasAnyAuthority("Employeee,Manager,Admin")
                .anyRequest().authenticated()

//                .and()
//                .formLogin()
//                .defaultSuccessUrl("/welcome",true)

//                .and()
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

                .and()
                .exceptionHandling()
                .accessDeniedPage("/accessDenied")

                .and()
                .authenticationProvider(authenticationProvider(controller));

        return http.build();

    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserController userController) {
        return new CustomAuthenticationProvider(userController);
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String mahendra = bCryptPasswordEncoder.encode("Mahendra");
        System.out.println(mahendra);
        return bCryptPasswordEncoder;
    }

}
