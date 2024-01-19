//package com.logiclegends.MerryMeal.JwtSecurity;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Date;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.boot.configurationprocessor.json.JSONObject;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import com.logiclegends.MerryMeal.service.JwtUtil;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//
//public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
//
//
//	    private final AuthenticationManager authenticationManager;
//	    private final JwtUtil jwtUtil; // Inject the JwtUtil service
//
//	    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
//	        this.authenticationManager = authenticationManager;
//	        this.jwtUtil = jwtUtil;
//	    }
//
//	    @Override
//	    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
//	            throws AuthenticationException {
////	        try {
////	            // Extract email and password from the request's body
////	            StringBuilder sb = new StringBuilder();
////	            System.out.println("Hello I'm request body's data " + sb);
////	            
////	            String line;
////	            BufferedReader reader = request.getReader();
////	            System.out.println("Hello I'm request reader : " + reader);
////	            
////	            while ((line = reader.readLine()) != null) {
////	                sb.append(line);
////	            }
////	            JSONObject json = new JSONObject(sb.toString());
////	            
////	            System.out.println("Hello I'm Json Object : " + json);
////	            
////	            String email = json.getString("email");  
////	            String password = json.getString("password");
////
////	            System.out.println("Hello I'm Json Email : " + email);
////	            System.out.println("Hello I'm Json Password : " + password);
////	            // Authenticate the user using the email
////	            return authenticationManager.authenticate(
////	                new UsernamePasswordAuthenticationToken(email, password, new ArrayList<>())
////	            );
////	        }catch (Exception e) {
////	            throw new org.springframework.security.authentication.InternalAuthenticationServiceException("Failed to authenticate user", e);
////	        }
//	    	return null;
//	    }
//
//	    @Override
//	    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
//	                                            Authentication authResult) throws IOException, ServletException {
//	        // Use JwtUtil to generate the token
////	        UserDetails userDetails = (UserDetails) authResult.getPrincipal();
////	        String token = jwtUtil.generateToken(userDetails);
////	        System.out.println("this is " + token);
////	        response.addHeader("Authorization", "Bearer " + token);
//	        
////	        chain.doFilter(request, response);
//	    }
//	}
//
