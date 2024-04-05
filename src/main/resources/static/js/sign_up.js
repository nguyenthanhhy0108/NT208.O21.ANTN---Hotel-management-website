function checkPasswordMatch() {
    let password = document.getElementById("exampleInputPassword1").value;
    let confirmPassword = document.getElementById("exampleInputPassword2").value;
    let passwordMatchMessage = document.getElementById("passwordMatchMessage");
    let registrationForm = document.getElementById("registrationForm");

    if (password !== confirmPassword) {
        passwordMatchMessage.innerHTML = "Passwords do not match!";
        passwordMatchMessage.style.display = "block";
        registrationForm.onsubmit = function () { return false; };
    } else {
        passwordMatchMessage.innerHTML = "Passwords match.";
        passwordMatchMessage.style.display = "none";
        registrationForm.onsubmit = function () { return true; };
    }
}
