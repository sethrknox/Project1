window.onload = populateGenres;
//document.getElementById("form").addEventListener('submit', submitForm)


async function populateGenres() {
    console.log("getting genres");
    genres = document.getElementById("genre")

    let url = "http://localhost:8080/SPMS/spform/genres"
    let response = await fetch(url,{
        headers : { 
          'Content-Type': 'application/json',
          'Accept': 'application/json'
         }})
    let result = await response.json();
    //console.log(result)
    for (let genre of result) {
        var option = document.createElement('option')
        option.text = genre.name
        option.value = genre.name
        genres.add(option)
    }
    getPoints();
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
        points.innerHTML = "You have " + data + " author points"
        points.style.display = "inline";
        document.getElementById("points").value = data;
    }).catch(err => {
        console.log("Error reading data " + err);
    })
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
    }
}

async function submitForm() {
    console.log("submitting form");
    var f = document.getElementById("form")
    console.log(f.elements);
    //console.log(f.firstName.value);
    //console.log(f.elements["firstName"].value);
    console.log(f.elements[0].value);
    //var form = JSON.stringify(f.elements)
    var form = JSON.stringify(f.elements)
    console.log(form);
    console.log(form.elements);

    let spform = {}

    let formData = new FormData(f)
    console.log(formData.entries());
    for (var [key, value] of formData.entries()) { 
        //console.log(key, value);
        //spform.push({key:value})
        spform[key] = value
      }
    console.log(spform);
    //var formData = JSON.stringify(f.serializeArray())
    console.log(JSON.stringify(spform));
    //alert(spform);

    let url = "http://localhost:8080/SPMS/spform/submit"
    let response = await fetch(url,{
        method: 'POST',
        headers : { 
          'Content-Type': 'application/json',
          'Accept': 'application/json'
         },
        body: JSON.stringify(spform)})
    let result = await response.json();
    console.log(result)
    alert();
}