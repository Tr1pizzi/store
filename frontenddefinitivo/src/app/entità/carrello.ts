import {Utente} from "./utente";
import {Carrelloarticolo} from "./carrello.articolo";
export interface Carrello {
  id: number;
  Articolisportivi: Carrelloarticolo[];
  importo: number;
  dataCreazione: Date;
  acquistato: boolean;
  utente: Utente;
}

