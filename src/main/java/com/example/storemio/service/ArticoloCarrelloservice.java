package com.example.storemio.service;

import com.example.storemio.entità.Carrelloarticolo;
import com.example.storemio.repository.CarrelloArticoloRepository;
import com.example.storemio.repository.CarrelloRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import com.example.storemio.repository.articolorepository;

import java.util.List;

@Service
public class ArticoloCarrelloservice {

    @Autowired
    private CarrelloArticoloRepository carrelloArticoloRepository;

    @Autowired
    private CarrelloRepository carrelloRepository;

    @Autowired
    private articolorepository articolorepository;

    // CRUD

    public Carrelloarticolo getByCarrelloIdAndArticoloSportivoId(Integer carrelloID, Integer articoloid) {
        return carrelloArticoloRepository.findByCarrelloIdAndArticoloSportivoId(carrelloID, articoloid).orElse(null);
    }

    public List<Carrelloarticolo> getAllByCarrelloId(Integer carrelloID) {
        return carrelloArticoloRepository.findAllByCarrelloId(carrelloID);
    }

    // METHODS

    @Modifying
    @Transactional
    public Carrelloarticolo aggiungiArticolo(Integer carrelloID, Integer articoloID, Integer quantita) {

        Carrelloarticolo carrellodaaggiornare = carrelloArticoloRepository.findByCarrelloIdAndArticoloSportivoId(carrelloID, articoloID).orElse(null);

        if (carrellodaaggiornare != null) {
            carrellodaaggiornare.setQuantita(carrellodaaggiornare.getQuantita() + quantita);
        } else {
            Carrelloarticolo carrelloart = new Carrelloarticolo();
            carrelloart.setCarrello(carrelloRepository.findById(carrelloID).orElse(null));
            carrelloart.setArticoloSportivo(articolorepository.findById(articoloID).orElse(null));
            carrelloart.setQuantita(quantita);
            return carrelloArticoloRepository.save(carrelloart);
        }
        return carrelloArticoloRepository.save(carrellodaaggiornare);
    }

    @Modifying
    @Transactional
    public void rimuoviarticolo(Integer carrelloID, Integer articoloid, Integer quantitaDaRimuovere) {

        Carrelloarticolo clmod = carrelloArticoloRepository.findByCarrelloIdAndArticoloSportivoId(carrelloID, articoloid).orElse(null);
        assert clmod != null;

        if (clmod.getQuantita() - quantitaDaRimuovere <= 0) {
            carrelloArticoloRepository.elimina(carrelloID, articoloid);
        } else {
            clmod.setQuantita(clmod.getQuantita() - quantitaDaRimuovere);
            carrelloArticoloRepository.save(clmod);
        }
    }

    @Modifying
    @Transactional
    public void svuotaCarrello(Integer carrelloID) {
        carrelloArticoloRepository.svuotaCarrello(carrelloID);
    }
}
