package com.example.storemio.entità;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "carrelli")
@Getter
@Setter
public class Carrello {

    @Id
    @GeneratedValue(generator = "carrelli_id_seq")
    @SequenceGenerator(name = "carrelli_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @Column(name = "data_creazione")
    private Date dataCreazione;

    @Column(name = "importo")
    private Double importo;

    @Column(name = "acquistato")
    private Boolean acquistato;

    @OneToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @OneToMany(mappedBy = "carrello")
    @OrderBy("id ASC")
    private List<Carrelloarticolo> Articolisportivi;

    public void aggiornaArticoli(List<Carrelloarticolo> articoli) {
        this.Articolisportivi.clear();
        this.Articolisportivi.addAll(articoli);
    }
}
