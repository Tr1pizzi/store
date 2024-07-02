import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Ordine} from "../entità/ordine";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class OrdineService {

  URL = "http://localhost:8080/api/ordine";

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {
  }

  create(carrelloID: number): Observable<Ordine> {
    return this.http.post<Ordine>(`${this.URL}/create`, carrelloID);
  }

  getById(ordineID: number) {
    return this.http.get<Ordine>(this.URL + "/get/" + ordineID);
  }

  getAll() {
    return this.http.get<Array<Ordine>>(this.URL + "/get/all");
  }

}
