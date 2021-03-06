window.onload = getForms;

async function getForms() {
    console.log("Getting AE forms");
    table = document.getElementById("form_table");
    table.innerHTML = "<tr><th>Edit</th><th>First</th><th>Last</th><th>Title</th><th>Expected Completion</th><th>Type</th><th>Genre</th><th>Tag</th><th>Description</th><th>Status</th><th>Submission Date</th><th>Priority</th><th>Assistant Editor Approval</th><th>General Editor Approval</th><th>Senior Editor Approval</th><th>Approve</th><th>Deny</th><th>Request more information (Author)</th><th>Request more information (Assistant Editor)</th><th>Request more information (General Editor)</th></tr>";
    let url = "http://localhost:8080/SPMS/spform/editor/view";
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
       var cell0 = row.insertCell();
       createEditButton(cell0, async function edit() {
            console.log("EDIT FORM BUTTON");
            let se_title = prompt("Input a new title, or leave blank to keep it the same.");
            let se_tag_line = prompt("Input a new tag line, or leave blank to keep it the same.");
            let se_end_date = prompt("Input a new completion date, or leave blank to keep it the same.", "dd-mm-yyyy");
            var mydate = "";
            if (se_end_date != "" && se_end_date != "dd-mm-yyyy") {
                var parts =se_end_date.split('-');
                console.log("parts: "+parts);
                if (parts.length == 3 && parts[0] < 32 && parts[1] < 13) { //check proper date formatting
                    mydate = new Date(parts[2], parts[1] - 1, parts[0]); 
                    //mydate = parts[2] + "-" + parts[1] + "-" + parts[0];
                } 
                
            }
            console.log("MY DATE: "+mydate);
            sendEdit(form.id, se_title, se_tag_line, mydate);
       })
        

        var cell1 = row.insertCell();
        cell1.innerHTML = form.author_first;
        var cell2 = row.insertCell();
        cell2.innerHTML = form.author_last;
        var cell3 = row.insertCell();
        cell3.innerHTML = form.title;
        var cell4 = row.insertCell();
        cell4.innerHTML = form.end_date;
        var cell5 = row.insertCell();
        cell5.innerHTML = form.story_type.type;
        var cell6 = row.insertCell();
        cell6.innerHTML = form.genre.name;
        var cell7 = row.insertCell();
        cell7.innerHTML = form.tag_line;
        var cell8 = row.insertCell();
        cell8.innerHTML = form.description;
        var cell9 = row.insertCell();
        cell9.innerHTML = form.status;
        var cell10 = row.insertCell();
        cell10.innerHTML = form.submit_date;
        var cell11 = row.insertCell();
        cell11.innerHTML = form.priority;
        var cell12 = row.insertCell();
        cell12.innerHTML = form.ae_approval;
        var cell13 = row.insertCell();
        cell13.innerHTML = form.ge_approval;
        var cell14 = row.insertCell();
        cell14.innerHTML = form.se_approval;
        // add buttons
        var cell15 = row.insertCell();
        createApproveButton(cell15, async function approve() {
            console.log("APPROVE FUNCTION");
            let id = form.id
            console.log("Form id: "+id);
            sendApproval(id);
            
        })
        var cell16 = row.insertCell();
        createDenyButton(cell16, async function deny() {
            console.log("DENY FUNCTION");
            let id = form.id
            console.log("Form id: "+id);
            let reason = prompt('Denial reason');
            sendDenial(id, reason);
            
        })
        var cell17 = row.insertCell();
        createRequestButton(cell17, async function requestInfo() {
            console.log("REQUEST INFO FUNCTION");
            let id = form.id
            console.log("Form id: "+id);
            let info = prompt('Tell author what information you want:');
            sendRequestInfo(id, info, 'author');
            
        })
        var cell18 = row.insertCell();
        if (form.ae_id != null) {
            createRequestButton(cell18, async function requestInfo() {
                console.log("REQUEST INFO FUNCTION");
                let id = form.id
                console.log("Form id: "+id);
                let info = prompt('Tell assistant editor what information you want:');
                sendRequestInfo(id, info, 'ae');
                
            })
        } else {
            cell18.innerHTML = "No assistant editor for this form."
        }
        
        var cell19 = row.insertCell();
        createRequestButton(cell19, async function requestInfo() {
            console.log("REQUEST INFO FUNCTION");
            let id = form.id
            console.log("Form id: "+id);
            let info = prompt('Tell general editor what information you want:');
            sendRequestInfo(id, info, 'ge');
            
        })
    }
    sortTable();
}

function createApproveButton(context, func) {
    var approve_btn = document.createElement("button");
            
    approve_btn.id = "form_approve";
    approve_btn.className = "btn";
    approve_btn.type = "submit";
    approve_btn.innerHTML = "Approve"
    approve_btn.onclick = func;
    //resubmit_btn.onclick = resubmit(form.id, form.story_type.type);
    context.appendChild(approve_btn);
}
function createDenyButton(context, func) {
    var deny_btn = document.createElement("button");
            
    deny_btn.id = "form_deny";
    deny_btn.className = "btn";
    deny_btn.type = "submit";
    deny_btn.innerHTML = "Deny"
    deny_btn.onclick = func;
    //resubmit_btn.onclick = resubmit(form.id, form.story_type.type);
    context.appendChild(deny_btn);
}
function createRequestButton(context, func) {
    var request_btn = document.createElement("button");
            
    request_btn.id = "form_request";
    request_btn.className = "btn";
    request_btn.type = "submit";
    request_btn.innerHTML = "Request Info"
    request_btn.onclick = func;
    //resubmit_btn.onclick = resubmit(form.id, form.story_type.type);
    context.appendChild(request_btn);
}
function createEditButton(context, func) {
    var edit_btn = document.createElement("button");

    edit_btn.id = "edit_btn";
    edit_btn.className = "btn";
    edit_btn.type = "submit";
    edit_btn.innerHTML = "Edit Form"
    edit_btn.onclick = func;

    context.appendChild(edit_btn);
}
async function sendApproval(form_id) {
    console.log("SEND APPROVAL");
    let url = "http://localhost:8080/SPMS/spform/approve";
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
async function sendDenial(form_id, reason) {
    console.log("SEND DENIAL");
    let url = "http://localhost:8080/SPMS/spform/deny";
    let denial = {
        id : form_id,
        denial_reason : reason
    }
    let response = await fetch(url,{
        method: 'POST',
        headers : { 
          'Content-Type': 'application/json',
          'Accept': 'application/json'
         },
        body: JSON.stringify(denial)})
    let result = await response.json();
    console.log(result)
    getForms();
}
async function sendRequestInfo(id, msg, target) {
    console.log("SEND REQUEST INFO");
    var req = {
        form_id: id,
        receiver: target,
        request: msg
    }
    let url = "http://localhost:8080/SPMS/spform/request";
    let response = await fetch(url,{
        method: 'POST',
        headers : { 
          'Content-Type': 'application/json',
          'Accept': 'application/json'
         },
        body: JSON.stringify(req)});
    let result = await response.json();
    console.log(result);
    getForms();
}
async function sendEdit(form_id, title, tag, date) {
    console.log("INSIDE sendEdit");
    var req = {
        id: form_id,
        se_title: title,
        se_tag_line: tag,
        se_end_date: date
    }
    let url = "http://localhost:8080/SPMS/spform/edit";
    let response = await fetch(url,{
        method: 'POST',
        headers : { 
          'Content-Type': 'application/json',
          'Accept': 'application/json'
         },
        body: JSON.stringify(req)});
    let result = await response.json();
    console.log(result);
}
function sortTable() {
    var table, i, x, y;
    table = document.getElementById("form_table");
    var switching = true;

    // Run loop until no switching is needed
    while (switching) {
        switching = false;
        var rows = table.rows;

        // Loop to go through all rows
        for (i = 1; i < (rows.length - 1); i++) {
            var Switch = false;

            // Fetch 2 elements that need to be compared
            x = rows[i].getElementsByTagName("TD")[10];
            y = rows[i + 1].getElementsByTagName("TD")[10];
            console.log(x);
            console.log(y);

            // Check if 2 rows need to be switched
            if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase())
                {

                // If yes, mark Switch as needed and break loop
                Switch = true;
                break;
            }
        }
        if (Switch) {
            // Function to switch rows and mark switch as completed
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            switching = true;
        }
    }
}