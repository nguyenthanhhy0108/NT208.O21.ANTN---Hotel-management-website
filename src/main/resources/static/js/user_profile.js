const editButton = document.getElementById("editButton");
const saveButton = document.getElementById("saveButton");
const fullName = document.getElementById("fullName");
const fullNameInput = document.getElementById("fullNameInput");
const email = document.getElementById("email");
const emailInput = document.getElementById("emailInput");
const phone = document.getElementById("phone");
const phoneInput = document.getElementById("phoneInput");
const nationality = document.getElementById("nationality");
const nationalityInput = document.getElementById("nationalityInput");
const address = document.getElementById("address");
const addressInput = document.getElementById("addressInput");


editButton.addEventListener("click", toggleEditMode);
saveButton.addEventListener("click", saveChanges);

function toggleEditMode() {
  fullName.classList.toggle("d-none"); 
  fullNameInput.classList.toggle("d-none");
  email.classList.toggle("d-none"); 
  emailInput.classList.toggle("d-none");
  phone.classList.toggle("d-none"); 
  phoneInput.classList.toggle("d-none");
  nationality.classList.toggle("d-none"); 
  nationalityInput.classList.toggle("d-none");
  address.classList.toggle("d-none"); 
  addressInput.classList.toggle("d-none");
  saveButton.disabled = !saveButton.disabled; 
}

function saveChanges() {
  fullName.textContent = fullNameInput.value;
  email.textContent = emailInput.value;
  toggleEditMode(); 
}

async function callAPI() {
  try {
    const test = await $.ajax({
      url: '/loading-user-page',
      method: 'GET',
      dataType: 'json'
    });
    return test;
  } catch (error) {
    alert(error);
  }
}


async function getAllRequests() {
  alert("abca");
  let test = await callAPI();
  if (test.receivedBookings.length === 0){
    alert("true");
  }
  alert(test.receivedBookings);
}
