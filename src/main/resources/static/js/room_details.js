// $(document).ready(function(){
//     var additionalImages = [
//         "/images/1.jpg",
//         "/images/2.jpg",
//         "/images/3.jpg",
//     ];
//
//     var expandedImage = null;
//     var isExpanded = false;
//
//     function addExpandFunctionality(selector) {
//         $(selector).find('img').click(function() {
//             if (expandedImage !== null) {
//                 if (expandedImage.is($(this)) && isExpanded) {
//                     $(this).removeClass('expanded');
//                     $('body').removeClass('expanded-image');
//                     isExpanded = false;
//                     expandedImage = null;
//                     return;
//                 } else {
//                     expandedImage.removeClass('expanded');
//                 }
//             }
//             $(this).addClass('expanded');
//             $('body').addClass('expanded-image');
//             expandedImage = $(this);
//             isExpanded = true;
//         });
//     }
//
//     $('#exampleModal').on('shown.bs.modal', function () {
//         var modalBody = $('#additionalImages');
//         additionalImages.forEach(function(imageSrc) {
//             var img = $('<img>').attr('src', imageSrc).addClass('img-fluid mb-3');
//             modalBody.append(img);
//         });
//         addExpandFunctionality('#exampleModal');
//     });
//
//     addExpandFunctionality('.add-image');
// });

window.onload = function() {
    receiveData();
    printImages();
};

function printImages() {
    const urlParams = new URLSearchParams(window.location.search);
    let room_id = urlParams.get("room_id");

    fetch('/load-room-image?id=' + room_id)
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
                    fetch(`/delete-room-image?url=${data.imageURL[index]}`, {
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
            if(data.isOwner.toString() === 'false'){
                addButton.remove();
            }
            else{
                addButton.onclick=addImages;
            }

        })
        .catch(error => {
            console.error('Error fetching images:', error);
        });
}


const urlParams = new URLSearchParams(window.location.search);
let room_id = urlParams.get("room_id");


let bookButton = document.getElementById("bookButton");
bookButton.onclick = function () {
    if(localStorage.getItem("checkInDate") === null) {
        window.location.href = "/";
        return;
    }
    window.location.href = "/booking?id=" + room_id;
}

// let book_now = document.getElementById("book_now");
// book_now.onclick = function () {
//     if(localStorage.getItem("checkInDate") === null) {
//         window.location.href = "/";
//         return;
//     }
//     window.location.href = "/booking?id=" + room_id.toString();
// }

async function getRoomDetails() {
    try {
        const test = await $.ajax({
            url: `/get-room-details?room_id=${room_id}`,
            method: 'GET',
            dataType: 'json'
        });
        return test;
    } catch (error) {
        alert(error);
    }
}

async function receiveData() {
    let data = await getRoomDetails();
    console.log(data);

    let roomID = document.getElementById("roomID");
    roomID.textContent = "Room " + data.room.roomID.toString();

    let capacity = document.getElementById("capacity");
    capacity.textContent = data.room.numOfPeople;

    let price = document.getElementById("price");
    price.textContent = data.room.price.toString() + '$';
    price.style.color = "red";

    let hotel_id = localStorage.getItem("hotel_id");
    let redirect = document.getElementById("hotel");
    redirect.href = "/hotel-detail?hotel_id=" + hotel_id.toString();
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


function addImages(){
    window.location.href = '/room-image?id=' + room_id;
}

