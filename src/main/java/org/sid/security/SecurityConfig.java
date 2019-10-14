package org.sid.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//les utilisateurs sont en memoire, l'utilisateur "admin" a deux roles : "user" et "admin"
		auth.inMemoryAuthentication().withUser("admin").roles("USER","ADMIN");
		auth.inMemoryAuthentication().withUser("user").roles("USER");
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {

	}
}
