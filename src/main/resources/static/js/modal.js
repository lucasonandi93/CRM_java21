// Get the modal
const modal = document.getElementById("modal-infos");


/**
 * Open the modal with id "modal-infos"
 */
function openModal(){
    modal.style.display = "block";
}

/**
 * Close the modal with id "modal-infos"
 */
function closeModal(){
    modal.style.display = "none";
}

// Close the modal when the dark background is clicked
window.onclick = function(event) {
  if (event.target == modal) {
      modal.style.display = "none";
  }
} 
