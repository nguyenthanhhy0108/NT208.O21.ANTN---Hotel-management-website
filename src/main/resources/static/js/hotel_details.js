var checkInDate = localStorage.getItem("checkInDate");
var checkOutDate = localStorage.getItem("checkOutDate");

// alert(checkInDate)

const urlParams = new URLSearchParams(window.location.search);
let hotel_id = urlParams.get("hotel_id");

localStorage.setItem("hotel_id", hotel_id);

window.onload = function (){
    printRooms();
    printImages();
}

async function getListRoom() {
    try {
        const test = await $.ajax({
            url: `/get-list-rooms?hotel_id=${hotel_id}`,
            method: 'GET',
            dataType: 'json'
        });
        return test;
    } catch (error) {
        alert(error);
    }
}

async function printRooms() {
    // let rooms = await getListRoom();
    let data = await getListRoom();

    var isOwner = document.getElementById("isOwner").textContent;
    console.log(isOwner);


    console.log(data);
    // alert(data)

    let roomsList = document.getElementById("rooms");
    let cardHead = document.createElement("div");
    cardHead.classList.add("row");
    cardHead.classList.add("text-end");
    cardHead.classList.add("mb-3");
    cardHead.classList.add("py-2");

    let heading = document.createElement("h1");
    heading.style.marginLeft = "-30%";
    heading.style.fontSize = "24px";
    heading.textContent = "Hotel available rooms";

    cardHead.appendChild(heading);
    roomsList.appendChild(cardHead);

    let fullDataCard = document.createElement("div");
    fullDataCard.classList.add("row");
    fullDataCard.classList.add("py-2");
    fullDataCard.style.marginLeft = "1%";
    fullDataCard.style.fontSize = "15px";
    fullDataCard.style.maxWidth = "99%";
        if (data && data.length > 0) {
            let table = document.createElement("table");

            let row = document.createElement("tr");

            let col1 = document.createElement("td");
            col1.style.overflowWrap = "break-word"
            col1.textContent = "Room ID";

            let col2 = document.createElement("td");
            col2.style.overflowWrap = "break-word"
            col2.textContent = "Room capacity";
            col2.style.position = "absolute";
            col2.style.marginLeft = "-75%"

            let col3 = document.createElement("td");
            col3.style.overflowWrap = "break-word"
            col3.textContent = "Price";
            col3.style.position = "absolute";
            col3.style.marginLeft = "-50%";

            let col4 = document.createElement("td");
            col4.style.position = "absolute"
            col4.style.marginLeft = "-35%";
            col4.style.marginTop = "-1%";
            let hide1 = document.createElement("button");
            hide1.style.display = "none";
            hide1.disabled = true;
            hide1.style.opacity = "0";
            col4.appendChild(hide1);

            let col5 = document.createElement("td");
            col5.style.position = "absolute";
            col5.style.marginLeft = "-20%";
            let hide2 = document.createElement("span");
            hide2.textContent = "Action";
            col5.appendChild(hide2);

            let col6 = document.createElement("td");
            col6.style.position = "absolute"
            col6.style.marginLeft = "3%";
            col6.style.marginTop = "-1%";
            let hide3 = document.createElement("button");
            hide3.style.display = "none";
            hide3.disabled = true;
            hide3.style.opacity = "0";
            col6.appendChild(hide3);

            row.appendChild(col1);
            row.appendChild(col2);
            row.appendChild(col3);
            row.appendChild(col4);
            row.appendChild(col5);
            row.appendChild(col6);

            table.appendChild(row);

            let horizon = document.createElement("hr");
            horizon.style.width = "100%";
            table.appendChild(horizon);

            fullDataCard.appendChild(table);
            roomsList.appendChild(fullDataCard);

            for (let room of data) {
                let fullDataCard = document.createElement("div");
                fullDataCard.classList.add("row");
                fullDataCard.classList.add("py-2");
                fullDataCard.style.marginLeft = "1%";
                fullDataCard.style.fontSize = "15px";
                fullDataCard.style.maxWidth = "99%";

                let table = document.createElement("table");

                let row = document.createElement("tr");

                let col1 = document.createElement("td");
                // col1.style.borderRight = "1px solid black";
                col1.style.padding = "20px";
                col1.style.overflowWrap = "break-word"
                col1.textContent = room.roomID.toString();

                let col2 = document.createElement("td");
                // col2.style.borderRight = "1px solid black";
                col2.style.padding = "20px";
                col2.style.overflowWrap = "break-word"
                col2.textContent = room.numOfPeople.toString();
                col2.style.position = "absolute";
                col2.style.marginLeft = "-73%"

                let col3 = document.createElement("td");
                // col3.style.borderRight = "1px solid black";
                col3.style.padding = "20px";
                col3.style.overflowWrap = "break-word"
                col3.textContent = room.price.toString();
                col3.style.position = "absolute";
                col3.style.marginLeft = "-52%";

                let col4 = document.createElement("td");
                col4.style.position = "absolute"
                col4.style.marginLeft = "-41%";
                col4.style.marginTop = "-1%";
                col4.style.paddingTop = "20px";
                let hide1 = document.createElement("button");
                // hide1.style.display = "none";
                hide1.type = "button";
                hide1.classList.add("btn");
                hide1.classList.add("btn-success");
                hide1.style.backgroundColor = "blue";
                // hide1.classList.add("mr-2");
                hide1.textContent = "More information";
                hide1.onclick = function () {
                    window.location.href = "/room-details?room_id=" + room.roomID.toString();
                }
                // hide1.disabled = true;
                // hide1.style.opacity = "0";
                col4.appendChild(hide1);

                let col5 = document.createElement("td");
                col5.style.position = "absolute";
                // col5.style.paddingLeft = "17%";
                col5.style.marginLeft = "-20.8%";
                col5.style.marginTop = "-1%";
                col5.style.paddingTop = "20px";
                let hide2 = document.createElement("button");
                // hide2.style.display = "none";
                hide2.type = "button";
                hide2.classList.add("btn");
                hide2.classList.add("btn-success");
                // hide2.classList.add("mr-2");
                hide2.textContent = "Book now";
                hide2.onclick = function () {
                    if(localStorage.getItem("checkInDate") === null) {
                        window.location.href = "/";
                        return;
                    }
                    window.location.href = "/booking?id=" + room.roomID.toString();
                }
                // hide2.disabled = true;
                // hide2.style.opacity = "0";
                col5.appendChild(hide2);

                let col6 = document.createElement("td");
                col6.style.position = "absolute";
                // col5.style.paddingLeft = "17%";
                col6.style.marginLeft = "-8%";
                col6.style.marginTop = "-1%";
                col6.style.paddingTop = "20px";
                let hide3 = document.createElement("button");
                hide3.type = "button";
                hide3.classList.add("btn");
                hide3.classList.add("btn-danger");
                hide3.textContent = "Delete";
                hide3.onclick = function () {
                    if(localStorage.getItem("checkInDate") === null) {
                        window.location.href = "/";
                        return;
                    }
                    window.location.href = "/delete-room?room_id=" + room.roomID.toString();
                }
                if (isOwner === "true"){
                    col6.appendChild(hide3);
                }
                row.appendChild(col1);
                row.appendChild(col2);
                row.appendChild(col3);
                row.appendChild(col4);
                row.appendChild(col5);
                row.appendChild(col6);

                table.appendChild(row);

                let horizon = document.createElement("hr");
                horizon.style.width = "100%";
                table.appendChild(horizon);

                fullDataCard.appendChild(table);
                roomsList.appendChild(fullDataCard);
            }
        }
    else {
            let table = document.createElement("table");

            let row = document.createElement("tr");

            let col1 = document.createElement("td");
            col1.style.overflowWrap = "break-word"
            col1.textContent = "There are no available rooms !!!";
            col1.style.color = "orange";
            col1.style.paddingLeft = "40%";

            row.appendChild(col1);
            table.appendChild(row);

            let horizon = document.createElement("hr");
            horizon.style.width = "100%";
            table.appendChild(horizon);

            fullDataCard.appendChild(table);
            roomsList.appendChild(fullDataCard);
    }
}

async function clickContact() {
    let urlParams = new URLSearchParams(window.location.search);
    let hotelID = urlParams.get('hotel_id');

    let status = await createChatRoom(hotelID);
    window.location.href = '/chat';
}

async function createChatRoom(hotelID) {

    $.ajax({
        url: '/create-chat-room?hotelID=' + hotelID.toString(),
        type: 'POST',
        dataType: 'json',
        success: function(response) {
            return response.status;
        },
        error: function(xhr, status, error) {
            console.error('Error:', error);
        }
    });
}


function printImages() {
    const urlParams = new URLSearchParams(window.location.search);
    let hotel_id = urlParams.get("hotel_id");

    fetch('/load-hotel-image?id=' + hotel_id)
        .then(response => response.json())
        .then(data => {

            // Get the carousel inner container
            var carouselInner = document.querySelector('.carousel-inner');

            // Clear any existing images
            carouselInner.innerHTML = '';
            // Iterate over the image URLs and create corresponding <img> tags
            for (let index = 0; index < data.imageURL.length; index++){
                var imgDiv = document.createElement('div');
                imgDiv.classList.add('carousel-item');
                if (index === 0) {
                    imgDiv.classList.add('active');
                }

                var img = document.createElement('img');
                img.src = data.imageURL[index];
                img.alt = 'Image ' + (index + 1);
                img.classList.add('d-block', 'w-50', 'img-fluid'); // Make the image responsive
                img.style.height = 'auto'; // Maintain aspect ratio

                var button = document.createElement('button');
                button.innerText = 'Delete this image'; // Set button text
                button.classList.add('btn', 'btn-primary', 'mt-2', 'mx-auto', 'd-block'); // Add Bootstrap button classes
                button.setAttribute('type', 'button'); // Set button type attribute

                button.addEventListener("click", function() {
                    fetch(`/delete-hotel-image?url=${data.imageURL[index]}`, {
                        method: "DELETE"
                    })
                        .then(response => {
                            if (response.ok) {
                                popupDialog("Success", "Successfully deleted the images!");
                                setTimeout(function () {
                                    console.log("Waiting for 5 seconds");
                                    window.location.reload();
                                }, 500);
                            } else {
                                // Access the response text by chaining another .then() block
                                response.text().then(errorMessage => {
                                    // Alert the error message
                                    popupDialog("Error", errorMessage);
                                    setTimeout(function () {
                                        console.log("Waiting for 5 seconds");
                                        window.location.reload();
                                    }, 500);
                                });
                            }
                        });
                });

                imgDiv.appendChild(img);
                if(data.isOwner === true){
                    imgDiv.appendChild(button);
                }
                carouselInner.appendChild(imgDiv);
            }

            let addButton = document.getElementById("imageButton");
            if(data.isOwner === true){
                addButton.onclick=addImages;
            }
            else{
                addButton.remove();
            }
        })
        .catch(error => {
            console.error('Error fetching images:', error);
        });
}

function addImages(){
    window.location.href = '/hotel-image?id=' + hotel_id;
}

function popupDialog(title, content) {
    var dialog = document.getElementById('dialog');
    var titleElement = document.getElementById('dialogTitle');
    var contentElement = document.getElementById('dialogContent');

    titleElement.textContent = title;
    contentElement.textContent = content;

    if (title === 'Error') {
        titleElement.style.background = 'rgb(243 49 49)';
    } else if (title === 'Success') {
        titleElement.style.background = '#57c463';
    }

    dialog.showModal()
    dialog.classList.add('show')

    dialog.addEventListener('click', function (event) {
        if (event.target == dialog) {
            dialog.classList.add('hide');
            dialog.addEventListener('webkitAnimationEnd', function () {
                    dialog.classList.remove('hide');
                    dialog.close();
                    dialog.removeEventListener('webkitAnimationEnd', arguments.callee, false);
                }
                , false);
        }
    });
}



