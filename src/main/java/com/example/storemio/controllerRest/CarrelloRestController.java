package com.example.storemio.controllerRest;

import com.example.storemio.Exception.messaggiodirisposta;
import com.example.storemio.entità.Carrello;
import com.example.storemio.entità.Carrelloarticolo;
import com.example.storemio.service.CarrelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carrello")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public class CarrelloRestController {

    @Autowired
    private CarrelloService carrelloService;

    // CRUD

    @PostMapping("/create/{utenteID}")
    public ResponseEntity<?> create(@PathVariable Integer utenteID) {
        try{
            carrelloService.create(utenteID);
            return new ResponseEntity<>(new messaggiodirisposta("carrello creato"), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(new messaggiodirisposta("carrello non creato"), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(carrelloService.getAll());
    }

    @GetMapping("/get/{utenteID}")
    public ResponseEntity<?> getByUtente(@PathVariable int utenteID) {
        return ResponseEntity.ok(carrelloService.getNonAcquistatoByUtenteId(utenteID));
    }

    @DeleteMapping("/delete/{carrelloID}")
    public ResponseEntity<?> delete(@PathVariable int carrelloID) {
        try {
            carrelloService.delete(carrelloID);
            return new ResponseEntity<>(new messaggiodirisposta("Carrello eliminato con successo!"),HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(new messaggiodirisposta("non è stato possibile eliminare il carrello"),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/aggiungi/{carrelloID}/{articoloid}/{quantita}")
    public ResponseEntity<?> aggiungi(@PathVariable int carrelloID, @PathVariable int articoloid, @PathVariable int quantita) {
        try {

           Carrello c= carrelloService.aggiungiarticolo(carrelloID,articoloid,quantita);
            return new ResponseEntity<>(c,HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(new messaggiodirisposta("non è stato possibile inserire alcun elemento"),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/rimuovi/{carrelloID}/{articoloid}/{quantita}")
    public ResponseEntity<?> rimuovi(@PathVariable int carrelloID, @PathVariable int articoloid, @PathVariable int quantita) {
        try {
            Carrello c=carrelloService.rimuoviArticolo(carrelloID,articoloid,quantita);
            return new ResponseEntity<>(c,HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(new messaggiodirisposta("non è stato possibile eliminare l'elemento dal carrello"),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/svuota/{carrelloID}")
    public ResponseEntity<?> svuota(@PathVariable int carrelloID) {
        try {
             Carrello c=carrelloService.svuotaCarrello(carrelloID);
            return new ResponseEntity<>(c,HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(new messaggiodirisposta("non è stato possibile svuotare il carrello desiderato"),HttpStatus.BAD_REQUEST);
        }
    }
}