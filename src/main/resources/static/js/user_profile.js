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

function resizeTable(){
  const cols = document.getElementsByClassName("col-sm-2");
  const width = 12/cols.length;
  const newClass = "col-sm-" + width.toString();

  for (col in cols){
    col.classList.remove("col-sm-2");
    col.classList.add(newClass);
  }
}

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


function formatDate(dateString) {
  let date = new Date(dateString);
  let day = date.getDate();
  let month = date.getMonth() + 1;
  let year = date.getFullYear();
  let formattedDate = day + '/' + month + '/' + year;
  return formattedDate;
}

async function printData() {
  // alert("abc")
  data = await callAPI();

  // console.log(data);
  // alert(data)

  let requestList = document.getElementById("my-requests");
  let cardHead = document.createElement("div");
  cardHead.classList.add("row");
  cardHead.classList.add("text-end");
  cardHead.classList.add("mb-3");
  cardHead.classList.add("py-2");

  let heading = document.createElement("h1");
  heading.style.paddingLeft = "35%";
  heading.style.fontSize = "24px";
  heading.textContent = "Your sent requests";

  cardHead.appendChild(heading);
  requestList.appendChild(cardHead);

  let fullDataCard = document.createElement("div");
  fullDataCard.classList.add("row");
  fullDataCard.classList.add("py-2");
  // fullDataCard.style.marginLeft = "1%";
  fullDataCard.style.fontSize = "12px";
  fullDataCard.style.maxWidth = "99%";

  let table = document.createElement("table");

  let row = document.createElement("tr");

  let col1 = document.createElement("td");
  // col1.style.borderRight = "1px solid black";
  col1.style.padding = "10px";
  col1.style.overflowWrap = "break-word"
  col1.textContent = "Check in date";

  let col2 = document.createElement("td");
  // col2.style.borderRight = "1px solid black";
  col2.style.padding = "10px";
  col2.style.overflowWrap = "break-word"
  col2.textContent = "Check out date";

  let col3 = document.createElement("td");
  // col3.style.borderRight = "1px solid black";
  col3.style.padding = "10px";
  col3.style.overflowWrap = "break-word"
  col3.textContent = "Customer Name";

  let col4 = document.createElement("td");
  // col4.style.borderRight = "1px solid black";
  col4.style.padding = "10px";
  col4.style.overflowWrap = "break-word"
  col4.textContent = "Hotel name";

  let col5 = document.createElement("td");
  // col5.style.borderRight = "1px solid black";
  col5.style.padding = "10px";
  col5.style.overflowWrap = "break-word"
  col5.textContent = "Room ID";

  let col6 = document.createElement("td");
  // col6.style.borderRight = "1px solid black";
  col6.style.padding = "10px";
  col6.style.overflowWrap = "break-word"
  col6.textContent = "Total Price";

  let col7 = document.createElement("td");
  col7.style.padding = "10px";
  col7.style.overflowWrap = "break-word"
  col7.textContent = "Status";

  let col8 = document.createElement("td");
  let hide1 = document.createElement("span");
  // hide1.style.display = "none";
  // hide1.type = "button";
  // hide1.classList.add("btn");
  // hide1.classList.add("btn-success");
  // hide1.classList.add("mr-2");
  // hide1.textContent = "Confirm";
  hide1.disabled = true;
  hide1.style.opacity = "0";
  col8.appendChild(hide1);

  let col9 = document.createElement("td");
  let hide2 = document.createElement("span");
  // hide2.style.display = "none";
  // hide2.type = "button";
  // hide2.classList.add("btn");
  // hide2.classList.add("btn-success");
  // hide2.classList.add("mr-2");
  // hide2.textContent = "Confirm";
  hide2.disabled = true;
  hide2.style.opacity = "0";
  col9.appendChild(hide2);

  row.appendChild(col1);
  row.appendChild(col2);
  row.appendChild(col3);
  row.appendChild(col4);
  row.appendChild(col5);
  row.appendChild(col6);
  row.appendChild(col7);
  row.appendChild(col8);
  row.appendChild(col9);

  table.appendChild(row);

  let horizon = document.createElement("hr");
  horizon.style.width = "720%";
  table.appendChild(horizon);

  for(let i = 0; i < data.sentHotelNames.length; i++) {

    let row = document.createElement("tr");

    let col1 = document.createElement("td");
    // col1.style.borderRight = "1px solid black";
    col1.style.padding = "30px";
    col1.style.overflowWrap = "break-word"
    col1.textContent = formatDate(data.sentBookings[i].checkInDate);

    let col2 = document.createElement("td");
    // col2.style.borderRight = "1px solid black";
    col2.style.padding = "10px";
    col2.style.overflowWrap = "break-word"
    col2.textContent = formatDate(data.sentBookings[i].checkOutDate);

    let col3 = document.createElement("td");
    // col3.style.borderRight = "1px solid black";
    col3.style.padding = "10px";
    col3.style.overflowWrap = "break-word"
    col3.textContent = data.customerName;

    let col4 = document.createElement("td");
    // col4.style.borderRight = "1px solid black";
    col4.style.padding = "10px";
    col4.style.overflowWrap = "break-word"
    col4.textContent = data.sentHotelNames[i];

    let col5 = document.createElement("td");
    // col5.style.borderRight = "1px solid black";
    col5.style.padding = "10px";
    col5.style.overflowWrap = "break-word"
    col5.textContent = data.sentBookings[i].roomId;

    let col6 = document.createElement("td");
    // col6.style.borderRight = "1px solid black";
    col6.style.padding = "10px";
    col6.style.overflowWrap = "break-word"
    col6.textContent = data.sentBookings[i].totalPrice;

    let col7 = document.createElement("td");
    col7.style.padding = "10px";
    col7.style.overflowWrap = "break-word";
    if(data.sentBookings[i].isAccepted === 1) {
      col7.textContent = "Accepted";
      col7.style.color = "green";
    }
    if(data.sentBookings[i].isAccepted === 0) {
      col7.textContent = "Waiting";
      col7.style.color = "blue";
    }
    if(data.sentBookings[i].isAccepted === 2) {
      col7.textContent = "Refused";
      col7.style.color = "red";
    }
    if(data.sentBookings[i].isAccepted === 3) {
      col7.textContent = "Completed";
      col7.style.color = "green";
    }

    let col8 = document.createElement("td");
    let confirmButton = document.createElement("span");
    // confirmButton.type = "button";
    // confirmButton.classList.add("btn");
    // confirmButton.classList.add("btn-success");
    // confirmButton.classList.add("mr-2");
    // confirmButton.textContent = "Confirm";
    confirmButton.style.opacity = "0";
    col8.appendChild(confirmButton);

    let col9 = document.createElement("td");
    let cancelButton = document.createElement("span");
    cancelButton.type = "button";
    cancelButton.classList.add("btn");
    cancelButton.classList.add("btn-danger");
    cancelButton.textContent = "Cancel";

    let col10 = document.createElement("td");
    col10.style.paddingLeft = "2%";
    let paymentButton = document.createElement("span");
    paymentButton.type = "button";
    paymentButton.classList.add("btn");
    paymentButton.classList.add("btn-success");
    paymentButton.textContent = "Pay";
    if(data.sentBookings[i].isAccepted != 1) {
      paymentButton.style.opacity = "0";
      paymentButton.style.cursor = "not-allowed";
      paymentButton.style.pointerEvents = "none";
    }
    if (data.sentBookings[i].isAccepted == 1 && data.sentBookings[i].isPaid == 1)
    {
      paymentButton.textContent = "Paid";
      paymentButton.style.cursor = "not-allowed";
      paymentButton.style.pointerEvents = "none";
    }

    paymentButton.onclick = function () {
      window.location.href = "/pay?booking_id=" + data.sentBookings[i].bookingId + "&price=" + data.sentBookings[i].totalPrice;
    }

    if(data.sentBookings[i].isAccepted === 1 || data.sentBookings[i].isAccepted === 2) {
      cancelButton.style.opacity = "0";
    }

    cancelButton.addEventListener("click", function() {
      const bookingId = data.sentBookings[i].bookingId; // Get the booking ID

      fetch(`/delete?id=${bookingId}`, {
        method: "DELETE"
      })
          .then(response => {
            if (response.ok) {
              popupDialog("Success", "Successfully deleted your booking!");
              setTimeout(function() {console.log("Waiting for 5 seconds"); window.location.reload();}, 2000);
            } else {
              // Access the response text by chaining another .then() block
              response.text().then(errorMessage => {
                // Alert the error message
                popupDialog("Error", errorMessage);
                setTimeout(function() {console.log("Waiting for 5 seconds"); window.location.reload();}, 2000);
              });
            }
          });
  });

    if(data.sentBookings[i].isAccepted === 0) {
      col9.appendChild(cancelButton);
    }
    else {
      let hide2 = document.createElement("span");
      hide2.style.opacity = "0";
      col9.appendChild(hide2);
    }

    col10.appendChild(paymentButton);

    row.appendChild(col1);
    row.appendChild(col2);
    row.appendChild(col3);
    row.appendChild(col4);
    row.appendChild(col5);
    row.appendChild(col6);
    row.appendChild(col7);
    row.appendChild(col8);
    row.appendChild(col9);
    row.appendChild(col10);

    let horizon = document.createElement("hr");
    horizon.style.width = "720%";

    table.appendChild(row);
    table.appendChild(horizon);
    fullDataCard.appendChild(table);
    requestList.appendChild(fullDataCard);
  }

  if (data.roleOwner === false) {
    let toRemove = document.getElementById("owner");
    if (toRemove) {
      let child1 = document.getElementById("child1");
      if (child1) {
        let child = document.getElementById("my-received-requests");
        if (child) {
          child1.removeChild(child);
        }
        toRemove.removeChild(child1);
      }
      toRemove.remove();
    }
  }

  if(data.roleOwner === true) {
    let receivedRequestList = document.getElementById("my-received-requests");
    let cardHead1 = document.createElement("div");
    cardHead1.classList.add("row");
    cardHead1.classList.add("text-end");
    cardHead1.classList.add("mb-3");
    cardHead1.classList.add("py-2");

    let heading1 = document.createElement("h1");
    heading1.style.paddingLeft = "33%";
    heading1.style.fontSize = "24px";
    heading1.textContent = "Your received requests";

    cardHead1.appendChild(heading1);
    receivedRequestList.appendChild(cardHead1);

    let fullDataCard1 = document.createElement("div");
    fullDataCard1.classList.add("row");
    fullDataCard1.classList.add("py-2");
    // fullDataCard.style.marginLeft = "1%";
    fullDataCard1.style.fontSize = "12px";
    fullDataCard1.style.maxWidth = "99%";

    let table1 = document.createElement("table");

    let row1 = document.createElement("tr");

    let col11 = document.createElement("td");
    // col1.style.borderRight = "1px solid black";
    col11.style.padding = "10px";
    col11.style.overflowWrap = "break-word"
    col11.textContent = "Check in date";

    let col21 = document.createElement("td");
    // col2.style.borderRight = "1px solid black";
    col21.style.padding = "10px";
    col21.style.overflowWrap = "break-word"
    col21.textContent = "Check out date";

    let col31 = document.createElement("td");
    // col3.style.borderRight = "1px solid black";
    col31.style.padding = "10px";
    col31.style.overflowWrap = "break-word"
    col31.textContent = "Customer Name";

    let col41 = document.createElement("td");
    // col4.style.borderRight = "1px solid black";
    col41.style.padding = "10px";
    col41.style.overflowWrap = "break-word"
    col41.textContent = "Hotel name";

    let col51 = document.createElement("td");
    // col5.style.borderRight = "1px solid black";
    col51.style.padding = "10px";
    col51.style.overflowWrap = "break-word"
    col51.textContent = "Room ID";

    let col61 = document.createElement("td");
    // col6.style.borderRight = "1px solid black";
    col61.style.padding = "10px";
    col61.style.overflowWrap = "break-word"
    col61.textContent = "Total Price";

    let col71 = document.createElement("td");
    col71.style.padding = "10px";
    col71.style.overflowWrap = "break-word"
    col71.textContent = "Status";

    let col81 = document.createElement("td");
    col81.style.padding = "10px";
    col81.style.overflowWrap = "break-word"
    col81.textContent = "Payment Status";

    let col91 = document.createElement("td");
    let hide11 = document.createElement("span");
    // hide1.style.display = "none";
    // hide1.type = "button";
    // hide1.classList.add("btn");
    // hide1.classList.add("btn-success");
    // hide1.classList.add("mr-2");
    // hide1.textContent = "Confirm";
    hide11.disabled = true;
    hide11.style.opacity = "0";
    col91.appendChild(hide11);

    let col101 = document.createElement("td");
    let hide21 = document.createElement("span");
    // hide2.style.display = "none";
    // hide2.type = "button";
    // hide2.classList.add("btn");
    // hide2.classList.add("btn-success");
    // hide2.classList.add("mr-2");
    // hide2.textContent = "Confirm";
    hide21.disabled = true;
    hide21.style.opacity = "0";
    col101.appendChild(hide21);

    row1.appendChild(col11);
    row1.appendChild(col21);
    row1.appendChild(col31);
    row1.appendChild(col41);
    row1.appendChild(col51);
    row1.appendChild(col61);
    row1.appendChild(col71);
    row1.appendChild(col81);
    row1.appendChild(col91);
    row1.appendChild(col101);

    table1.appendChild(row1);

    let horizon1 = document.createElement("hr");
    horizon1.style.width = "650%";
    table1.appendChild(horizon1);

    for(let i = 0; i < data.receivedHotelNames.length; i++) {

      let row = document.createElement("tr");

      let col1 = document.createElement("td");
      // col1.style.borderRight = "1px solid black";
      col1.style.padding = "30px";
      col1.style.overflowWrap = "break-word"
      col1.textContent = formatDate(data.receivedBookings[i].checkInDate);

      let col2 = document.createElement("td");
      // col2.style.borderRight = "1px solid black";
      col2.style.padding = "10px";
      col2.style.overflowWrap = "break-word"
      col2.textContent = formatDate(data.receivedBookings[i].checkOutDate);

      let col3 = document.createElement("td");
      // col3.style.borderRight = "1px solid black";
      col3.style.padding = "10px";
      col3.style.overflowWrap = "break-word"
      col3.textContent = data.receivedCustomerNames[i];

      let col4 = document.createElement("td");
      // col4.style.borderRight = "1px solid black";
      col4.style.padding = "10px";
      col4.style.overflowWrap = "break-word"
      col4.textContent = data.receivedHotelNames[i];

      let col5 = document.createElement("td");
      // col5.style.borderRight = "1px solid black";
      col5.style.padding = "10px";
      col5.style.overflowWrap = "break-word"
      col5.textContent = data.receivedBookings[i].roomId;

      let col6 = document.createElement("td");
      // col6.style.borderRight = "1px solid black";
      col6.style.padding = "10px";
      col6.style.overflowWrap = "break-word"
      col6.textContent = data.receivedBookings[i].totalPrice;

      let col7 = document.createElement("td");
      col7.style.padding = "10px";
      col7.style.overflowWrap = "break-word";
      if(data.receivedBookings[i].isAccepted === 1) {
        col7.textContent = "Accepted";
        col7.style.color = "green";
      }
      if(data.receivedBookings[i].isAccepted === 0) {
        col7.textContent = "Waiting";
        col7.style.color = "blue";
      }
      if(data.receivedBookings[i].isAccepted === 2) {
        col7.textContent = "Refused";
        col7.style.color = "red";
      }
      if(data.receivedBookings[i].isAccepted === 3) {
        col7.textContent = "Completed";
        col7.style.color = "green";
      }

      let col8 = document.createElement("td");
      // col6.style.borderRight = "1px solid black";
      col8.style.padding = "10px";
      col8.style.overflowWrap = "break-word";
      if(data.receivedBookings[i].isPaid == 0) {
        col8.textContent = "Not paid yet";
        col8.style.color = "red";
      }
      if(data.receivedBookings[i].isPaid == 1) {
        col8.textContent = "Already Paid";
        col8.style.color = "Green";
      }

      let col9 = document.createElement("td");
      let confirmButton = document.createElement("button");
      confirmButton.type = "button";
      confirmButton.classList.add("btn");
      confirmButton.classList.add("btn-success");
      confirmButton.classList.add("mr-2");
      confirmButton.textContent = "Confirm";

      confirmButton.addEventListener("click", function() {
        const bookingId = data.receivedBookings[i].bookingId; // Get the booking ID

          fetch(`/accept?id=${bookingId}`, {
            method: "POST"
          })
              .then(response => {
                if (response.ok) {
                  popupDialog("Success", "Successfully accepted customer booking!");
                  setTimeout(function() {console.log("Waiting for 5 seconds"); window.location.reload();}, 2000);
                } else {
                  // Access the response text by chaining another .then() block
                  response.text().then(errorMessage => {
                    // Alert the error message
                    popupDialog("Error", errorMessage);
                    setTimeout(function() {console.log("Waiting for 5 seconds"); window.location.reload();}, 2000);
                  });
                }
              });
        });

      let completeButton = document.createElement("button");
      completeButton.type = "button";
      completeButton.classList.add("btn");
      completeButton.classList.add("btn-success");
      completeButton.classList.add("mr-2");
      completeButton.textContent = "Complete";

      completeButton.addEventListener("click", function() {
        const bookingId = data.receivedBookings[i].bookingId; // Get the booking ID

        fetch(`/complete?id=${bookingId}`, {
          method: "POST"
        })
            .then(response => {
              if (response.ok) {
                popupDialog("Success", "Successfully complete the booking!");
                setTimeout(function() {console.log("Waiting for 5 seconds"); window.location.reload();}, 2000);
              } else {
                // Access the response text by chaining another .then() block
                response.text().then(errorMessage => {
                  // Alert the error message
                  popupDialog("Error", errorMessage);
                  setTimeout(function() {console.log("Waiting for 5 seconds"); window.location.reload();}, 2000);
                });
              }
            });
      });

      if(data.receivedBookings[i].isAccepted === 0) {
        col9.appendChild(confirmButton);
      }

      else if(data.receivedBookings[i].isAccepted === 1) {
        col9.appendChild(completeButton);
      }

      else{
          let hide1 = document.createElement("span");
          hide1.style.opacity = "0";
          col9.appendChild(hide1);
      }

      let col10 = document.createElement("td");
      let cancelButton = document.createElement("button");
      cancelButton.type = "button";
      cancelButton.classList.add("btn");
      cancelButton.classList.add("btn-danger");
      cancelButton.textContent = "Cancel";

      cancelButton.addEventListener("click", function() {
        const bookingId = data.receivedBookings[i].bookingId; // Get the booking ID

        fetch(`/refuse?id=${bookingId}`, {
          method: "POST"
        })
            .then(response => {
              if (response.ok) {
                popupDialog("Success", "Successfully refuse customer booking!");
                setTimeout(function() {
                  console.log("Waiting for 5 seconds");
                  window.location.reload();
                }, 2000);
              } else {
                // Access the response text by chaining another .then() block
                response.text().then(errorMessage => {
                  // Alert the error message
                  popupDialog("Error", errorMessage);
                  setTimeout(function() {
                    console.log("Waiting for 5 seconds");
                    window.location.reload();
                  }, 2000);
                });
              }
            });
      });

      if(data.receivedBookings[i].isAccepted === 0) {
        col10.appendChild(cancelButton);
      }
      else {
        let hide2 = document.createElement("span");
        hide2.style.opacity = "0";
        col10.appendChild(hide2);
      }

      row.appendChild(col1);
      row.appendChild(col2);
      row.appendChild(col3);
      row.appendChild(col4);
      row.appendChild(col5);
      row.appendChild(col6);
      row.appendChild(col7);
      row.appendChild(col8);
      row.appendChild(col9);
      row.appendChild(col10);

      let horizon = document.createElement("hr");
      horizon.style.width = "650%";

      table1.appendChild(row);
      table1.appendChild(horizon);
      fullDataCard1.appendChild(table1);
      receivedRequestList.appendChild(fullDataCard1);
    }
  }
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

function redirectChatPage() {
  window.location.href = '/chat';
}

// // Define a function to check for successBooking and notify the user
// function checkSuccessBooking() {
//   alert("abc");
//   // Check if there is a successBooking attribute in the session
//   if (sessionStorage.getItem('notifyBookingSuccessfully') === 'true') {
//     // Notify the user after 10 seconds
//     setTimeout(function() {
//       alert('Your booking was successful!');
//       // Replace the attribute with 'false' after 10 seconds
//       sessionStorage.setItem('notifyBookingSuccessfully', 'false');
//     }, 5000); // 10 seconds delay
//   }
// }
//
// // Call the function when the DOM content is loaded
// window.onload = async function (){
//   await printData();
//   checkSuccessBooking();
// };