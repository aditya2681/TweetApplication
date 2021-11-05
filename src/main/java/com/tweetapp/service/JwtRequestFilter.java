//package com.tweetapp.service;
//
//import java.io.IOException;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
///**
// * @author POD4
// * @version 1.8
// * @apiNote This class is used to intercept every method. It extends class
// *          {@link OncePerRequestFilter} that aims to guarantee a single
// *          execution per request dispatch, on any servlet container. It
// *          provides a doFilterInternalmethod with HttpServletRequest and
// *          HttpServletResponse arguments.
// *
// * @see HttpServletRequest
// * @see HttpServletResponse
// */
//@Component
//public class JwtRequestFilter extends OncePerRequestFilter{
//	/**
//	 * This holds the object of type {@link JwtUtil} class which will be injected
//	 * automatically because of the annotation autowired.
//	 */
//	@Autowired
//	private JwtUtil jwtUtil;
//	/**
//	 * This holds the object of type {@link CustomerDetailsService} class which will
//	 * be injected automatically because of the annotation autowired.
//	 */
//	@Autowired
//	private MyUserDetailsService customUserDetailsService;
//	/**
//	 * This method guaranteed to be just invoked once per request within a single
//	 * request thread.
//	 */
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		System.out.println(request.toString());
//		final String authHeadder = request.getHeader("Authorization");
//		String username = null;
//		String jwt = null;
//
//		if (authHeadder != null && authHeadder.startsWith("Bearer ")) {
//			jwt = authHeadder.substring(7);
//			username = jwtUtil.extractUsername(jwt);
//
//		}
//		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//			UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
//
//			Boolean validateToken = jwtUtil.validateToken(jwt, userDetails);
//			if (Boolean.TRUE.equals(validateToken)) {
//
//				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//						userDetails, null, userDetails.getAuthorities());
//				usernamePasswordAuthenticationToken
//				.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//			}
//		}
//		filterChain.doFilter(request, response);
//	}
//
//}
