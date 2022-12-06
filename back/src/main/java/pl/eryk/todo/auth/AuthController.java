package pl.eryk.todo.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.eryk.todo.config.security.JWTGenerator;
import pl.eryk.todo.dto.AuthDetailsDTO;
import pl.eryk.todo.dto.AuthResponseDTO;
import pl.eryk.todo.user.User;
import pl.eryk.todo.user.UserService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    private final JWTGenerator jwtGenerator;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> registerUser(@RequestBody AuthDetailsDTO detailsDTO, HttpServletResponse response) {
        User user = userService.createUser(detailsDTO.getUsername(), detailsDTO.getPassword());

        return loginUser(detailsDTO, response);
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUser(Authentication authentication) {
        return ResponseEntity.ok(userService.getUser(authentication.getName()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> loginUser(@RequestBody AuthDetailsDTO detailsDTO, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(detailsDTO.getUsername(), detailsDTO.getPassword());

        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = jwtGenerator.generateToken(authentication);

        return ResponseEntity.ok(new AuthResponseDTO(true, jwtToken, "Zalogowano poprawnie!"));
    }
}
