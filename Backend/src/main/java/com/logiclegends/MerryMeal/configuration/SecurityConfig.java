package com.logiclegends.MerryMeal.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.logiclegends.MerryMeal.JwtSecurity.JWTAuthorizationFilter;
import com.logiclegends.MerryMeal.service.JwtUtil;
import com.logiclegends.MerryMeal.service.RegistrationServiceImpl;

@Configuration
//use to enable configure class
@EnableWebSecurity
//use to enable Web Security
@EnableGlobalMethodSecurity(
    securedEnabled = true,
    //security for controller, service method
    jsr250Enabled = true,
    //@RolesAllowed annotation
    prePostEnabled = true
    //PreAuthorize or PostAuthorize
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private RegistrationServiceImpl regServiceImpl;
	
	@Autowired
	private JwtUtil jwtUtil;

	  @Override
	    @Bean
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	    }
	
	  //AuthenticationManagerBuidler - is used to create AuthenticationManager
	  //AuthenticationManagerBuilder - is used to build custom authentication, JDBC, etc
	  //In my project, want to authenticate usersServiceImpl and passwordEncoder
	  @Override
	  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
	      authenticationManagerBuilder
	              .userDetailsService(regServiceImpl)
	              .passwordEncoder(passwordEncoder());
	  }
	  
	
//
	  @Bean
	  public PasswordEncoder passwordEncoder() {
	      return new BCryptPasswordEncoder();
	  }
	  
	
	
	
	protected void configure(HttpSecurity http) throws Exception {
	      http
	              .cors()
	                  .and()
	              .sessionManagement()
	                  .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	                  .and()
	              .csrf()
	                  .disable()
	              .formLogin()
	                  .disable()
	              .httpBasic()
	                  .disable()                    
	              .authorizeRequests()
	                  .antMatchers("/",
	                      "/error",
	                      "/favicon.ico",
	                      "/**/*.png",
	                      "/**/*.gif",
	                      "/**/*.svg",
	                      "/**/*.jpg",
	                      "/**/*.html",
	                      "/**/*.css",
	                      "/**/*.js")
	                      .permitAll()
	                  .antMatchers("/logic/**")
	                      .permitAll()
	                  .anyRequest()
	                      .authenticated()
	                  .and()
//		                  .addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil))
		                  .addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, regServiceImpl));

//	                      .oauth2Login()
//	                          .authorizationEndpoint()
//	                              .baseUri("/oauth2/authorize")
//	                              .authorizationRequestRepository(cookieAuthorizationRequestRepo())
//	                              .and()
//	                          .redirectionEndpoint()
//	                              .baseUri("/oauth2/callback/*")
//	                              .and()
//	                          .userInfoEndpoint()
//	                              .userService(oAuthUsersServiceImpl)
//	                              .and()
//	                          .successHandler(authorizationSuccessHandler)
//	                          .failureHandler(authorizationFailureHandler);
	               

//	      // Add our custom Token based authentication filter
//	      http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//	  }
	}
}
