package com.example.storemio.controllerRest;

import com.example.storemio.Exception.messaggiodirisposta;
import com.example.storemio.entità.Ordine;
import com.example.storemio.service.OrdineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ordine")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public class OrdineRestController {

    @Autowired
    private OrdineService ordineService;

    // CRUD

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Integer id) {
        try{
            Ordine o=ordineService.create(id);
            return new ResponseEntity<>(o, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(new messaggiodirisposta("ordine non creato"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/{ordineID}")
    public ResponseEntity<?> getById(@PathVariable Integer ordineID) {
        return ResponseEntity.ok(ordineService.getById(ordineID));
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(ordineService.getAll());
    }





}

