package kz.hbscale.main.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTUserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager manager;


    public JWTUserAuthenticationFilter(AuthenticationManager manager) {
        this.manager = manager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        System.out.println("request came to attemptAuthentication");
        try {
            UsernameAndPasswordDto creds = new ObjectMapper().readValue(request.getInputStream(), UsernameAndPasswordDto.class);
            System.out.println("creds.getUsername(), creds.getPassword() : " + creds.getUsername() + " - " + creds.getPassword());

            Authentication auth = manager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword()));
            if(auth.isAuthenticated()) {
                System.out.println("user is authenticated");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return super.attemptAuthentication(request, response);
    }
}
