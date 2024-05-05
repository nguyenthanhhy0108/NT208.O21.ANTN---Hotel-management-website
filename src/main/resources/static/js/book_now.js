async function load () {
    let checkInDate = document.getElementById("check_in_date");
    let checkOutDate = document.getElementById("check_out_date");

    var localCheckInDate = localStorage.getItem("checkInDate");
    var localCheckOutDate = localStorage.getItem("checkOutDate");

    checkInDate.value = localCheckInDate;

    checkOutDate.value = localCheckOutDate;
}

