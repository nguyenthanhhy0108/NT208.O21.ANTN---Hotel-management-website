document.getElementById("checkoutDate").addEventListener("change", function() {
    var checkinDate = new Date(document.getElementById("checkinDate").value);
    var checkoutDate = new Date(this.value);

    var currentDate = new Date();

    var twoMonthsLater = new Date();
    twoMonthsLater.setMonth(currentDate.getMonth() + 1);

    if (checkoutDate <= checkinDate) {
        let inp = document.querySelector("#alertAreaCheking");
        inp.value = "Check out date must be greater than check in date !!!";
        let out = document.getElementById("checkoutDate");
        out.value = "";

        // location.reload();
    }


    if(checkinDate < currentDate || checkoutDate < currentDate){
        let inp = document.querySelector("#alertAreaCheking");
        inp.value = "Check in and check out day must greater than current date !!!";
        var inDate = document.getElementById("checkinDate");
        var outDate = document.getElementById("checkoutDate");
        outDate.value = "";
        inDate.value = "";

        // location.reload();
    }

    if (checkinDate < twoMonthsLater && checkoutDate < twoMonthsLater) {
        return;
    } else {
        let inp = document.querySelector("#alertAreaCheking");
        inp.placeholder = "Check in and check out day must around one months next !!!";
        let inDate = document.getElementById("checkinDate");
        let outDate = document.getElementById("checkoutDate");
        outDate.value = "";
        inDate.value = "";

        // location.reload();
    }
});


