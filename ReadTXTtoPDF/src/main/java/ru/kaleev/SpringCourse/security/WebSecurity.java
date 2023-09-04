//package ru.kaleev.SpringCourse.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.security.web.authentication.ui.DefaultLogoutPageGeneratingFilter;
//
//@Configuration
//@EnableWebSecurity(debug = true)
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class WebSecurity extends WebSecurityConfigurerAdapter {
//	@Autowired
//	CustomAuthentication customAuthentication;
//
//	@Bean
//	public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
//		AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity
//				.getSharedObject(AuthenticationManagerBuilder.class);
//		authenticationManagerBuilder.authenticationProvider(customAuthentication);
//		return authenticationManagerBuilder.build();
//	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//			http
//				.authorizeRequests()
//					.antMatchers(HttpMethod.POST, "/login/success")
//					.permitAll()
//					.antMatchers(HttpMethod.GET, "/login/success")
//					.authenticated()
//					.antMatchers("/login/**")
//					.permitAll()
//					.anyRequest()
//					.authenticated()
//			.and()
//				.formLogin()
//				.loginPage("/login/signin")
//			.and()
//				.logout()
//			    .logoutUrl("/login/logout")
//			    .logoutSuccessUrl("/login/signin")
//			    .deleteCookies("auth_code", "JSESSIONID")
//			    .invalidateHttpSession(true)
//			.and()
//                .exceptionHandling().accessDeniedHandler(myAccessDeniedHandler())
//			.and()
//				.csrf().disable();
//	   }
//
//	@Bean
//	public AccessDeniedHandler myAccessDeniedHandler() {
//		return new MyAccessDeniedHandler();
//	}
//
//}
