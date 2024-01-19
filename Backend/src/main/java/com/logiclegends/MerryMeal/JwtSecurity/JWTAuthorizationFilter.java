package com.logiclegends.MerryMeal.JwtSecurity;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.logiclegends.MerryMeal.service.JwtUtil;
import com.logiclegends.MerryMeal.service.RegistrationServiceImpl;

import antlr.StringUtils;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	
	

    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    
    
    private final JwtUtil jwtUtil;
    private final RegistrationServiceImpl userDetailsService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil, RegistrationServiceImpl userDetailsService) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
    	
        String header = request.getHeader(HEADER_STRING);
        System.out.println("Hello I'm doFilderInternal. " +header);

//        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
//        	System.out.println("Before doFilder");
//            chain.doFilter(request, response);
//            
//        	System.out.println("After doFilder");
//
//        }
        System.out.println("Hello Reading Bearer");
        
            try {
//				header=header.substring(7);
				if(org.springframework.util.StringUtils.hasText(header)) {
					if(jwtUtil.validateToken(header.substring(7))){
					String email = jwtUtil.extractEmail(header.substring(7));
					System.out.println(email);
					UserDetails userDetails = userDetailsService.loadUserByUsername(email);
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//    					getAuthentication(request);
					
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					System.out.println(authentication.getName()+ "line 50");
					SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				}
			} catch (UsernameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
		System.out.println("line 74 in jwtauth");
        chain.doFilter(request, response);
    }

//    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
//        
//    	String token;
//		try {
//			token = request.getHeader(HEADER_STRING);
//			System.out.println(token+" in usernamePasswordAuthenticaitonToken in jwtauthorization first");
//			if (token != null) {
//				System.out.println(token +"in usernamePasswordAuthenticaitonToken in jwtauthorization");
//	        	token=token.substring(7);
//	        	
//	            String email = jwtUtil.extractEmail(token); // Assuming the method name remains extractUsername in JwtUtil even if it extracts email
//	            
//	            if (email != null) {
//	                UserDetails userDetails = userDetailsService.loadUserByUsername(email); // Assuming the method name remains loadUserByUsername in UserDetailsService even if it loads by email
//	                if (jwtUtil.validateToken(token, userDetails)) {
//	                    return new UsernamePasswordAuthenticationToken(email, null, userDetails.getAuthorities());
//	                }
//	            }
//	            return null;
//	        }
//			System.out.println("after try");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	
//        return null;
//    }
}
