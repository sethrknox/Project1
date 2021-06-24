var editor_id;
window.onload = getId;
async function getId() {
    let url = "http://localhost:8080/SPMS/editor/id";
    let response = await fetch(url,{
        headers : {
            'Content-Type': 'application/json',
            'Accept' : 'application/json'
        }})
    let result = await response.json();
    console.log(result);
    editor_id = result;
    getForms();
}

async function getForms() {
    console.log("Getting AE drafts");
    table = document.getElementById("form_table");
    table.innerHTML = "<tr><th>First</th><th>Last</th><th>Title</th><th>Type</th><th>Genre</th><th>Tag</th><th>Description</th><th>Status</th><th>Assistant Editor Approval</th><th>General Editor Approval</th><th>Senior Editor Approval</th><th>Approve</th><th>Request Changes</th></tr>";
    let url = "http://localhost:8080/SPMS/spform/editor/view/drafts";
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
        cell4.innerHTML = form.story_type.type;
        var cell5 = row.insertCell(4);
        cell5.innerHTML = form.genre.name;
        var cell6 = row.insertCell(5);
        cell6.innerHTML = form.tag_line;
        var cell7 = row.insertCell(6);
        cell7.innerHTML = form.description;

        var cell8 = row.insertCell(7);
        //console.log("DRAFT CONTENT");
        //console.log(typeof form.draft);
        //console.log(form.draft);
        var byteArray = new Uint8Array(form.draft);
        var saveByteArray = (function () {
            var a = document.createElement("a");
            cell8.appendChild(a);
            return function (data, name) {
                var blob = new Blob(data, {type: "octet/stream"}),
                    url = window.URL.createObjectURL(blob);
                a.innerHTML = "draft";
                a.href = url;
                a.download = name;
                //a.click();
                //window.URL.revokeObjectURL(url);
            };
        }());
        saveByteArray([byteArray], 'draft.txt');
        //let fd = new FormData();
        //fd.append("file", byteArray);
        //var download = document.createElement('a');
        //download.setAttribute('href', 'data:text/plain;charset=utf-8,')
        
        //cell8.innerHTML = form.status;

        var cell9 = row.insertCell(8);
        cell9.innerHTML = form.ae_draft;
        var cell10 = row.insertCell(9);
        cell10.innerHTML = form.ge_draft;
        var cell11 = row.insertCell(10);
        cell11.innerHTML = form.se_draft;

        if (editor_id == form.ae_id.id && form.ae_draft == 'pending' && form.ge_draft != 'denied' && form.se_draft != 'denied') {
            var cell12 = row.insertCell(11);
            createApproveButton(cell12, async function approve() {
            console.log("APPROVE FUNCTION");
            let id = form.id
            console.log("Form id: "+id);
            sendApproval(id);
            
        })
        //     var cell13 = row.insertCell(12);
        //     createDenyButton(cell13, async function deny() {
        //     console.log("DENY FUNCTION");
        //     let id = form.id
        //     console.log("Form id: "+id);
        //     let reason = prompt('Denial reason');
        //     sendDenial(id, reason);
            
        // })
            var cell13 = row.insertCell(12);
            createRequestButton(cell13, async function requestInfo() {
            console.log("REQUEST CHANGES FUNCTION");
            let id = form.id
            console.log("Form id: "+id);
            let info = prompt('Tell author what changes you want:');
            sendRequestInfo(id, info, 'author');
            
        })
        }
        
    }
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
    request_btn.innerHTML = "Request Changes"
    request_btn.onclick = func;
    //resubmit_btn.onclick = resubmit(form.id, form.story_type.type);
    context.appendChild(request_btn);
}
async function sendApproval(form_id) {
    console.log("SEND APPROVAL");
    let url = "http://localhost:8080/SPMS/spform/draft/approve";
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
    let url = "http://localhost:8080/SPMS/spform/draft/deny";
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
    let req = {
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