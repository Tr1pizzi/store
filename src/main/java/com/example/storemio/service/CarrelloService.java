package com.example.storemio.service;

import com.example.storemio.entità.ArticoloSportivo;
import com.example.storemio.entità.Carrello;
import com.example.storemio.entità.Carrelloarticolo;
import com.example.storemio.entità.Utente;
import com.example.storemio.repository.CarrelloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.storemio.repository.articolorepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CarrelloService {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private CarrelloRepository carrelloRepository;

    @Autowired
    private articolorepository articolorepository;

    @Autowired
    private ArticoloCarrelloservice carrelloartService;

    // CRUD

    public Carrello create(Integer utenteID) {
        Carrello carrello = new Carrello();

        carrello.setDataCreazione(new Date());
        carrello.setImporto(0.0d);
        carrello.setAcquistato(false);

        Utente utente = utenteService.getById(utenteID);
        assert utente != null;

        carrello.setUtente(utente);
        return carrelloRepository.save(carrello);
    }

    public List<Carrello> getAll() {
        return carrelloRepository.findAll();
    }

    public Carrello getById(Integer carrelloID) {
        return carrelloRepository.findById(carrelloID).orElse(null);
    }

    public Carrello getNonAcquistatoByUtenteId(Integer utenteID) {
        return carrelloRepository.findNonAcquistatoByUtente(utenteID).orElse(null);
    }

    public void delete(Integer carrelloID) {
        carrelloRepository.deleteById(carrelloID);
    }

    // METHODS

    public Carrello aggiungiarticolo(Integer carrelloID, Integer articoloid, Integer quantitaDaAggiungere) {

        // Carico la relazione
        Carrelloarticolo carrelloarticolo = carrelloartService.getByCarrelloIdAndArticoloSportivoId(carrelloID, articoloid);

        Carrello carrello;
        ArticoloSportivo articoloSportivo;

        if (carrelloarticolo != null) {
            carrello = carrelloarticolo.getCarrello();
            articoloSportivo = carrelloarticolo.getArticoloSportivo();
        } else {
            carrello = carrelloRepository.findById(carrelloID).orElse(null);
            articoloSportivo = articolorepository.findById(articoloid).orElse(null);
        }

        // Aggiungo al carrello
        carrelloartService.aggiungiArticolo(carrelloID, articoloid, quantitaDaAggiungere);

        // Aggiorno importo
        assert carrello != null && articoloSportivo != null;
        double nuovoImporto = carrello.getImporto() + (articoloSportivo.getPrezzo() * quantitaDaAggiungere);
        carrello.setImporto(nuovoImporto);

        // Aggiorna la lista degli articoli nel carrello
        List<Carrelloarticolo> articoliAggiornati = carrelloartService.getAllByCarrelloId(carrelloID);
        carrello.aggiornaArticoli(articoliAggiornati);

        return carrelloRepository.save(carrello);
    }

    public Carrello rimuoviArticolo(Integer carrelloID, Integer articoloId, Integer quantitaDaRimuovere) {

        // Carico la relazione
        Carrelloarticolo carrelloarticolo = carrelloartService.getByCarrelloIdAndArticoloSportivoId(carrelloID, articoloId);

        Carrello carrello = carrelloarticolo.getCarrello();
        ArticoloSportivo articoloSportivo = carrelloarticolo.getArticoloSportivo();

        // Rimuovo dal carrello
        carrelloartService.rimuoviarticolo(carrelloID, articoloId, quantitaDaRimuovere);

        // Aggiorno l'importo
        double importo = carrello.getImporto() - (articoloSportivo.getPrezzo() * quantitaDaRimuovere);
        carrello.setImporto(importo);

        // Aggiorna la lista degli articoli nel carrello
        List<Carrelloarticolo> articoliAggiornati = carrelloartService.getAllByCarrelloId(carrelloID);
        carrello.aggiornaArticoli(articoliAggiornati);

        return carrelloRepository.save(carrello);
    }

    public Carrello svuotaCarrello(Integer carrelloID) {

        // Svuoto il carrello
        carrelloartService.svuotaCarrello(carrelloID);

        Carrello carrello = carrelloRepository.findById(carrelloID).orElse(null);
        assert carrello != null;

        // Aggiorna l'importo
        carrello.setImporto(0.0);

        // Aggiorna la lista degli articoli nel carrello
        carrello.aggiornaArticoli(new ArrayList<>());

        return carrelloRepository.save(carrello);
    }
}
