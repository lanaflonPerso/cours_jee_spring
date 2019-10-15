package org.sid.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//les utilisateurs sont en memoire, l'utilisateur "admin" a deux roles : "user" et "admin"
		auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("1234")).roles("USER","ADMIN");
		auth.inMemoryAuthentication().withUser("user").password(passwordEncoder().encode("1234")).roles("USER");
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin();
		//une requete http dans l'url "/index" nécessaite une authetification avec un utilisateur avec un rôle "USER"
		http.authorizeRequests().antMatchers("/index").hasRole("USER");
		//une requete http dans l'url "/form","/save","/edit","/delete" nécessaite une authetification avec un utilisateur avec un rôle "ADMIN"
		http.authorizeRequests().antMatchers("/form","/save","/edit","/delete").hasRole("ADMIN");
		//acces non authorisé à la page
		http.exceptionHandling().accessDeniedPage("403");
	}
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
