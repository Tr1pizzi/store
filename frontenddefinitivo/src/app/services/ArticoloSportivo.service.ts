import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ArticoloSportivo} from "../entità/articolosportivo";

@Injectable({
  providedIn: 'root'
})
export class ArticoloSportivoService {

  URL = "http://localhost:8080/api/Articolo";

  constructor(private http: HttpClient) {
  }

  getAll() {
    return this.http.get<Array<ArticoloSportivo>>(this.URL + "/get/all");
  }
}
