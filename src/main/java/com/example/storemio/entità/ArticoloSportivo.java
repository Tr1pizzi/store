package com.example.storemio.entità;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "articolosportivo")
@Getter
@Setter
public class ArticoloSportivo {

    @Id
    @GeneratedValue(generator = "articolosportivo_id_seq")
    @SequenceGenerator(name = "articolosportivo_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @Column(name= "nome_articolo")
    private String nomeArticolo;

    @Column(name = "descrizione")
    private String descrizione;


    @Column(name = "eta_minima")
    private Integer etaMinima;

    @Column(name = "quantita")
    private Integer quantita;


    @Column(name = "prezzo")
    private Double prezzo;

    @Column(name = "produttore")
    private String produttore;
}
