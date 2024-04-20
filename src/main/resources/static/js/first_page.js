document.addEventListener("DOMContentLoaded", function() {
    let inp = document.querySelector("#searchInput");
    if (inp) {
        let urlParams = new URLSearchParams(window.location.search);
        const not_exist = urlParams.get('not_exist');
        if (not_exist === "true") {
            inp.placeholder = "Please choose a place in recommended list !!!";
        }
    }
});

//Display logout message
function submitLogoutForm() {
    document.getElementById("myForm").submit();
}


