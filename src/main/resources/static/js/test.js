$(document).ready(function() {
    // Gọi API /suggest khi trang HTML được tải
    $.ajax({
        url: '/suggest',
        method: 'GET',
        dataType: 'json',
        success: function(data) {
            alert(data)
            // Xử lý dữ liệu ở đây (ví dụ: hiển thị danh sách gợi ý)
        },
        error: function(xhr, status, error) {
            console.error(status + ': ' + error); // Log lỗi nếu có
        }
    });
});




// function extractHotelNames(hotels) {
//         var hotelNames = hotels.map(function(hotel) {
//             return hotel.name;
//         });
//         return hotelNames;
//     }
// });
