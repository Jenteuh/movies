"use strict";
import {byId, toon, verberg} from "./util.js";
byId("zoek").onclick = async () => {
    verberg("klantenTable", "storing");
    const stukNaamInput = byId("stukNaam");
    findByStukNaam(stukNaamInput.value);
};

async function findByStukNaam(stukNaam) {
    var response = await fetch(
        `klanten?stukNaam=${stukNaam}`);
    if (response.ok) {
        const klanten = await response.json();
        toon("klantenTable");
        const klantenBody = byId("klantenBody");
        klantenBody.innerHTML = "";
        for (const klant of klanten) {
            const tr = klantenBody.insertRow();
            const hyperlink = document.createElement("a");
            hyperlink.href = "bevestig.html";
            hyperlink.textContent = klant.familienaam + " " + klant.voornaam;
            hyperlink.onclick = () =>
                sessionStorage.setItem("klant", JSON.stringify(klant));
            tr.insertCell().appendChild(hyperlink);
            tr.insertCell().textContent = klant.straatNummer;
            tr.insertCell().textContent = klant.postcode;
            tr.insertCell().textContent = klant.gemeente;
        }
    } else {
        toon("storing");
    }
}