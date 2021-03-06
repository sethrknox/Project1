
document.getElementById("hbtn").onclick = home;
document.getElementById("view_btn").onclick = viewForms;
document.getElementById("draft_btn").onclick = viewDrafts;
document.getElementById("req_btn").onclick = viewRequests;
document.getElementById("logout_btn").onclick = logout;


function home() {
    window.location.href = "http://localhost:8080/SPMS/editor.html"
}
async function viewForms() {
    let url = "http://localhost:8080/SPMS/editor/type";
    let response = await fetch(url,{
        headers : { 
          'Content-Type': 'application/json',
          'Accept': 'application/json'
         }})
    let result = await response.json();
    console.log("Editor type: "+result);
    switch(result) {
        case "assistant": 
        window.location.href = "http://localhost:8080/SPMS/editorview_a.html"
            break;
        case "general":
            window.location.href = "http://localhost:8080/SPMS/editorview_g.html"
            break;
        case "senior":
            window.location.href = "http://localhost:8080/SPMS/editorview_s.html"
            break;
    }
    
}

async function viewDrafts() {
    let url = "http://localhost:8080/SPMS/editor/type";
    let response = await fetch(url,{
        headers : { 
          'Content-Type': 'application/json',
          'Accept': 'application/json'
         }})
    let result = await response.json();
    console.log("Editor type: "+result);
    switch(result) {
        case "assistant": 
        window.location.href = "http://localhost:8080/SPMS/editordraft_a.html"
            break;
        case "general":
            window.location.href = "http://localhost:8080/SPMS/editordraft_g.html"
            break;
        case "senior":
            window.location.href = "http://localhost:8080/SPMS/editordraft_s.html"
            break;
    }
}

async function viewRequests() {
    window.location.href = "http://localhost:8080/SPMS/editorrequests.html"
    // let url = "http://localhost:8080/SPMS/editor/type";
    // let response = await fetch(url,{
    //     headers : { 
    //       'Content-Type': 'application/json',
    //       'Accept': 'application/json'
    //      }})
    // let result = await response.json();
    // console.log("Editor type: "+result);
    // switch(result) {
    //     case "assistant": 
    //     window.location.href = "http://localhost:8080/SPMS/editorrequests_a.html"
    //         break;
    //     case "general":
    //         window.location.href = "http://localhost:8080/SPMS/editorrequests_g.html"
    //         break;
    //     case "senior":
    //         window.location.href = "http://localhost:8080/SPMS/editorrequests_s.html"
    //         break;
    // }
}

async function logout() {
    let url = "http://localhost:8080/SPMS/editor/logout";
    let response = await fetch(url,{
        headers : { 
          'Content-Type': 'application/json',
          'Accept': 'application/json'
         }})
    let result = await response.json();
    console.log(result);
    window.location.href = "http://localhost:8080/SPMS/"
}

async function getCommittees() {
    let url = "http://localhost:8080/SPMS/editor/committees";
    let response = await fetch(url,{
        headers : { 
          'Content-Type': 'application/json',
          'Accept': 'application/json'
         }})
    let result = await response.json();
    console.log(result);
    let genres = document.getElementById("genres");
    for (let genre of result) {
        let item = document.createElement("li");
        item.innerHTML = genre.name;
        genres.appendChild(item);
    }
}