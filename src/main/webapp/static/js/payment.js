import {dataHandler} from "./dataManager.js";

function clear(){
    let payBtn=document.getElementById("checkout")
    payBtn.addEventListener("click",async ()=>{
        console.log("ajunge aici")
        let req =await dataHandler.clearCart();
        window.location.assign("http://localhost:8080/")

    })
}
clear()
