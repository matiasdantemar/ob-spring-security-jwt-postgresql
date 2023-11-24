package com.example.obspringsecurityjwt.rest;
import com.example.obspringsecurityjwt.domain.User;
import com.example.obspringsecurityjwt.repository.UserRepository;
import com.example.obspringsecurityjwt.security.jwt.JwtTokenUtil;
import com.example.obspringsecurityjwt.security.payload.JwtResponse;
import com.example.obspringsecurityjwt.security.payload.LoginRequest;
import com.example.obspringsecurityjwt.security.payload.MessageResponse;
import com.example.obspringsecurityjwt.security.payload.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

// se hace el login y el registro
/**
 * Controlador para llevar a cabo la autenticación utilizando JWT
 *
 * Se utiliza AuthenticationManager para autenticar las credenciales que son el
 * usuario y password que llegan por POST en el cuerpo de la petición
 *
 * Si las credenciales son válidas se genera un token JWT como respuesta
 */
// @CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;  // el PasswordEncoder que se usa esta configurado en Securityconfig
    private final JwtTokenUtil jwtTokenUtil;

    public AuthController(AuthenticationManager authManager,
                          UserRepository userRepository,
                          PasswordEncoder encoder,
                          JwtTokenUtil jwtTokenUtil){
        this.authManager = authManager;
        this.userRepository = userRepository;
        this.encoder = encoder; // se inyecta en el constructor el bean PasswordEncoder
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest){// Se extraen las credenciales de inicio de sesión del cuerpo de la solicitud.

        // El controlador utiliza las clases de Spring Security para autenticar al usuario.
        // Las credenciales se validan a través del AuthenticationManager.
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        // Si la autenticación es exitosa, se agrega la información username y password de autenticación al contexto de seguridad.
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // a partir de esa autenticacion Se genera un token JWT para el usuario autenticado.
        String jwt = jwtTokenUtil.generateJwtToken(authentication);

        // Se crea y devuelve una respuesta exitosa con el token JWT.
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    //intenta guardar un usuario en base de datos
    @PostMapping("/register")
    //le envian un objeto que es RegisterRequest el cual tiene username, email y password
    public ResponseEntity<MessageResponse> register(@RequestBody RegisterRequest signUpRequest) {

        // Check 1: comprueba que no exista el username en base de datos
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        // Check 2: comprueba que el email no exista en base de datos
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Crea nueva cuenta de usuario
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                //el encoder que viene de RegisterRequest que es un PasswordEncoder, esta utilizando un encoder para cifrar la contraseña
                encoder.encode(signUpRequest.getPassword()));

        userRepository.save(user); //almacena el usuario en la base de datos

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}