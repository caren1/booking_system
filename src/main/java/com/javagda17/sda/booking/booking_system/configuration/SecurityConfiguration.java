package com.javagda17.sda.booking.booking_system.configuration;

import com.javagda17.sda.booking.booking_system.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                // od tego momentu (niżej) zajmujemy się konfiguracją uprawnień do stron
                .authorizeRequests()
                .antMatchers(
                        "/",
                        "/css/**",
                        "/js/**",
                        "/webjars/**",
                        "/login").permitAll()
                .antMatchers("/admin/**",
                        "/register").hasRole("ADMIN")
                // << od antmatchers do permitall
                // zdefiniowałem strony (adresy url) które mają być dostępne dla każdego
                .and()
                // od tego miejsca(niżej) definiuję reguły dla stron które wymagają uprawnień
                // wszystko co nie należy do stron powyżej będzie wymagało logowania
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll();

    }

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private LoginService loginService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        daoAuthenticationProvider.setUserDetailsService(loginService);

        // tworzymy bean (instancję klasy) dao authentication provider
        // za chwilę powiemy spring'owi że ma skorzystać z tego bean'a kiedy podejmujemy próbę logowania
        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());

    }
}
