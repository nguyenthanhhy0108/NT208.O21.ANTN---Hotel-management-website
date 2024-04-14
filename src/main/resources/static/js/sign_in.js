//Display login message when loading login page
function showLoginMessage() {
    const urlParams = new URLSearchParams(window.location.search);
    const successful = urlParams.get('successful');
    if (successful === 'true') {
        document.getElementById("password-successMessage").style.display = "block";
    }
}

window.onload = showLoginMessage;