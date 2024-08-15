// js/navbar.js
document.addEventListener("DOMContentLoaded", function() {
    var toggler = document.querySelector(".navbar-toggler");
    var links = document.querySelector(".navbar-links");

    if (toggler && links) {
        toggler.addEventListener("click", function() {
            links.classList.toggle("show");
        });
    }
});
