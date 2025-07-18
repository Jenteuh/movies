"use strict";
import {byId, setText, toon} from "./util.js";
const klant = JSON.parse(sessionStorage.getItem("klant"));
const mandje = JSON.parse(sessionStorage.getItem("mandje"));
const bestelling = `${mandje.length} film(s) voor ${klant.familienaam} ${klant.voornaam}`;
setText("bestelling", bestelling);

