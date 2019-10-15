package org.sid.security;

import javax.activation.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	/*@Autowired
	private DataSource dataSource;*/
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//les utilisateurs sont en memoire, l'utilisateur "admin" a deux roles : "user" et "admin"
		auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("1234")).roles("USER","ADMIN");
		auth.inMemoryAuthentication().withUser("user").password(passwordEncoder().encode("1234")).roles("USER");
		
		/*//quelle source utiliser et où aller chercher les users
		auth.jdbcAuthentication().dataSource((javax.sql.DataSource) dataSource)
		.usersByUsernameQuery("select login as principal, pass as credentials,active from users where login=?")
		.authoritiesByUsernameQuery("select login as principal,role as role from users_roles where login=?")
        .passwordEncoder(passwordEncoder())
        .rolePrefix("ROLE_");*/
		
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.formLogin().loginPage("/login");
		//desactiver le cross site request forgery
		http.csrf().disable();
		/*une requete http dans l'url "/index" nécessaite une authetification avec un utilisateur avec un rôle "USER"
		http.authorizeRequests().antMatchers("/index").hasRole("USER");
		//une requete http dans l'url "/form","/save","/edit","/delete" nécessaite une authetification avec un utilisateur avec un rôle "ADMIN"
		http.authorizeRequests().antMatchers("/form","/save","/edit","/delete").hasRole("ADMIN");
		//acces non authorisé à la page
		http.exceptionHandling().accessDeniedPage("/403");*/
			
		//gestion de plusieurs utilisateurs
		//necessite le rôle "user"
		http.authorizeRequests().antMatchers("/user/*").hasRole("USER");	
		//necessite le rôle "admin"
		http.authorizeRequests().antMatchers("/admin/*").hasRole("ADMIN");
		//acces non authorisé à la page
		http.exceptionHandling().accessDeniedPage("/403");
		

		
		
	}
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
