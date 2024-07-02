package com.example.storemio.service;

import com.example.storemio.entità.ArticoloSportivo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.storemio.repository.articolorepository;
import java.util.List;

@Service
public class ArticoloSportivoservice {

    @Autowired
    private articolorepository articolorepository;

    // CRUD

    public List<ArticoloSportivo> getAll() {
        return articolorepository.findAllByOrderByNomeArticolo();
    }

}
