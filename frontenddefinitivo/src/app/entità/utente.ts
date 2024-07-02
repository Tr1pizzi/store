import {Ruolo} from "./ruolo";

export interface Utente {
  id: number,
  nome: string,
  cognome: string,
  dataNascita: Date,
  username: string,
  email: string,
  password: string,
  ruolo: Ruolo,

}
