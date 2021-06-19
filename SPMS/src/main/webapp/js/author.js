
document.getElementById("hbtn").onclick = home;
document.getElementById("view_btn").onclick = viewForms;
document.getElementById("create_btn").onclick = createForm;
document.getElementById("logout_btn").onclick = logout;


function home() {
    window.location.href = "http://localhost:8080/SPMS/author.html"
}
function viewForms() {
    window.location.href = "http://localhost:8080/SPMS/authorview.html"
}

function createForm() {
    //alert("OVER HERE")
    window.location.href = "http://localhost:8080/SPMS/authorcreate.html"
}

function logout() {
    
}