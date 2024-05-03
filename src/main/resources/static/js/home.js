//Display logout message
function submitLogoutForm() {
    document.getElementById("myForm").submit();
}

function displayHotels(page, hotelsPerPage, hotelNames, hotelPrices, hotelAddresses, numberOfPeople, ids) {
    var startIndex = (page - 1) * hotelsPerPage;
    var endIndex = Math.min(startIndex + hotelsPerPage, hotelNames.length);

    // alert(startIndex);
    // alert(endIndex);

    var hotelListElement = document.getElementById("room");
    hotelListElement.innerHTML = "";

    var wholeTitle = document.createElement("h1");
    wholeTitle.textContent = "Hotels that fit your request";
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
        (function (index){

        var hotelDiv = document.createElement("div");
        hotelDiv.classList.add("col-md-4");
        hotelDiv.classList.add("room");

        var cardDiv = document.createElement("div");
        cardDiv.classList.add("card");
        cardDiv.style.width = "23rem";
        cardDiv.style.height = "30rem";

        var img = document.createElement("img");
        img.src = "/images/room3.jpg";
        img.classList.add("card-img-top");
        img.onclick = function (event) {
            window.location.href = "/hotel-detail?hotel_id=" + ids[index].toString();
        }

        var cardBodyDiv = document.createElement("div");
        cardBodyDiv.classList.add("card-body");

        var title = document.createElement("h3");
        title.classList.add("card-title");
        title.style.fontSize = "150%";
        title.textContent = hotelNames[i];

        var text1 = document.createElement("p");
        text1.classList.add("card-text");
        text1.style.marginTop = "-25%";
        text1.style.marginRight = "-80%";
        text1.style.fontSize = "80%";
        text1.style.color = "#05cd05";
        text1.textContent = "Price only from";

        var text2 = document.createElement("p");
        text2.classList.add("card-text");
        text2.style.marginTop = "-18%";
        text2.style.marginRight = "-80%";
        text2.style.fontSize = "180%";
        text2.style.color = "red";
        text2.textContent = (parseInt(hotelPrices[i]) / 25).toString() + "$";

        var text3 = document.createElement("p");
        text3.classList.add("card-text");
        text3.style.marginTop = "-18%";
        text3.style.marginRight = "-80%";
        text3.style.fontSize = "80%";
        text3.style.color = "#05cd05";
        text3.textContent = "for " + numberOfPeople.toString() + " people";

        var address = document.createElement("p")
        address.classList.add("card-text");
        address.style.marginTop = "-30%";
        // address.style.marginLeft = "-43%";
        address.style.position = "absolute";
        address.style.maxWidth = "50%";
        address.style.fontSize = "80%";
        address.textContent = hotelAddresses[i];

        var bookNowLink = document.createElement("a");
        bookNowLink.href = "/hotel-detail?hotel_id=" + ids[index].toString();
        bookNowLink.classList.add("button");
        bookNowLink.textContent = "More...";

        cardBodyDiv.appendChild(title);
        cardBodyDiv.appendChild(text1);
        cardBodyDiv.appendChild(text2);
        cardBodyDiv.appendChild(text3);
        cardBodyDiv.appendChild(address);
        cardBodyDiv.appendChild(bookNowLink);

        cardDiv.appendChild(img);
        cardDiv.appendChild(cardBodyDiv);

        hotelDiv.appendChild(cardDiv);

        roomsRow.appendChild(hotelDiv);
        })(i);
    }
}



function displayPagination(totalPages, hotelsPerPage, hotels) {
    var paginationElement = document.getElementById("pagination");
    paginationElement.innerHTML = "";

    const urlParams = new URLSearchParams(window.location.search);
    const country = urlParams.get('country');
    const page = parseInt(urlParams.get("page")); // Chuyển đổi page sang số nguyên
    const numPeople = urlParams.get('numberOfPeople');
    const option = urlParams.get('option')

    var previousPage = page - 1;
    if (page === 1) {
        previousPage = 1;
    }

    for (var i = 1; i <= totalPages; i++) {
        var button = document.createElement("button");
        button.textContent = i.toString();
        button.style.padding = "0.5%";
        if (i === page) {
            button.classList.add("hover");
        }
        if (i === page + 2) {
            var button_ = document.createElement("button");
            button_.textContent = 'Next';
            button_.style.padding = "0.5%";
            var nextButton_ = button_;
            button_.onclick = function () {
                if (page < totalPages) {
                    nextButton_.classList.add("hover");
                    var url = '/home?country=' + country + '&page=' + (page + 1) +
                        '&numberOfPeople=' + numPeople + '&option=' + option;
                    window.location.href = url;
                }
            }.bind(null, totalPages, country, page, numPeople, option);
            paginationElement.appendChild(button_);
            break;
        }
        if (i <= page - 2) {
            var button_pre = document.createElement("button");
            button_pre.textContent = 'Previous';
            button_pre.style.padding = "0.5%"
            var nextButton_pre = button_pre;
            button_pre.onclick = function () {
                if (page > 1) {
                    nextButton_pre.classList.add("hover");
                    var url = '/home?country=' + country + '&page=' + (page - 1) +
                        '&numberOfPeople=' + numPeople + '&option=' + option;
                    window.location.href = url;
                }
            }.bind(null, totalPages, country, page, numPeople, option);
            paginationElement.appendChild(button_pre);
            continue;
        }
        button.onclick = function () {
            this.classList.add("hover");
            var url = '/home?country=' + country;
            var newURL = url + "&page=" + this.textContent + "&numberOfPeople=" + numPeople + "&option=" + option;
            window.location.href = newURL;
            callAPI(country, this.textContent);
        };
        paginationElement.appendChild(button);
    }
}


function callAPI(country, page, numofpeople, option){
    let url = '/get-sorted-hotels-details?country=' + country + '&page=' + page + '&numberOfPeople=' + numofpeople + '&option=' + option;

    $.ajax({
        url: url,
        method: 'GET',
        dataType: 'json',
        success: function(data) {
            var hotelsName = data.names;
            var hotelsAddresses = data.addresses;
            var hotelsPrices = data.prices;
            var numberOfPeople = data.numberOfPeople;
            var ids = data.ids;

            // alert(hotelsAddresses)
            // alert(hotelsPrices[0])
            // alert(hotelsName)
            var hotelsPerPage = 6;
            var totalPages = Math.ceil(hotelsName.length / hotelsPerPage);

            displayHotels(page, hotelsPerPage, hotelsName, hotelsPrices, hotelsAddresses, numberOfPeople, ids);
            displayPagination(totalPages, hotelsPerPage, hotelsName);
        },
        error: function(xhr, status, error) {
            alert(status + ': ' + error);
        }
    });
}

function changeOption(option){
    const urlParams = new URLSearchParams(window.location.search);
    var country = urlParams.get('country');
    var page = urlParams.get('page');
    var numberOfPeople = urlParams.get('numberOfPeople');

    var newURL = '/home' + '?country=' + country + '&page=1' + '&numberOfPeople=' + numberOfPeople + '&option=' + option;

    window.location.href = newURL;
}

document.addEventListener("DOMContentLoaded", function() {

    const urlParams = new URLSearchParams(window.location.search);
    const country = urlParams.get('country');
    // alert(country)
    if(country == null){
        window.location.href = "/first-page";
    }
    const numberofpeople = urlParams.get('numberOfPeople')
    const option = urlParams.get('option');
    // alert(numberofpeople)
    var page = parseInt(urlParams.get("page"))

    var sort = document.getElementById("sort_by");
    sort.value = option;

    callAPI(country, page, numberofpeople, option);
});
