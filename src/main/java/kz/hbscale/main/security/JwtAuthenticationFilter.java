package kz.hbscale.main.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kz.hbscale.main.dto.UsernameAndPasswordDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.sql.Date;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/api/auth");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        UsernameAndPasswordDto usernameAndPasswordDto;
        try {
            System.out.println("attempt " + request.getInputStream().isReady());
            usernameAndPasswordDto = new ObjectMapper().readValue(
                    request.getInputStream(), UsernameAndPasswordDto.class
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("up = " + usernameAndPasswordDto.username +", "+ usernameAndPasswordDto.password);
        AuthenticationManager au = authenticationManager;
        System.out.println("au = " + au);
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                usernameAndPasswordDto.username,
                usernameAndPasswordDto.password
        ));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        User user = (User) authResult.getPrincipal();
        String payload = "{\"token\":\"" + generateToken(user.getUsername()) + "\"}";
        response.getWriter().write(payload);
        response.setContentType("application/json");
        response.getWriter().flush();
//        super.successfulAuthentication(request, response, chain, authResult);
    }

    private String generateToken(String username) {

        String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";

        Key key = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());

        Date d = new Date(System.currentTimeMillis() + 50 * 1000);
        Map<String, String> claims = new HashMap<>();
        claims.put("username", username);
        String jws = Jwts.builder().setExpiration(d).setSubject("User").setClaims(claims).signWith(key).compact();
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws);
            System.out.println("ok they are equaal");
        } catch (JwtException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("authenticate token len = " + jws.length());
        return jws;
    }
}
