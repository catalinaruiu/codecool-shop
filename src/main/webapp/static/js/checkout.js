const br = document.createElement("br");
const form = document.createElement("form");
const email = document.createElement("input");
const name = document.createElement("input");
const phone = document.createElement("input");
const submitButton = document.createElement("input");
const billingZip = document.createElement("input");
const billingCity = document.createElement("input");
const billingAddress = document.createElement("input");
const checkbox = document.createElement("input");
const shippingZip = document.createElement("input");
const shippingCity = document.createElement("input");
const shippingAddress = document.createElement("input");
const hr = document.createElement("hr");
const checkoutButton = document.getElementById("checkout");
const label = document.createElement("label");

checkoutButton.addEventListener("click", addCheckOutForm, {once:true});
checkbox.addEventListener("click", copyBillingAddress);

function addCheckOutForm() {
    appendElements();
    setNameAttributes();
    setEmailAttributes();
    setPhoneAttributes();
    setAddressAttributes();
    setButtonAttributes();
    setHrAttributes();
    setFormAttributes();
    setCheckboxAttributes();
    setLabelAttributes();
}

function appendElements() {
    form.appendChild(name);
    form.appendChild(br.cloneNode());
    form.appendChild(email);
    form.appendChild(br.cloneNode());
    form.appendChild(phone);
    form.appendChild(br.cloneNode());
    form.appendChild(billingZip);
    form.appendChild(billingCity);
    form.appendChild(billingAddress);
    form.appendChild(checkbox);
    form.appendChild(label);
    form.appendChild(br.cloneNode());
    form.appendChild(shippingZip);
    form.appendChild(shippingCity);
    form.appendChild(shippingAddress);
    form.appendChild(br.cloneNode());
    form.appendChild(submitButton);
    form.appendChild(br.cloneNode());
    document.getElementById("checkout-form").appendChild(hr);
    document.getElementById("checkout-form").appendChild(form);
}

function setHrAttributes() {
    hr.setAttribute("class", "my-4");
}

function setNameAttributes() {
    name.setAttribute("type", "text");
    name.setAttribute("placeholder", "Name");
    name.required = true;
}

function setEmailAttributes() {
    email.setAttribute("type", "email");
    email.setAttribute("placeholder", "E-Mail");
    email.required = true;
}

function setPhoneAttributes() {
    phone.setAttribute("type", "tel");
    phone.setAttribute("placeholder", "Phone");
    phone.setAttribute("pattern", "[0-9]{12}")
    phone.required = true;
}

function setAddressAttributes() {
    billingAddress.setAttribute("type", "text");
    billingAddress.setAttribute("placeholder", "Billing address");
    billingZip.setAttribute("type", "text");
    billingZip.setAttribute("placeholder", "ZIP code");
    billingZip.setAttribute("pattern", "[0-9]{4}")
    billingCity.setAttribute("type", "text");
    billingCity.setAttribute("placeholder", "City");
    billingAddress.required = true;
    billingZip.required = true;
    billingCity.required = true;

    shippingAddress.setAttribute("type", "string");
    shippingAddress.setAttribute("placeholder", "Shipping address");
    shippingZip.setAttribute("type", "text");
    shippingZip.setAttribute("placeholder", "ZIP code");
    shippingZip.setAttribute("pattern", "[0-9]{4}")
    shippingCity.setAttribute("type", "text");
    shippingCity.setAttribute("placeholder", "City");
    shippingAddress.required = true;
    shippingZip.required = true;
    shippingCity.required = true;
}

function setLabelAttributes() {
    label.setAttribute("for", 'checkbox');
    label.innerHTML = "Shipping address same as billing";
}

function setCheckboxAttributes() {
    checkbox.setAttribute("type", "checkbox");
}

function setButtonAttributes() {
    submitButton.setAttribute("type", "submit");
    submitButton.setAttribute("value", "Go to payment");
    submitButton.setAttribute("class", "btn btn-dark btn-block btn-lg");
}

function setFormAttributes() {
    form.style.backgroundColor = "#eae8e8";
    form.style.paddingTop = "25px";
    form.action = "/payment"
    for (let i = 0; i < form.length-1; i++) {
        if (form[i] !== checkbox) {
            form[i].setAttribute("class", "form-control");
        }
    }
}

function copyBillingAddress() {
    if (checkbox.checked === true) {
        shippingZip.value = billingZip.value;
        shippingCity.value = billingCity.value;
        shippingAddress.value = billingAddress.value;
    } else {
        shippingZip.value = "";
        shippingCity.value = "";
        shippingAddress.value = "";
    }
}

