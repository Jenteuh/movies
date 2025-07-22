"use strict";
import {byId, setText, toon, verberg} from "./util.js";
const klant = JSON.parse(sessionStorage.getItem("klant"));
const mandje = JSON.parse(sessionStorage.getItem("mandje"));
const bestelling = `${mandje.length} film(s) voor ${klant.familienaam} ${klant.voornaam}`;
setText("bestelling", bestelling);

byId("bevestig").onclick = async () => {
    verberg("mandjeLink");
    toon("besteldeFilms");
    document.getElementById("bevestig").disabled = true;
    const ul = byId("besteldeFilms");
    for (const film of mandje) {
        const li = document.createElement("li");
        const p = document.createElement("p");
        li.setAttribute("class", "onderElkaar");
        li.textContent = `${film.titel}`
        p.textContent = await reserveer(film);
        li.appendChild(p);
        ul.appendChild(li);
    }
    sessionStorage.removeItem("mandje");
}

async function reserveer(film) {
    const reservatie = {"klantId":`${klant.id}`,"filmId":`${film.id}`};
    const response = await fetch(`reservaties`,
        {
            method: "POST",
            headers: {'Content-Type': "application/json"},
            body: JSON.stringify(reservatie)
        });
    if (response.ok) {
        return ": OK";
    } else {
        switch (response.status) {
            case 404:
                return ": Niet gevonden";
                break;
            case 409:
                return ": Uitverkocht";
                break;
            default:
                return ": Niet gelukt";
        }
    }
}

