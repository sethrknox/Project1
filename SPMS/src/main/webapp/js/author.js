
document.getElementById("hbtn").onclick = home;
document.getElementById("view_btn").onclick = viewForms;
document.getElementById("create_btn").onclick = createForm;
document.getElementById("req_btn").onclick = viewRequests;
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

function viewRequests() {
    window.location.href = "http://localhost:8080/SPMS/authorrequests.html"
}

async function logout() {
    let url = "http://localhost:8080/SPMS/author/logout";
    let response = await fetch(url,{
        headers : { 
          'Content-Type': 'application/json',
          'Accept': 'application/json'
         }})
    let result = await response.json();
    console.log(result);
    window.location.href = "http://localhost:8080/SPMS/"
}