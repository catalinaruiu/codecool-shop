export function cardBuilder(product) {
    return `<div class="col col-sm-12 col-md-6 col-lg-4">
            <div class="card">
                <img class="" src="/static/img/product_${product.id}.jpg" />
                <div class="card-header">
                    <h4 class="card-title">${product.name}</h4>
                    <p class="card-text">${product.description}</p>
                </div>
                <div class="card-body">
                    <div class="card-text">
                        <p class="lead">${product.defaultPrice} EUR</p>
                    </div>
                    <div class="card-text" id="button-container${product.id}">
                        
                    </div>
                </div>
            </div>
        </div>`
}

export function buttonBuilder(product){
    return `<button id="${product.id}" class="btn btn-success">Add to cart</button>`
}

export function shopCartBuilder(product) {
    return `<div class="row mb-4 d-flex justify-content-between align-items-center">
                <div class="col-md-2 col-lg-2 col-xl-2">
                    <img src="/static/img/product_${product.id}.jpg"
                         class="img-fluid rounded-3">
                </div>
                <div class="col-md-3 col-lg-3 col-xl-3">
                    <h6 class="text-muted">${product.productCategory}</h6>
                    <h6 class="text-black mb-0">${product.name}</h6>
                </div>
                <div class="col-md-3 col-lg-3 col-xl-2 d-flex">
                    <button id="down-button${product.id}" class="btn btn-link px-2"
                            onclick="this.parentNode.querySelector('input[type=number]').stepDown()">
                        <i class="fas fa-minus"></i>
                    </button>

                    <input id="form${product.id}" min="0" name="quantity" value="${product.count}" type="number"
                           class="form-control form-control-sm" />

                    <button id="up-button${product.id}" class="btn btn-link px-2"
                            onclick="this.parentNode.querySelector('input[type=number]').stepUp()">
                        <i class="fas fa-plus"></i>
                    </button>
                </div>
                <div class="col-md-3 col-lg-2 col-xl-2 offset-lg-1">
                    <h6 class="mb-0">EUR ${product.defaultPrice}</h6>
                </div>
                <div class="col-md-1 col-lg-1 col-xl-1 text-end">
                    <a href="#!" class="text-muted"><i class="fas fa-times"></i></a>
                </div>
            </div>`
}