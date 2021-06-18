
document.getElementById("hbtn").onclick = home;
document.getElementById("view_btn").onclick = viewForms;
document.getElementById("logout_btn").onclick = logout;


function home() {
    window.location.href = "http://localhost:8080/SPMS/editor.html"
}
function viewForms() {
    window.location.href = "http://localhost:8080/SPMS/editorview.html"
}

function logout() {
    
}