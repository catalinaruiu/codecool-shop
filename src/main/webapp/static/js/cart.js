import {fillShoppingCard, storeOrder} from "./cartManager.js";

window.addEventListener("load", initializeCart);

async function initializeCart() {
    await fillShoppingCard();
    storeOrder();
}