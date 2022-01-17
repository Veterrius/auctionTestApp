package by.itstep.auction.controller.restController;

import by.itstep.auction.dao.model.User;
import by.itstep.auction.dao.model.dto.AuthenticationRequestDTO;
import by.itstep.auction.security.JwtTokenProvider;
import by.itstep.auction.security.SecurityUser;
import by.itstep.auction.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationRestController {

    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationRestController(AuthenticationManager authenticationManager, UserServiceImpl userService, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO request, HttpServletResponse response) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            User user = userService.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
            String token = jwtTokenProvider.createToken(request.getEmail(), user.getRole().name());
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(cookie);
            Map<Object, Object> body = new HashMap<>();
            body.put("email", request.getEmail());
            body.put("token", token);

            return ResponseEntity.ok(body);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid email password combination", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/register")
    public UserDetails user(@RequestBody User user) {
        return SecurityUser.fromUser(userService.createUser(user));
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
