//Display logout message
function submitLogoutForm() {
    document.getElementById("myForm").submit();
}

function displayHotels(page, hotelsPerPage, hotels) {
    var startIndex = (page - 1) * hotelsPerPage;
    var endIndex = Math.min(startIndex + hotelsPerPage, hotels.length);

    // alert(startIndex);
    // alert(endIndex);

    var hotelListElement = document.getElementById("room");
    hotelListElement.innerHTML = "";

    var wholeTitle = document.createElement("h1");
    wholeTitle.textContent = "Hotels fit your request";
    wholeTitle.classList.add("pt-5");
    wholeTitle.classList.add("text-center");
    wholeTitle.classList.add("headings");
    hotelListElement.appendChild(wholeTitle);
    hotelListElement.appendChild(document.createElement("br"));

    var roomsRow = document.createElement("div");
    roomsRow.classList.add("row");
    roomsRow.classList.add("our-Rooms");
    roomsRow.classList.add("px-4");
    hotelListElement.appendChild(roomsRow);

    for (var i = startIndex; i < endIndex; i++) {

        var hotelDiv = document.createElement("div");
        hotelDiv.classList.add("col-md-4");
        hotelDiv.classList.add("room");

        var cardDiv = document.createElement("div");
        cardDiv.classList.add("card");
        cardDiv.style.width = "23rem";
        cardDiv.style.height = "30rem";

        var img = document.createElement("img");
        img.src = "/images/room1.jpg";
        img.classList.add("card-img-top");

        var cardBodyDiv = document.createElement("div");
        cardBodyDiv.classList.add("card-body");

        var title = document.createElement("h5");
        title.classList.add("card-title");
        title.textContent = hotels[i];

        var description = document.createElement("p");
        description.classList.add("card-text");
        description.textContent = "Description";

        var bookNowLink = document.createElement("a");
        bookNowLink.href = "/first-page";
        bookNowLink.classList.add("button");
        bookNowLink.textContent = "Book Now";

        cardBodyDiv.appendChild(title);
        cardBodyDiv.appendChild(description);
        cardBodyDiv.appendChild(bookNowLink);

        cardDiv.appendChild(img);
        cardDiv.appendChild(cardBodyDiv);

        hotelDiv.appendChild(cardDiv);

        roomsRow.appendChild(hotelDiv);
    }
}



function displayPagination(totalPages, hotelsPerPage, hotels) {
    var paginationElement = document.getElementById("pagination");
    paginationElement.innerHTML = "";

    const urlParams = new URLSearchParams(window.location.search);
    const country = urlParams.get('country');
    const page = urlParams.get("page");

    for (var i = 1; i <= totalPages; i++) {
        var button = document.createElement("button");
        button.textContent = i;
        button.onclick = function() {
            // alert(this.textContent);
            var url = 'http://localhost:8080/home?country=abc';
            var newURL = url + "&page=" + this.textContent;
            window.location.href = newURL;

            // alert(this.textContent);

            callAPI(country, this.textContent);
        };
        paginationElement.appendChild(button);
    }
}

function callAPI(country, page){
    let url = '/get-sorted-hotels-details?country=' + country + '&page=' + page;

    $.ajax({
        url: url,
        method: 'GET',
        dataType: 'json',
        success: function(data) {
            hotelsName = data.names
            // alert(hotelsName)
            var hotelsPerPage = 6;
            var totalPages = Math.ceil(hotelsName.length / hotelsPerPage);

            displayHotels(page, hotelsPerPage, hotelsName);
            displayPagination(totalPages, hotelsPerPage, hotelsName);
        },
        error: function(xhr, status, error) {
            alert(status + ': ' + error);
        }
    });
}

document.addEventListener("DOMContentLoaded", function() {

    const urlParams = new URLSearchParams(window.location.search);
    const country = urlParams.get('country');

    var page = parseInt(urlParams.get("page"))

    callAPI(country, page);
});