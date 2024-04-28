package in.bushansirgur.expensetrackerapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import in.bushansirgur.expensetrackerapi.security.CustomUserDetailsService;
import in.bushansirgur.expensetrackerapi.security.JwtRequestFilter;

@Configuration
public class WebSecurityConfig {
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	
	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		
//		http
//			.csrf().disable()
//			.authorizeRequests()
//			.antMatchers("/login", "/register").permitAll()
//			.anyRequest().authenticated()
//			.and()
//			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//		http.httpBasic();
//	
//	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//		http
//		.csrf().disable()
//		.authorizeHttpRequests()
//		.requestMatchers("/login", "/register").permitAll()
//		.anyRequest().authenticated()
//		.and()
//		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//		http.httpBasic();
//		return http.build();

		return http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth.requestMatchers("/login", "/register").permitAll().anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
				.httpBasic(Customizer.withDefaults())
				.build();

	}
	
	@Bean
	public JwtRequestFilter authenticationJwtTokenFilter() {
		return new JwtRequestFilter();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsService);
//	}
//	
//	
//	
//	@Bean
//	@Override
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//	
//		return super.authenticationManagerBean();
//	}
	
}


























