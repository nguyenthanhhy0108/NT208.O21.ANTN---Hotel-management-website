function printData() {
    const urlParams = new URLSearchParams(window.location.search);

    let amount = document.getElementById("amount");
    amount.value = urlParams.get("price");
}