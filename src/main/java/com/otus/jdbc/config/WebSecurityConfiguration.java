package com.otus.jdbc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/static/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .permitAll()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .permitAll();
    }

    @Bean
    @Autowired
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user1 =
                User.builder()
                        .username("user1")
                        .password(passwordEncoder.encode("password1"))
                        .roles("USER")
                        .build();

        UserDetails user2 =
                User.builder()
                        .username("user2")
                        .password(passwordEncoder.encode("password2"))
                        .roles("USER")
                        .build();

        return new MyInMemoryUserDetailsManager(user1, user2);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private class MyInMemoryUserDetailsManager implements UserDetailsService {
        Map<String, UserDetails> usersByName;

        public MyInMemoryUserDetailsManager(UserDetails... users) {
            usersByName = Arrays.stream(users).collect(Collectors.toMap(UserDetails::getUsername, u -> u));
        }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            return Optional.ofNullable(usersByName.get(username))
                    .map(u ->
                            User.builder()
                                    .username(u.getUsername())
                                    .password(u.getPassword())
                                    .authorities(u.getAuthorities())
                                    .build())
                    .orElseThrow(() -> new UsernameNotFoundException("not found"));
        }
    }
}
