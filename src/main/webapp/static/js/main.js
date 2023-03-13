import {productManager} from "./productManager.js";
import {loadButtons} from "./cartManager.js";

window.addEventListener("load", init);

async function init() {
    productManager.addEventListeners();
    loadButtons();
}