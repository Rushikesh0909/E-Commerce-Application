package com.bikkadIt.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Authorization

        String requestHeader = request.getHeader("Authorization");
        log.info("Header:{}",requestHeader);

        String username=null;
        String token=null;

        if(requestHeader!=null && requestHeader.startsWith("Bearer")){
          token=requestHeader.substring(7);

          try {
           username = this.jwtHelper.getUsernamefromToken(token);

          }catch (IllegalArgumentException e){
              log.info("Illegal Argument while fetching the username");

          }catch (ExpiredJwtException e){
              log.info("Jwt token is expired");

          }catch (MalformedJwtException e){
              log.info("Invalid token");
          }

        }else {
            log.info("Invalid Header value");
        }

        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);

            if(validateToken){

                UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
              authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else {
                log.info("Validation fails");
            }
        }

        filterChain.doFilter(request,response);
    }
}
