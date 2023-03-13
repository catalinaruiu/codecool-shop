export let dataHandler = {

    getProductsByCategory: async function (id) {
        return getApi(`/api/products/category?id=${id}`);
    },

    getProductsBySupplier: async function (id) {
        return getApi(`/api/products/supplier?id=${id}`);
    },

    addProduct: async function (id){
        return getApi(`/api/cart?addId=${id}`);
    },
    removeProduct: async function (id){
        return getApi(`/api/cart?removeId=${id}`);
    },
    getProducts: async function (){
        return getApi(`/api/cart`);
    },
    createNewOrder: async function (userId, quantity, total, status){
        let order = {user_id: userId, quantity: quantity, total: total, status: status}
        return await apiPost("/api/order", order);
    },
    clearCart: async function(){
        return getApi("/api/clear-cart")
    }
}

async function getApi(url) {
    let response = await fetch(url, {
        method: "GET",
    });
    if (response.ok) {
        return await response.json();
    } else {
        console.log("didn't work man/woman");
    }
}

async function apiPost(url, payload) {

    let response = await fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(payload)
    });
    if (response.ok) {
        console.log(response.ok);
    }
}

async function apiPut(url, payload) {
    let response = await fetch(url, {
        method: "PUT",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(payload)
    });
    if (response.ok) {
        return console.log(response.ok);
    }
}

async function apiDelete(url) {
    let response = await fetch(url, {method: "DELETE"});
    console.log(url);
    if (response.ok) {
        return console.log(response.ok);
    }
}