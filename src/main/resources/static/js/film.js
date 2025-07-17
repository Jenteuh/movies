"use strict";
import {byId, setText, toon} from "./util.js";
const film = JSON.parse(sessionStorage.getItem("film"));
let mandje = JSON.parse(sessionStorage.getItem('mandje')) || [];
const titel = `${film.titel}`;
document.title = titel;
setText("titel", titel);
const imgFoto = byId("foto");
imgFoto.alt = titel;
imgFoto.src = `images/${film.id}.jpg`;
setText("prijs", film.prijs);
setText("voorraad", film.voorraad);
setText("gereserveerd", film.gereserveerd)
setText("beschikbaar", film.voorraad - film.gereserveerd)
if ((film.voorraad - film.gereserveerd) === 0) {
    document.getElementById("mandje").disabled = true;

}

byId("mandje").onclick = async () => {
    let alInMandje = mandje.some(obj => obj.titel ===  film.titel);
    if (!alInMandje) {
        mandje.push(film);
        sessionStorage.setItem('mandje', JSON.stringify(mandje));
        window.location = "mandje.html";
    } else {
        toon("duplicaat");
    }
}