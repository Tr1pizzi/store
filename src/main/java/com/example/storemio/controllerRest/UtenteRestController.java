package com.example.storemio.controllerRest;

import com.example.storemio.entità.Utente;
import com.example.storemio.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/utente")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public class UtenteRestController {

    @Autowired
    private UtenteService utenteService;


    // CRUD

    @GetMapping("/get/{utenteID}")
    public ResponseEntity<?> get(@PathVariable Integer utenteID) {
        return ResponseEntity.ok(utenteService.getById(utenteID));
    }

    @PostMapping("/update/{utenteID}")
    public ResponseEntity<?> update(@PathVariable Integer utenteID, @RequestBody Utente utente) {
        return ResponseEntity.ok(utenteService.update(utenteID, utente));
    }



}

