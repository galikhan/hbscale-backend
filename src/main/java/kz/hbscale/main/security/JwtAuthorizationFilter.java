package kz.hbscale.main.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Optional;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {


    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        System.out.println("authorization");
        Optional<String> header = Optional.ofNullable(request.getHeader("Authorization"));
        if(header.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken token = getToken(request);
        SecurityContextHolder.getContext().setAuthentication(token);
        chain.doFilter(request, response);
    }

    public UsernamePasswordAuthenticationToken getToken(HttpServletRequest request) {
        Optional<String> header = Optional.ofNullable(request.getHeader("Authorization"));
        if(header.isPresent()) {
            String headerValue = header.get();
            String token = headerValue.replace("Bearer ", "");
            Optional<String> username = validateAndGetUsername(token);
            System.out.println("token " + token);
            if(username.isPresent()) {
                UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(username.get(), null, new ArrayList<>());
                return upat;
            } else {
                return null;
            }
        }
        return null;
    }

    private Optional<String> validateAndGetUsername(String token) {

        String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";

        Key key = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());

        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            String username = (String) claimsJws.getBody().get("username");
            return Optional.of(username);
        } catch (JwtException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
