//Display logout message
function submitLogoutForm() {
    document.getElementById("myForm").submit();
}

$(document).ready(function() {
    $.ajax({
        url: '/get-6-hotels-details',
        method: 'GET',
        dataType: 'json',
        success: function(data) {
            // alert(names)
            names = data.allName;
            autocomplete(document.getElementById("searchInput"), countries, names);
        },
        error: function(xhr, status, error) {
            console.error(status + ': ' + error);
        }
    });
});