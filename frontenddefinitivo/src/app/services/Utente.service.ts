import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Utente} from "../entità/utente";
import {ArticoloSportivo} from "../entità/articolosportivo";

@Injectable({
  providedIn: 'root'
})
export class UtenteService {

  URL = "http://localhost:8080/api/utente";

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {
  }

  get(utenteID: number) {
    return this.http.get<Utente>(this.URL + "/get/" + utenteID);
  }


  update(utenteID: number, utente: Utente) {
    return this.http.post<Utente>(this.URL + "/update/" + utenteID, utente, this.httpOptions);
  }

}
