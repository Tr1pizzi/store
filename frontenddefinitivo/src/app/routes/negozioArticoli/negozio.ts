import {Component, OnInit} from '@angular/core';
import {ArticoloSportivoService} from "../../services/ArticoloSportivo.service";
import {Carrello} from "../../entità/carrello";
import {ArticoloSportivo} from "../../entità/articolosportivo";
import {CarrelloService} from "../../services/carrello.service";
import {AuthService} from "../../services/auth.service";
import * as far from "@fortawesome/free-regular-svg-icons";
import * as fas from "@fortawesome/free-solid-svg-icons";
import {UtenteService} from "../../services/Utente.service";
import {StorageService} from "../../services/storage.service";


@Component({
  selector: 'app-negozioarticoli',
  templateUrl: './negozio.html',
  styleUrls: ['./negozio.scss']
})
export class negozioArticoliComponent implements OnInit {

  dataLoaded = false;
  isLoggedIn = false;
  ListaArticoli: Array<ArticoloSportivo> = [];
  carrello: Carrello | null = null;

  // Icone
  faSearch = fas.faSearch;
  faSort = fas.faArrowDownWideShort;
  faLikeEmpty = far.faHeart;
  faLikeFull = fas.faHeart;

  // Sort
  asc = false;

  // Ricerca
  ricerca: string = "";

  constructor(private storageService: StorageService, private ArticoloService: ArticoloSportivoService, private carrelloService: CarrelloService, private authService: AuthService, private utenteService: UtenteService) {
  }

  ngOnInit(): void {

    this.isLoggedIn = this.authService.isLoggedIn();

    this.ArticoloService.getAll().subscribe({
      next: data => {
        this.ListaArticoli = data as Array<ArticoloSportivo>;
        this.dataLoaded = true;
      },
      error: err => {
        console.log(err);
      }
    });

    if (this.authService.isLoggedIn()) {

      let utenteID = this.storageService.getJWToken()!.id;

      // Carico il carrello
      this.carrelloService.getUpdate().subscribe({
        next: data => {
          this.carrello = data;
        },
        error: err => {
          console.log(err);
        }
      });
    }
  }

  alertNotLogged() {
    alert("Esegui il login prima!");
  }

  ampliaDescrizione(id: number) {
    let descrizione = document.getElementById("descrizione-" + id);
    let button = document.getElementById("show-" + id);

    if (descrizione!.style.webkitLineClamp !== "999") {
      descrizione!.style.webkitLineClamp = "999";
      button!.innerHTML = "Mostra meno"
    } else {
      descrizione!.style.webkitLineClamp = "10";
      button!.innerHTML = "Mostra di piu..."
    }
  }

  aggiungiArticolo(Articoloid: number) {
    this.carrelloService.aggiungiArticolo(Articoloid, 1);
  }
  rimuoviArticolo(Articoloid: number) {
    this.carrelloService.rimuoviArticolo(Articoloid, 1);
  }

  // Sorting

  cercaPernome() {

    this.ArticoloService.getAll().subscribe({
      next: data => {
        this.ListaArticoli = data as Array<ArticoloSportivo>;

        if (this.ricerca.length == 0) {
          return;
        }

        this.ListaArticoli = this.ListaArticoli.filter((Articolo) => {
          let nome = Articolo.nomeArticolo.toLowerCase().includes(this.ricerca.toLowerCase());
          let produttore = Articolo.produttore.toLowerCase().includes(this.ricerca.toLowerCase());
          let descrizione = Articolo.descrizione.toLowerCase().includes(this.ricerca.toLowerCase());
          return nome || produttore || descrizione;
        });

      },
      error: err => {
        console.log(err);
      }
    });

  }



  sortBynome() {
    this.ListaArticoli = this.ListaArticoli.sort((a, b) => {
      return this.asc ? a.nomeArticolo.localeCompare(b.nomeArticolo) : b.nomeArticolo.localeCompare(a.nomeArticolo);
    });
    this.asc = !this.asc;
  }

  sortByproduttore() {
    this.ListaArticoli = this.ListaArticoli.sort((a, b) => {
      let produttorA = a.produttore.split(" ").at(-1);
      let produttorB = b.produttore.split(" ").at(-1);
      return this.asc ? produttorA!.localeCompare(produttorB!) : produttorB!.localeCompare(produttorA!);
    });
    this.asc = !this.asc;
  }

  sortByPrezzo() {
    this.ListaArticoli = this.ListaArticoli.sort((a, b) => {
      return this.asc ? (a.prezzo - b.prezzo) : (b.prezzo - a.prezzo);
    });
    this.asc = !this.asc;
  }

  sortByQuantita() {
    this.ListaArticoli = this.ListaArticoli.sort((a, b) => {
      return this.asc ? (a.quantita - b.quantita) : (b.quantita - a.quantita);
    })
    this.asc = !this.asc;
  }
}
