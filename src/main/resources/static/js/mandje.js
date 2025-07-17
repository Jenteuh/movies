"use strict";
import {byId} from "./util.js";
const mandje = JSON.parse(sessionStorage.getItem("mandje"));

const mandjeBody = byId("mandjeBody");
for (const film of mandje){
    const tr = mandjeBody.insertRow();
    tr.insertCell().textContent = film.titel;
    tr.insertCell().textContent = film.prijs;
}