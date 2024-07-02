package com.example.storemio.service;

import com.example.storemio.entità.ArticoloSportivo;
import com.example.storemio.entità.Carrello;
import com.example.storemio.entità.Carrelloarticolo;
import com.example.storemio.entità.Ordine;
import com.example.storemio.repository.OrdineRepository;
import com.example.storemio.repository.articolorepository;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import com.example.storemio.repository.CarrelloArticoloRepository;
import java.util.Date;
import java.util.List;

@Service
public class OrdineService {

    @Autowired
    private OrdineRepository ordineRepository;

    @Autowired
    private CarrelloService carrelloService;


    @Autowired
    private articolorepository articolorepositor;

    // CRUD

    @Transactional
    @Lock(LockModeType.OPTIMISTIC)
    public Ordine create(Integer carrelloID) {

        // Carico il carrello
        Carrello carrello = carrelloService.getById(carrelloID);
        carrello.setAcquistato(true);

        // Creo l'ordine
        Ordine ordine = new Ordine();
        ordine.setData(new Date());
        ordine.setCarrello(carrello);
        ordine.setImporto(carrello.getImporto());


        // Decremento le quantita in magazzino
        for (Carrelloarticolo cl : carrello.getArticolisportivi()) {
            ArticoloSportivo articoloSportivo = cl.getArticoloSportivo();
            articoloSportivo.setQuantita(articoloSportivo.getQuantita() - cl.getQuantita());
            articolorepositor.save(articoloSportivo);
        }

        return ordineRepository.save(ordine);

    }

    public List<Ordine> getAll() {
        return ordineRepository.findAll();
    }

    public Ordine getById(Integer ordineID) {
        return ordineRepository.findById(ordineID).orElse(null);
    }

}

