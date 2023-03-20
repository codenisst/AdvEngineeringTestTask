package ru.codenisst.AdvEngineeringTestTask.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.codenisst.AdvEngineeringTestTask.dao.repositories.UserRepo;
import ru.codenisst.AdvEngineeringTestTask.security.UserDetail;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public PasswordEncoder noOpPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepo userRepo) {
        return login -> {
            UserDetail userDetail = new UserDetail(userRepo.findByLogin(login));
            if (userDetail.getUser() != null) return userDetail;

            throw new UsernameNotFoundException("User '" + login + "' not found");
        };
    }
}
