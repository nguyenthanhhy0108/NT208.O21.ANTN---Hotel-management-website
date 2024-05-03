$(document).ready(function(){
    var additionalImages = [
        "/images/2.jpg",
        "/images/3.jpg",
        "/images/back.jpg",
    ];

    var expandedImage = null;
    var isExpanded = false;

    function addExpandFunctionality(selector) {
        $(selector).find('img').click(function() {
            // Kiểm tra trạng thái của ảnh
            if (expandedImage !== null) {
                if (expandedImage.is($(this)) && isExpanded) {
                    $(this).removeClass('expanded');
                    $('body').removeClass('expanded-image');
                    isExpanded = false;
                    expandedImage = null;
                    return;
                } else {
                    expandedImage.removeClass('expanded');
                }
            }
            $(this).addClass('expanded');
            $('body').addClass('expanded-image');
            expandedImage = $(this);
            isExpanded = true;
        });
    }

    $('#exampleModal').on('shown.bs.modal', function () {
        var modalBody = $('#additionalImages');
        additionalImages.forEach(function(imageSrc) {
            var img = $('<img>').attr('src', imageSrc).addClass('img-fluid mb-3');
            modalBody.append(img);
        });
        addExpandFunctionality('#exampleModal');
    });

    addExpandFunctionality('.add-image');
});

const urlParams = new URLSearchParams(window.location.search);
let room_id = urlParams.get("room_id");

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