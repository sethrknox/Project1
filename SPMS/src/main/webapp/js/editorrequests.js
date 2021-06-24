window.onload = getInRequests;

async function getInRequests() {
    console.log("Getting requests");
    table = document.getElementById("in_req");
    table.innerHTML = "<tr><th>First</th><th>Last</th><th>Title</th><th>Expected Completion</th><th>Type</th><th>Genre</th><th>Tag</th><th>Description</th><th>Status</th><th>Submission Date</th><th>Information Request</th><th>Reply</th></tr>";
    let url = "http://localhost:8080/SPMS/spform/editor/view/incoming";
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
        cell1.innerHTML = form.form_id.author_first;
        var cell2 = row.insertCell(1);
        cell2.innerHTML = form.form_id.author_last;
        var cell3 = row.insertCell(2);
        cell3.innerHTML = form.form_id.title;
        var cell4 = row.insertCell(3);
        cell4.innerHTML = form.form_id.end_date;
        var cell5 = row.insertCell(4);
        cell5.innerHTML = form.form_id.story_type.type;
        var cell6 = row.insertCell(5);
        cell6.innerHTML = form.form_id.genre.name;
        var cell7 = row.insertCell(6);
        cell7.innerHTML = form.form_id.tag_line;
        var cell8 = row.insertCell(7);
        cell8.innerHTML = form.form_id.description;
        var cell9 = row.insertCell(8);
        cell9.innerHTML = form.form_id.status;
        var cell10 = row.insertCell(9);
        cell10.innerHTML = form.form_id.submit_date;

        var cell11 = row.insertCell(10);
        cell11.innerHTML = form.request;

        var cell12 = row.insertCell(11);
        createButton(cell12, async function reply() {
            console.log("IN REPLY FUNCTION");
            let id = form.id
            console.log(id);
            let reply = prompt('Reply to the editor:');
            sendReply(id, reply);

        })
            
        //id_count++;

    };

    getOutRequests();
};

async function getOutRequests() {
    console.log("Getting requests");
    table = document.getElementById("out_req");
    table.innerHTML = "<tr><th>First</th><th>Last</th><th>Title</th><th>Expected Completion</th><th>Type</th><th>Genre</th><th>Tag</th><th>Description</th><th>Status</th><th>Submission Date</th><th>Information Request</th><th>Request Target</th><th>Reply</th></tr>";
    let url = "http://localhost:8080/SPMS/spform/editor/view/outgoing";
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
        cell1.innerHTML = form.form_id.author_first;
        var cell2 = row.insertCell(1);
        cell2.innerHTML = form.form_id.author_last;
        var cell3 = row.insertCell(2);
        cell3.innerHTML = form.form_id.title;
        var cell4 = row.insertCell(3);
        cell4.innerHTML = form.form_id.end_date;
        var cell5 = row.insertCell(4);
        cell5.innerHTML = form.form_id.story_type.type;
        var cell6 = row.insertCell(5);
        cell6.innerHTML = form.form_id.genre.name;
        var cell7 = row.insertCell(6);
        cell7.innerHTML = form.form_id.tag_line;
        var cell8 = row.insertCell(7);
        cell8.innerHTML = form.form_id.description;
        var cell9 = row.insertCell(8);
        cell9.innerHTML = form.form_id.status;
        var cell10 = row.insertCell(9);
        cell10.innerHTML = form.form_id.submit_date;

        var cell11 = row.insertCell(10);
        cell11.innerHTML = form.request;
        var cell12 = row.insertCell(11);
        var req_target = form.receiver;
        if (req_target == 'ae') {
            req_target = 'assistant editor';
        } else if (req_target == 'ge') {
            req_target = 'general editor'
        } else if (req_target == 'se') {
            req_target = 'senior editor'
        }
        cell12.innerHTML = req_target;
        var cell13 = row.insertCell(12);
        if (form.response != undefined) {
            cell13.innerHTML = form.response;
        }
        
            
        //id_count++;

    };
}

function createButton(context, func) {
    var resubmit_btn = document.createElement("button");
            
    resubmit_btn.id = "form_reply";
    resubmit_btn.className = "btn";
    resubmit_btn.type = "submit";
    resubmit_btn.innerHTML = "Reply"
    resubmit_btn.onclick = func;
    //resubmit_btn.onclick = resubmit(form.id, form.story_type.type);
    context.appendChild(resubmit_btn);
}

async function sendReply(req_id, reply) {
    console.log("SEND REPLY");
    let url = "http://localhost:8080/SPMS/spform/reply";
    let msg = {
        id: req_id,
        response: reply
    }
    let response = await fetch(url,{
        method: 'POST',
        headers : { 
          'Content-Type': 'application/json',
          'Accept': 'application/json'
         },
        body: JSON.stringify(msg)})
    let result = await response.json();
    console.log(result);
    getRequests();
}