import {Carrello} from "./carrello";

export interface Ordine {
  id: number,
  data: Date,
  importo: number,
  carrello: Carrello | null,
}
