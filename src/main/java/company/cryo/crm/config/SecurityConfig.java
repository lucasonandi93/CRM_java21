package company.cryo.crm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity

public class SecurityConfig {

	//@Autowired // à utiliser dans UserService pour l'encodage du mdp
	//PasswordEncoder passwordEncoder;
	
	// Pour la vérification des mots de passe
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// Pour la récupération des informations de référence (table User)
	@Bean
	public UserDetailsService userDetailsService() {
		return new MonUserDetailsService();
	}
	
	// Pour l'identification
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setPasswordEncoder(passwordEncoder());
		authProvider.setUserDetailsService(userDetailsService());
		return authProvider;
	}
	
	// Construction de l'Authentication Manager à partir de notre AuthenticationProvider
	@Bean
	public AuthenticationManager authenticationManager(
			DaoAuthenticationProvider auth) {
		return new ProviderManager(auth);
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth ->
				auth
//					.requestMatchers("/deleteCustomer*/**").hasRole("ADMIN")
//					// .requestMatchers("/deleteCustomer*/**").hasAuthority("ROLE_ADMIN")
//					
//					.requestMatchers("/createCustomer*/**").hasAnyRole("ADMIN", "COMMERCIAL")
//					.requestMatchers("/updateCustomer*/**").hasAnyRole("ADMIN", "COMMERCIAL")
//					.requestMatchers("/listCustomer*/**").hasAnyRole("ADMIN", "COMMERCIAL")
//						// .hasAnyRole("ADMIN", "CONSEILLER")
					    //.hasAuthority("....")
					    //.hasAnyAuthority("....", "...")
					
					.requestMatchers("/login", "/styles.css", "/assets/image/logo.png", "/unauthorized").permitAll()
					
					// OBLIGATOIRE
					.anyRequest()
						.authenticated()
			)
			.exceptionHandling( exc -> exc.accessDeniedPage("/unauthorized"))
			.formLogin(form -> form
				.loginPage("/login")
				.permitAll()
				.defaultSuccessUrl("/")
			)
			//.formLogin(Customizer.withDefaults())
			//.formLogin((__) -> {})
			.logout(logout -> logout.invalidateHttpSession(true).logoutSuccessUrl("/login"));
		return http.build();
	}
	
}
