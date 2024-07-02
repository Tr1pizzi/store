package com.example.storemio.controllerRest;

import com.example.storemio.Exception.messaggiodirisposta;
import com.example.storemio.entità.Utente;
import com.example.storemio.repository.RuoloRepository;
import com.example.storemio.repository.UtenteRepository;
import com.example.storemio.response.JwtResponse;
import com.example.storemio.richiesta.richiestadiLogin;
import com.example.storemio.richiesta.richiestadiregistrazione;
import com.example.storemio.sicurezza.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private RuoloRepository ruoloRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${com.example.storemio.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${com.example.storemio.jwtRememberExpirationMs}")
    private int jwtRememberExpirationMs;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody richiestadiLogin richiestadiLogin) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(richiestadiLogin.getUsername(), richiestadiLogin.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Date expiration =
                richiestadiLogin.getRemember() ?
                        new Date((new Date()).getTime() + jwtRememberExpirationMs) :
                        new Date((new Date()).getTime() + jwtExpirationMs);

        String jwt = jwtUtils.generateJwtToken(authentication, expiration);

        Utente utente = (Utente) authentication.getPrincipal();
        List<String> roles = utente.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new JwtResponse(
                        jwt,
                        utente.getId(),
                        utente.getUsername(),
                        utente.getEmail(),
                        roles,
                        expiration
                )
        );

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody richiestadiregistrazione richiestadiregistrazione) throws ParseException {

        // Username esiste già
        if (utenteRepository.existsByUsername(richiestadiregistrazione.getUsername())) {
            return new ResponseEntity<>(new messaggiodirisposta("esiste già un utente con questo username"), HttpStatus.BAD_REQUEST);
        }

        // Email esiste già
        if (utenteRepository.existsByEmail(richiestadiregistrazione.getEmail())) {
            return new ResponseEntity<>(new messaggiodirisposta("email già associata ad una altro utente"),HttpStatus.BAD_REQUEST);
        }
        //se non esiste alcun utente già memorizzato con lo stesso username e stessa mail procedo con il creare un nuovo utente
        Utente utente = new Utente(
                richiestadiregistrazione.getNome(),
                richiestadiregistrazione.getCognome(),
                new SimpleDateFormat("yyyy-MM-dd").parse(richiestadiregistrazione.getDataNascita()),
                richiestadiregistrazione.getUsername(),
                richiestadiregistrazione.getEmail(),
                encoder.encode(richiestadiregistrazione.getPassword()),
                ruoloRepository.findByNome("ROLE_USER").orElse(null)
        );

        utenteRepository.save(utente);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(utente.getUsername(), richiestadiregistrazione.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Date expiration = new Date((new Date()).getTime() + jwtExpirationMs);
        String jwt = jwtUtils.generateJwtToken(authentication, expiration);

        List<String> roles = utente.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new JwtResponse(
                        jwt,
                        utente.getId(),
                        utente.getUsername(),
                        utente.getEmail(),
                        roles,
                        expiration
                )
        );
    }

}
