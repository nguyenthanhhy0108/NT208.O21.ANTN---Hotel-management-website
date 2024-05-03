var checkInDate = localStorage.getItem("checkInDate");
var checkOutDate = localStorage.getItem("checkOutDate");

// alert(checkInDate)

const urlParams = new URLSearchParams(window.location.search);
let hotel_id = urlParams.get("hotel")

async function getListRoom() {
    try {
        const test = await $.ajax({
            url: '/get-list-rooms?hotel_id=',
            method: 'GET',
            dataType: 'json'
        });
        return test;
    } catch (error) {
        alert(error);
    }
}

async function printRooms() {
    let rooms = await getListRoom();
    console.log(rooms);
}