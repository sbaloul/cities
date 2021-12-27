package com.codest.cities.security;

import java.util.Objects;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfigurerImp implements WebMvcConfigurer {

	@Configuration
	protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.inMemoryAuthentication()
					.passwordEncoder(new NoOpPasswordEncoder())
						.withUser("user").password("pass").roles("USER").and()
						.withUser("admin").password("pass").roles("USER", "ALLOW_EDIT");
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.csrf().disable()
					.formLogin()
					.successHandler(new LoginSuccessHandler())
				.and()
					.logout()
					.addLogoutHandler(new LogoutHandlerImpl())
				.and()
				.authorizeHttpRequests()
					.antMatchers(HttpMethod.PUT)
					.authenticated()
					.antMatchers("api/cities/*")
					.hasRole("ALLOW_EDIT")
				.and()
				.authorizeHttpRequests()
					.antMatchers((HttpMethod) null, "/login").permitAll()
				.and()
				.authorizeHttpRequests()
					.anyRequest()
					.permitAll();
		}
	}

	private static final class NoOpPasswordEncoder implements PasswordEncoder {

		@Override
		public String encode(CharSequence rawPassword) {
			return rawPassword == null ? null : rawPassword.toString();
		}

		@Override
		public boolean matches(CharSequence rawPassword, String encodedPassword) {
			return Objects.equals(rawPassword, encodedPassword);
		}

	}
}
