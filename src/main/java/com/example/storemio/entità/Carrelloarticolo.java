package com.example.storemio.entità;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "carrelli_articoli")
@Getter
@Setter
public class Carrelloarticolo {

    @Id
    @GeneratedValue(generator = "carrelli_articoli_id_seq")
    @SequenceGenerator(name = "carrelli_articoli_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "carrello_id")
    @JsonIgnore
    private Carrello carrello;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "articolo_id")
    private ArticoloSportivo articoloSportivo;

    @Column(name = "quantita")
    private Integer quantita;

}
