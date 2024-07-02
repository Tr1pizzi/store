import {Component, OnInit} from '@angular/core';
import {CarrelloService} from "../../services/carrello.service";
import {StorageService} from "../../services/storage.service";
import {faTrash} from "@fortawesome/free-solid-svg-icons";
import {HttpClient, HttpHeaders} from "@angular/common/http";

import {OrdineService} from "../../services/Ordine.service";
import {Carrello} from "../../entità/carrello";

@Component({
  selector: 'app-carrello',
  templateUrl: './carrello.component.html',
  styleUrls: ['./carrello.component.scss']
})
export class CarrelloComponent implements OnInit {

  utenteID = -1;
  carrello: Carrello | null = null;

  // HTTP
  URl = "http://localhost:8080/api";
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  // Icone
  faRemove = faTrash;

  constructor(private http: HttpClient, private carrelloService: CarrelloService, private ordineService: OrdineService, private storageService: StorageService) {

  }

  ngOnInit(): void {
    this.utenteID = this.storageService.getJWToken()!.id;
    this.updateCarrello();
  }

  updateCarrello() {
    this.carrelloService.getUpdate().subscribe({
      next: data => {
        this.carrello = data;
      },
      error: err => {
        console.log(err);
      }
    })
  }

  svuotaCarrello() {
    this.carrelloService.svuotaCarrello();
  }

  acquistaCarrello() {
    if (!this.carrello || this.carrello.importo== 0) {
      alert("Inserisci almeno un articolo nel carrello prima di acquistare!");
      return;
    }
    this.ordineService.create(this.carrello!.id).subscribe({
      next: _ => {
        alert("Ordine effettuato con successo");
        window.location.reload();
      },
      error: err => {
        console.log("Errore durante la creazione dell'ordine:", err);
      }
    });
  }
}
