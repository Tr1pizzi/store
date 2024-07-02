import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {richiestadiLogin} from "../supporto/richieste/richiestadiLogin";
import {richiestadiregistrazione} from "../supporto/richieste/richiestadiregistrazione";
import {StorageService} from "./storage.service";

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  URL = 'http://localhost:8080/api/auth/';

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient, private storage: StorageService) {
  }

  login(data: richiestadiLogin): Observable<any> {
    return this.http.post(
      this.URL + 'login',
      data,
      this.httpOptions
    );
  }

  register(data: richiestadiregistrazione): Observable<any> {
    return this.http.post(
      this.URL + 'register',
      data,
      this.httpOptions
    );
  }

  logout(): void {
    this.storage.clean();
    window.location.replace("/");
  }

  isLoggedIn(): boolean {
    const jwt = this.storage.getJWToken();
    return !!jwt;
  }
}
