/*package com.techlabs.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.techlabs.app.security.JwtAuthenticationEntryPoint;
import com.techlabs.app.security.JwtAuthenticationFilter;

@Configuration
//@EnableMethodSecurity
public class SecurityConfig {

	private JwtAuthenticationEntryPoint authenticationEntryPoint;

	private JwtAuthenticationFilter authenticationFilter;

	public SecurityConfig(JwtAuthenticationEntryPoint authenticationEntryPoint,
			JwtAuthenticationFilter authenticationFilter) {
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.authenticationFilter = authenticationFilter;
	}

	@Bean
	static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	@Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf((csrf) -> csrf.disable())
              .authorizeHttpRequests((authorize) ->
                      authorize
//                      
                      
//                      .requestMatchers("/api/auth/**").permitAll()
                      //.requestMatchers(("/api/contact-detail/**").permitAll()
                      //.anyRequest().permitAll()
                     // .anyRequest().authenticated()

//              ).exceptionHandling( exception -> exception
//                      .authenticationEntryPoint(authenticationEntryPoint)
//              ).sessionManagement( session -> session
//                      .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//              );
//
//      http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
                      
                      //.requestMatchers(("/api/contact-detail/**").permitAll()
                      .requestMatchers("/api/auth/**").permitAll().anyRequest().authenticated())
              .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
              .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

          http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

          
          
        


      return http.build();
  }

}*/

package com.techlabs.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.techlabs.app.security.JwtAuthenticationEntryPoint;
import com.techlabs.app.security.JwtAuthenticationFilter;

@Configuration
//@EnableMethodSecurity
public class SecurityConfig {

    private JwtAuthenticationEntryPoint authenticationEntryPoint;
    private JwtAuthenticationFilter authenticationFilter;

    public SecurityConfig(JwtAuthenticationEntryPoint authenticationEntryPoint,
                          JwtAuthenticationFilter authenticationFilter) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Disable CSRF
            .authorizeHttpRequests(authorize -> 
                authorize
                    .requestMatchers("/api/auth/**").permitAll()  // Allow public access to authentication endpoints
                    .requestMatchers(HttpMethod.GET, "/api/contacts/").hasAnyRole("STAFF", "ADMIN")  
                    .requestMatchers(HttpMethod.POST, "/api/contacts/").hasRole("ADMIN") 
                    .requestMatchers(HttpMethod.PUT, "/api/contacts/").hasRole("ADMIN")  
                    .requestMatchers(HttpMethod.DELETE, "/api/contacts/").hasRole("ADMIN")
                    
                    //users
                    
                    .requestMatchers(HttpMethod.POST,"/api/users").hasRole("STAFF")
                    .requestMatchers(HttpMethod.GET, "/api/users/").hasRole("STAFF")  
                    .requestMatchers(HttpMethod.GET, "/api/users/{id}").hasRole("STAFF")
                    .requestMatchers(HttpMethod.PUT, "/api/users/{id}").hasRole("STAFF")                    
                    .requestMatchers(HttpMethod.DELETE, "/api/users/{id}").hasRole("STAFF")
                    
                    //contactDetail   /api/contact-detail
                    
                    .requestMatchers("/api/contact-detail/").hasRole("STAFF")  
                    .requestMatchers(HttpMethod.GET, "/api/contact-detail/{contactId}/details").hasRole("STAFF")
                    .requestMatchers(HttpMethod.GET, "/api/contact-detail/{contactId}/details").hasRole("STAFF")
                    .requestMatchers(HttpMethod.PUT,"/api/contact-detail//details/{id}").hasRole("STAFF")
                     
                    .anyRequest().authenticated()  
            )
            .exceptionHandling(exception -> 
                exception
                    .authenticationEntryPoint(authenticationEntryPoint)  
            )
            .sessionManagement(session -> 
                session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)              );

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
