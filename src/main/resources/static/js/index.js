"use strict";
import {byId, toon, setText} from "./util.js";
const response = await fetch("genres");
if (response.ok) {
    const genres = await response.json();
    const ul = byId("genres");
    for (const genre of genres) {
        const li = document.createElement("li");
        const hyperlink = document.createElement("a");
        hyperlink.href = "javascript:void(0)";
        hyperlink.textContent = `${genre.naam}`;
        hyperlink.onclick = () =>
            sessionStorage.setItem("genre", JSON.stringify(genre));
        hyperlink.addEventListener("click", async () => { await findByGenre(genre);})
        li.appendChild(hyperlink);
        ul.appendChild(li);
    }
} else {
    toon("storing");
}

async function findByGenre(genre) {
    const response = await fetch(`genres/${genre.id}/films`);
    setText("genre", `${genre.naam}`);
    toon("genre");
    if (response.ok) {
        document.getElementById("films").innerHTML = "";
        toon("films");
        const films = await response.json();
        const ul = byId("films");
        for (const film of films) {
            const li = document.createElement("li");
            const hyperlink = document.createElement("a");
            const image = document.createElement("img");
            image.src = `images/${film.id}.jpg`
            image.alt = `${film.titel}`;
            hyperlink.href = "film.html";
            hyperlink.onclick = () =>
                sessionStorage.setItem("film", JSON.stringify(film));
            hyperlink.appendChild(image);
            li.appendChild(hyperlink);
            ul.appendChild(li);
        }
    }
}