package com.tesfai.sebtibeb.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.tesfai.sebtibeb.security.UserDetailsServiceImpl;

@EnableWebSecurity
public class SebtibebWebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		
		provider.setUserDetailsService(userDetailsService());
		provider.setPasswordEncoder(passwordEncoder());
		
		return provider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
			.antMatchers("/**/deletemember/**").hasAuthority("ROLE_ADMIN")
			.antMatchers("/**/editmember/**").hasAnyAuthority("ROLE_ADMIN","ROLE_EDITOR")
			.antMatchers("/public/**")
			.permitAll()
		.anyRequest()
			.authenticated()
		.and()
		.formLogin()
			.loginPage("/public/member/login")
			.usernameParameter("username")
			.passwordParameter("password")
			.loginProcessingUrl("/public/member/login")
			.defaultSuccessUrl("/login_success")
			.failureUrl("/login_error")			
			.successForwardUrl("/login_success_handler")
			.failureForwardUrl("/login_failure_handler")			
			.successHandler(new AuthenticationSuccessHandler() {			     
			    @Override
			    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			            Authentication authentication) throws IOException, ServletException {			         
			        System.out.println("Logged user: " + authentication.getName());			         
			        response.sendRedirect("/");
			    }
			})		
			.failureHandler(new AuthenticationFailureHandler() {			     
			    @Override
			    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			            AuthenticationException exception) throws IOException, ServletException {
			        System.out.println("Login failed");
			        System.out.println(exception);			         
			        response.sendRedirect("/login_error");
			    }
			})			
			.permitAll()
		.and()
		.logout()
			.invalidateHttpSession(true)
			.clearAuthentication(true)
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/public/member/login?logout")
			.permitAll();
	}

}
