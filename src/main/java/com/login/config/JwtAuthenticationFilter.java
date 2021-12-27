package com.login.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.login.service.CustomUserDetailsService;
import com.login.service.JwtUtil;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// get jwt header
		// starts with bearer
		// validate
		String authorizationToken = request.getHeader("Authorization");
		System.out.println(authorizationToken);
		request.getHeaderNames().asIterator().forEachRemaining(s->{
			System.out.println(s+": "+request.getHeader(s));
		});
		System.out.println();
		String username = null;
		String jwtToken;
		//check null and format
		if(authorizationToken != null && authorizationToken.startsWith("Bearer ") && authorizationToken.length()>8) {
			jwtToken = authorizationToken.substring(7);
			try {
				username = this.jwtUtil.getUsernameFromToken(jwtToken);
			} catch (Exception e) {
				System.out.println(e);
			}
			UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
			//security
			if(username != null && SecurityContextHolder.getContext().getAuthentication()==null) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			} else {
				System.out.println("TOken is not valid...");
			}
		}

		filterChain.doFilter(request, response);
	}

}
