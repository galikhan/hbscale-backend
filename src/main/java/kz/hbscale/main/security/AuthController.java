package kz.hbscale.main.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import kz.hbscale.main.jwt.UsernameAndPasswordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;

@RestController
@CrossOrigin
@Component
public class AuthController {

//    @Autowired
//    private JWTTokenUtils jwtTokenUtils;

    @Autowired
    private CustomAuthenticationManager authenticationManager;

    @RequestMapping(value = "/authenticate1", method = RequestMethod.POST)
    public ResponseEntity<?> authenticate(@RequestBody UsernameAndPasswordDto userRequest) {

        // try to authenticate user using specified credentials
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));

        // if authentication succeeded and is not anonymous
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated()) {

            // set authentication in security context holder
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // get authorities, we should have only one role per member so simply get the first one
            final GrantedAuthority grantedAuthority = authentication.getAuthorities().iterator().next();

            // generate new JWT token
            Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

            String jws = Jwts.builder().setSubject("Joe").signWith(key).compact();

//            final String jwtToken = jwtTokenUtils.generateToken(authentication.getPrincipal(), grantedAuthority);

            // return response containing the JWT token
            return ResponseEntity.ok(jws);
//            return ResponseEntity.ok(new JWTResponse(jwtToken));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }

}