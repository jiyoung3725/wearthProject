package com.example.demo.security;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.repository.UserJpaRepository;
import com.example.demo.service.UserInfoService;
import com.example.demo.vo.UsersVO;
import com.nimbusds.openid.connect.sdk.claims.UserInfo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Setter;

@Configuration
@EnableWebSecurity
@Setter
public class SecurityConfig {
	
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private OAuth2UserService oauth2UserService;

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring().requestMatchers("/css/**", "/js/**", "/php/**", "/images/**");
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.httpBasic().disable();
		
		http.csrf().disable();

		http.authorizeRequests().requestMatchers("/adminDashBoard").hasRole("ADMIN")
				.requestMatchers("/**").permitAll()
				.requestMatchers("/mypage/**").hasRole("USER")
				.anyRequest().authenticated()
				.and()
				.formLogin().loginPage("/userinfo/login").loginProcessingUrl("/userinfo/login").permitAll()
				.successHandler(successHandler()).failureHandler(failureHandler()).and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/userinfo/logout")).invalidateHttpSession(true)
				.logoutSuccessUrl("/").and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				.and()
				.oauth2Login()
				.successHandler(new AuthenticationSuccessHandler() {
					
					@Override
					public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
							Authentication authentication) throws IOException, ServletException {
						System.out.println("kakao success handler");
						try {
							System.out.println("id : "+authentication.getName());
							String id = "kakao@"+authentication.getName();
							UsersVO u = userInfoService.findById(id).get();
							u.setPwd(null);
							System.out.println(id + " 로그인 진행중 : " + u);
							HttpSession session = request.getSession();
							session.setAttribute("u", u);
							response.sendRedirect("/");
						} catch (Exception e) {
							System.out.println("error : "+e.getMessage());
						}
					
						
					}
				})	
				.failureHandler(failureHandler())
				.userInfoEndpoint().userService(oauth2UserService);

		
		return http.getOrBuild();
	}

	@Bean
	public AuthenticationSuccessHandler successHandler() {
		return new CustomAuthenticationSuccessHandler();
	}

	@Bean
	public AuthenticationFailureHandler failureHandler() {
		return new CustomAuthenticationFailureHandler();
	}

	private class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

		@Override
		public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
				Authentication authentication) throws IOException, ServletException {
			String id = authentication.getName();
			UsersVO u = userInfoService.findById(id).get();
			u.setPwd(null);
			System.out.println(id + " 로그인 진행중 : " + u);
			HttpSession session = request.getSession();
			session.setAttribute("u", u);
			
			if(u.getRoleKey()=="USER") {
				response.sendRedirect("/");				
			} else {
				response.sendRedirect("/adminDashBoard");
			}
			
		}
	}

	private class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
		@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException exception) throws IOException, ServletException {
			String id = request.getParameter("username");
			String error = getError(exception);
			System.out.println("error : " + error);
			request.setAttribute("error", error);
			response.sendRedirect("/userinfo/login");

		}
	}

	private String getError(AuthenticationException exception) {
		String error;
		if (exception instanceof UsernameNotFoundException) {
			error = "존재하지 않는 아이디입니다.";
		} else if (exception instanceof DisabledException) {
			error = "계정이 비활성화되었습니다.";
		} else if (exception instanceof LockedException) {
			error = "계정이 잠겼습니다. 잠시 후 다시 시도해주세요.";
		} else if (exception instanceof BadCredentialsException) {
			error = "잘못된 비밀번호입니다.";
		} else {
			error = "로그인에 실패했습니다. 다시 시도해주세요.";
		}

		return error;

	}

}
