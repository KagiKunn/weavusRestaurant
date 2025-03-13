package com.example.project02.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final SecurityUserService securityUserService;

	public SecurityConfig(SecurityUserService securityUserService) {
		this.securityUserService = securityUserService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		System.out.println("!!!!securityfilter!!!!");
		http
				.authenticationManager(authenticationManager())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/", "/signin", "/signup", "/css/**", "/js/**", "/idcheck", "/logout", "/main").permitAll() // 로그인 페이지, 정적 리소스 허용
						.anyRequest().authenticated() // 그 외 요청은 로그인 필요
				)
				.formLogin(login -> login
						.loginPage("/signin") // 로그인 페이지 지정
						.loginProcessingUrl("/signin")
						.usernameParameter("id")
						.defaultSuccessUrl("/main", true) // 로그인 성공 후 이동할 페이지
						.permitAll()
				)
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/signin")
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID", "XSRF-TOKEN")
						.permitAll()
				)
				.csrf(csrf -> csrf
						.ignoringRequestMatchers("/idcheck", "/signup", "/main") // 특정 URL에 대해 CSRF 보호 비활성화
				)
				.exceptionHandling(exception -> exception
						.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/signin"))
				);

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		System.out.println("!!!!passwordEncoder!!!!");
		return new BCryptPasswordEncoder();  // BCryptPasswordEncoder를 사용하여 비밀번호 암호화
	}
	@Bean
	public UserDetailsService userDetailsService() {
		System.out.println("!!!!userDetailsService!!!!");
		return securityUserService;
	}

	@Bean
	AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(securityUserService);
		provider.setPasswordEncoder(passwordEncoder());
		return new ProviderManager(provider);
	}
}
