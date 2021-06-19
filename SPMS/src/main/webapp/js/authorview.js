window.onload = getForms;

async function getForms() {
    console.log("Getting forms");
    table = document.getElementById("form_table");

    let url = "http://localhost:8080/SPMS/spform/view";
    let response = await fetch(url,{
        headers : { 
          'Content-Type': 'application/json',
          'Accept': 'application/json'
         }})
    let result = await response.json();
    console.log(result);
    // we have the json
    for (let form of result) {
        var row = table.insertRow(-1);

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

        // let tr = document.createElement('tr')

        // // Form first name
        // let tdFirst = document.createElement('td')
        // tdFirst.innerHTML = form.author_first;
        // tr.appendChild(tdFirst)

        // // Form last name
        // let tdLast = document.createElement('td')
        // tdLast.innerHTML = form.author_last;
        // tr.appendChild(tdLast)

        // // Form title
        // let tdTitle = document.createElement('td')
        // tdTitle.innerHTML = form.title;
        // tr.appendChild(tdTitle)

        // // Form expected completion
        // let tdEndDate = document.createElement('td')
        // tdEndDate.innerHTML = form.end_date;
        // tr.appendChild(tdEndDate)

        // // Form type
        // let tdType = document.createElement('td')
        // tdType.innerHTML = form.story_type.type;
        // tr.appendChild(tdType)

        // // Form genre
        // let tdGenre = document.createElement('td')
        // tdGenre.innerHTML = form.genre.name;
        // tr.appendChild(tdGenre)

        // // Form tag
        // let tdTag = document.createElement('td')
        // tdTag.innerHTML = form.tag_line;
        // tr.appendChild(tdTag)

        // // Form description
        // let tdDescription = document.createElement('td')
        // tdDescription.innerHTML = form.description;
        // tr.appendChild(tdDescription)

        // // Form status
        // let tdStatus = document.createElement('td')
        // tdStatus.innerHTML = form.status;
        // tr.appendChild(tdStatus)

        // // Form submission date
        // let tdSubmitDate = document.createElement('td')
        // tdSubmitDate.innerHTML = form.submit_date;
        // tr.appendChild(tdSubmitDate)

        // table.appendChild(tr)
    };

};