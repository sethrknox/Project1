document.getElementById("hbtn").onclick = home;
document.getElementById("create_btn").onclick = createForm;

function home() {
    window.location.href = "http://localhost:8080/SPMS/author.html"
}

function createForm() {
    alert("HI")
    //window.location.href = "http://localhost:8080/SPMS/authorcreate.html"
}

window.onload = getForms;
//window.onload = get;
async function getForms() {
    console.log("Getting forms")
    table = document.getElementById("form_table")

    let url = "http://localhost:8080/SPMS/spform/view"
    let response = await fetch(url,{
        headers : { 
          'Content-Type': 'application/json',
          'Accept': 'application/json'
         }})
    let result = await response.json();
    console.log(result)
    // we have the json
    for (let form of result) {
        let tr = document.createElement('tr')

        // Form first name
        let tdFirst = document.createElement('td')
        tdFirst.innerHTML = form.author_first;
        tr.appendChild(tdFirst)

        // Form last name
        let tdLast = document.createElement('td')
        tdLast.innerHTML = form.author_last;
        tr.appendChild(tdLast)

        // Form title
        let tdTitle = document.createElement('td')
        tdTitle.innerHTML = form.title;
        tr.appendChild(tdTitle)

        // Form expected completion
        let tdEndDate = document.createElement('td')
        tdEndDate.innerHTML = form.end_date;
        tr.appendChild(tdEndDate)

        // Form type
        let tdType = document.createElement('td')
        tdType.innerHTML = form.story_type.type;
        tr.appendChild(tdType)

        // Form genre
        let tdGenre = document.createElement('td')
        tdGenre.innerHTML = form.genre.name;
        tr.appendChild(tdGenre)

        // Form tag
        let tdTag = document.createElement('td')
        tdTag.innerHTML = form.tag_line;
        tr.appendChild(tdTag)

        // Form description
        let tdDescription = document.createElement('td')
        tdDescription.innerHTML = form.description;
        tr.appendChild(tdDescription)

        // Form status
        let tdStatus = document.createElement('td')
        tdStatus.innerHTML = form.status;
        tr.appendChild(tdStatus)

        // Form submission date
        let tdSubmitDate = document.createElement('td')
        tdSubmitDate.innerHTML = form.submit_date;
        tr.appendChild(tdSubmitDate)

        table.appendChild(tr)
    }
}

function get() {
    console.log("Gettings forms")

    let url = "http://localhost:8080/SPMS/spform/view"
    fetch(url).then(response => {
        console.log(response);
        return response.json();
    }).then(data => {
        console.log(data);
    }).catch(err => {
        console.log("Error reading data " + err);
    })
}