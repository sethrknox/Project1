window.onload = getForms;
document.getElementById("draft_file").onblur = checkFile;

async function getForms() {
    console.log("Getting forms");
    table = document.getElementById("form_table");
    table.innerHTML = "<tr><th>First</th><th>Last</th><th>Title</th><th>Expected Completion</th><th>Type</th><th>Genre</th><th>Tag</th><th>Description</th><th>Status</th><th>Submission Date</th><th>Misc</th></tr>";
    let url = "http://localhost:8080/SPMS/spform/view";
    let response = await fetch(url,{
        headers : { 
          'Content-Type': 'application/json',
          'Accept': 'application/json'
         }})
    let result = await response.json();
    console.log(result);
    // we have the json
    let id_count = 0;
    for (let form of result) {
        var row = table.insertRow(-1);
       // row.id = id_count;
        

        var cell1 = row.insertCell(0);
        cell1.innerHTML = form.author_first;
        var cell2 = row.insertCell(1);
        cell2.innerHTML = form.author_last;
        var cell3 = row.insertCell(2);
        cell3.innerHTML = form.title;
        var cell4 = row.insertCell(3);
        cell4.innerHTML = form.end_date;
        var cell5 = row.insertCell(4);
        cell5.innerHTML = form.story_type.type;
        var cell6 = row.insertCell(5);
        cell6.innerHTML = form.genre.name;
        var cell7 = row.insertCell(6);
        cell7.innerHTML = form.tag_line;
        var cell8 = row.insertCell(7);
        cell8.innerHTML = form.description;
        var cell9 = row.insertCell(8);
        cell9.innerHTML = form.status;
        var cell10 = row.insertCell(9);
        cell10.innerHTML = form.submit_date;

        if (form.status == "on hold") {
            var cell11 = row.insertCell(10);
            createButton(cell11, async function resubmit() {
                console.log("IN RESUMBIT FUNCTION");
                let id = form.id
                let type = form.story_type.type
                console.log(id + " " + type);
                
                points = document.getElementById("points").value;
                cost = 0;
                switch(type) {
                    case 'article':
                        cost = 10;
                        break;
                    case 'short story':
                        cost = 20;
                        break;
                    case 'novella':
                        cost = 25;
                        break;
                    case 'novel':
                        cost = 50;
                        break;
                }
                if (cost > points) {
                    alert("You do not have enough points to resubmit this form.");
                } else {
                    sendResubmitRequest(id);
                    //cell11.removeChild(cell11.children[id_count])
                    var cells = table.querySelectorAll('td:nth-child(11)')
                    cells[id_count].innerHTML = "";

                    getPoints();
                    id_count++;
                }
            })
            
            //cell11.appendChild(resubmit_btn)
            //resubmit_btn.addEventListener('onclick', resubmit(form.id, form.story_type.type));
            
            // let form_id = form.id;
            // let form_type = form.story_type.type;
            // let click = "resubmit("+form_id+",'"+form_type+"')";
            // console.log(form.story_type.type);
            // cell11.innerHTML = "<button name='form_resubmit' id='btn_row' type='submit' class='btn btn-primary' onclick='"+click+"'>Resubmit</button>"
        } else if (form.status == "denied") {
            var cell11 = row.insertCell(10);
            cell11.innerHTML = "Denial Reason: "+form.denial_reason;
        } else if (form.status != "approved" && form.ae_approval == "approved" && form.ae_approval == form.ge_approval && form.ae_approval == form.se_approval) {
            console.log("TIME TO SUBMIT DRAFT");
            var cell11 = row.insertCell(10);
            createFileButton(cell11, form.id)
        } else if (form.se_edit == true) {
            console.log("Approve/Deny edits");
            var cell11 = row.insertCell(10);
            createApproveButton(cell11, async function approve() {
                console.log("IN approve edit function");
                approveEdits(form.id);
            })
            createDenyButton(cell11, async function deny() {
                console.log("IN deny edit function");
                denyEdits(form.id);
            })
            // modify cell3, cell4, cell7 to show values editor wants
            if (form.se_title != "") {
                cell3.innerHTML = "<span>Original: " + form.title + "</span><br><span>Edited: " + form.se_title + "</span>"
            }
            if (form.se_end_date != null) {
                cell4.innerHTML = "<span>Original: " + form.end_date + "</span><br><span>Edited: " + form.se_end_date + "</span>"
            }
            if (form.se_tag_line != "") {
                cell7.innerHTML = "<span>Original: " + form.tag_line + "</span><br><span>Edited: " + form.se_tag_line + "</span>"
            }
        }
        //id_count++;

    };
    getPoints();
};

function createButton(context, func) {
    var resubmit_btn = document.createElement("button");
            
    resubmit_btn.id = "form_resubmit";
    resubmit_btn.className = "btn";
    resubmit_btn.type = "submit";
    resubmit_btn.innerHTML = "Resubmit"
    resubmit_btn.onclick = func;
    //resubmit_btn.onclick = resubmit(form.id, form.story_type.type);
    context.appendChild(resubmit_btn);
}
// async function resubmit(form_id, story_type) {
//     console.log("IN RESUMBIT FUNCTION");
//     console.log(form_id + " " + story_type);
// }
function createFileButton(context, form_id) {
    var file_btn = document.createElement("input");
    file_btn.type = "file"
    file_btn.id = "file_btn"
    context.appendChild(file_btn);

    var btn = document.createElement("button");
    btn.id = "file_submit";
    btn.className = "btn"
    btn.type = "submit";
    btn.innerHTML = "Upload Draft";
    btn.onclick = function () {
        console.log("HERE I AM ");
        console.log("FILE LENGTH:" + file_btn.files.length);
        console.log("FILE: "+file_btn.files[0]);

        let fd = new FormData();
        fd.append("file", file_btn.files[0]);
        console.log(fd);
        let url = "http://localhost:8080/SPMS/spform/draft/"+form_id
        let xhttp = new XMLHttpRequest();
        xhttp.open("POST", url);
        xhttp.send(fd);
        alert("Draft submitted.")
    }
    context.appendChild(btn);
}

function createApproveButton(context, func) {
    var approve_btn = document.createElement("button");
    approve_btn.className = "btn";
    approve_btn.type = "submit";
    approve_btn.innerHTML = "Approve changes";
    approve_btn.onclick = func;

    context.appendChild(approve_btn);
}

function createDenyButton(context, func) {
    var deny_btn = document.createElement("button");
    deny_btn.className = "btn";
    deny_btn.type = "submit";
    deny_btn.innerHTML = "Deny changes";
    deny_btn.onclick = func;

    var msg = document.createElement("span");
    msg.innerHTML = "Edits were made to your story pitch. Please approve or deny them."

    context.appendChild(deny_btn);
    context.appendChild(msg);
}

function checkFile() {
    
    let file = document.getElementById("draft_file");
    if (file.files.length == 0) {
        console.log("NO FILE SELECTED");
    } else {
        console.log("FILE SELECTED");
    }
}

function getPoints() {
    console.log("Gettings author's points")

    let url = "http://localhost:8080/SPMS/author/points"
    fetch(url).then(response => {
        console.log(response);
        return response.json();
    }).then(data => { // got points
        console.log(data);
        let points = document.getElementById("author_points");
        if (data == -1) {
            points.innerHTML = "Error getting points"
        } else {
            points.innerHTML = "You have " + data + " author points"
        }
        
        points.style.display = "inline";
        document.getElementById("points").value = data;
    }).catch(err => {
        console.log("Error reading data " + err);
    })
   // updatePoints()
}

function updatePoints() {
    let points = document.getElementById("points").value;
    let type = document.getElementById("story_type").value;
    let cost = 0;
    if (type === "novel") {
        cost = 50;
    } else if (type === "novella") {
        cost = 25;
    } else if (type === "short story") {
        cost = 20;
    } else if (type === "article") {
        cost = 10;
    }

    if (points < cost) {
        document.getElementById("points_notice").style.display = "inline";
    } else {
        document.getElementById("points_notice").style.display = "none";
    }
}

async function sendResubmitRequest(form_id) {
    let url = "http://localhost:8080/SPMS/spform/resubmit";
    let response = await fetch(url,{
        method: 'POST',
        headers : { 
          'Content-Type': 'application/json',
          'Accept': 'application/json'
         },
        body: JSON.stringify(form_id)})
    let result = await response.json();
    console.log(result)
    getForms();
}

async function approveEdits(form_id) {
    let url = "http://localhost:8080/SPMS/spform/author/approve";
  
    let response = await fetch(url,{
        method: 'POST',
        headers : { 
          'Content-Type': 'application/json',
          'Accept': 'application/json'
         },
        body: JSON.stringify(form_id)})
    let result = await response.json();
    console.log(result)
    getForms();
}

async function denyEdits(form_id) {
    let url = "http://localhost:8080/SPMS/spform/author/deny";

    let response = await fetch(url,{
        method: 'POST',
        headers : { 
          'Content-Type': 'application/json',
          'Accept': 'application/json'
         },
        body: JSON.stringify(form_id)})
    let result = await response.json();
    console.log(result)
    getForms();
}