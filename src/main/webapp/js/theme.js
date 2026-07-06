function toggleTheme() {

    console.log("Button Clicked");

    document.body.classList.toggle("dark-mode");

    console.log(document.body.className);

    if (document.body.classList.contains("dark-mode")) {
        localStorage.setItem("theme", "dark");
    } else {
        localStorage.setItem("theme", "light");
    }

    changeIcon();
}

function loadTheme() {

    if (localStorage.getItem("theme") === "dark") {
        document.body.classList.add("dark-mode");
    }

    changeIcon();
}

function changeIcon() {

    let icon = document.getElementById("themeIcon");

    if (!icon) return;

    if (document.body.classList.contains("dark-mode")) {
        icon.className = "fa-solid fa-sun";
    } else {
        icon.className = "fa-solid fa-moon";
    }
}

window.addEventListener("load", loadTheme);