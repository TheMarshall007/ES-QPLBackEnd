package br.com.univali.gabby_leo_kallil.quiz.api.access.auth;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import br.com.univali.gabby_leo_kallil.quiz.api.access.auth.DTO.AuthDTORequest;
import br.com.univali.gabby_leo_kallil.quiz.api.access.auth.DTO.JWTDTOResponse;
import br.com.univali.gabby_leo_kallil.quiz.api.access.user.User;
import br.com.univali.gabby_leo_kallil.quiz.api.access.user.UserService;
import br.com.univali.gabby_leo_kallil.quiz.security.exception.WarningException;
import br.com.univali.gabby_leo_kallil.quiz.security.security.jwt.JwtUtils;
import br.com.univali.gabby_leo_kallil.quiz.security.security.services.UserDetailsImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@SecurityRequirement(name = "vinicius-piai@hotmail.com")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @CrossOrigin
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JWTDTOResponse> authenticateUser(@Valid @RequestBody AuthDTORequest authDTORequest) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authDTORequest.getUsername(), authDTORequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            User user = userService.findUserByEmail(userDetails.getEmail());
            String jwt = jwtUtils.generateJwtToken1(user);
            JWTDTOResponse jwtdtoResponse = new JWTDTOResponse();
            jwtdtoResponse.setToken(jwt);
            jwtdtoResponse.setId(user.getId());
            jwtdtoResponse.setUsername(userDetails.getUsername());
            jwtdtoResponse.setEmail(userDetails.getEmail());
            jwtdtoResponse.setRoles(roles);
            return ResponseEntity.ok(jwtdtoResponse);
        }catch (AuthenticationException e){
            throw new WarningException("E-mail e/ou senha incorretos");
        }
    }

    /*@PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }*/
}
