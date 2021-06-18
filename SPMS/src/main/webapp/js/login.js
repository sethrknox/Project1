
document.getElementById("abtn").onclick = updateAuthorLogin;
document.getElementById("ebtn").onclick = updateEditorLogin;

function updateAuthorLogin () {
    login("author")
}
function updateEditorLogin () {
    login("editor")
}
function login (user_type) {
    section = document.getElementById("login_boxes");

    let un = document.createElement('span');
    let un_tb = document.createElement('input');
    let pw = document.createElement('span');
    let pw_tb = document.createElement('input');
    let login_btn = document.createElement('button')
    

    un.innerHTML = "Username: "
    pw.innerHTML = " Password: "
    un_tb.type = "text"
    un_tb.id = "un"
    pw_tb.type = "text"
    pw_tb.id = "pw"
    login_btn.id = "login_btn"
    login_btn.innerHTML = "login"
    if (user_type == "author") {
        login_btn.onclick = authorLogin;
    }
    if (user_type == "editor") {
        login_btn.onclick = editorFetchLogin;
    }
    

    section.appendChild(un)
    section.appendChild(un_tb)
    section.appendChild(pw)
    section.appendChild(pw_tb)
    section.appendChild(login_btn)
}

function authorLogin () {
    let un = document.getElementById("un").value
    let pw = document.getElementById("pw").value
    let url = "http://localhost:8080/SPMS/author/login"
    let params = "?username=" + un + "&password=" + pw

    let xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = receiveData;

    xhttp.open("GET", url+params, true)

    xhttp.send()

    function receiveData () {
        if (xhttp.readyState == 4) {
            if (xhttp.status == 200) {
                
                console.log("recieved response");
                let res = xhttp.responseText;
                if (res != "null") { // logged in
                    // Handle any login information
                    console.log(res);

                    // Redirect to new html page
                    window.location.href = "http://localhost:8080/SPMS/author.html"
                } else {
                    alert("username or password incorrect");
                }
                
            } else {
                console.log("status not 200");
            }
        } else {
            console.log("readyState not 4");
        }
    }
};

function editorLogin() {
    let un = document.getElementById("un").value
    let pw = document.getElementById("pw").value
    let url = "http://localhost:8080/SPMS/editor/login"
    let params = "?username=" + un + "&password=" + pw

    let xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = receiveData;

    xhttp.open("GET", url+params, true)

    xhttp.send()

    function receiveData () {
        if (xhttp.readyState == 4) {
            if (xhttp.status == 200) {
                
                
                let res = xhttp.responseText;
                if (res != "null") { // logged in
                    // Handle any login information
                    console.log(res);

                    // Redirect to new html page
                    window.location.href = "http://localhost:8080/SPMS/editor.html"
                    

                } else {
                    alert("username or password incorrect");
                }
                
            } else {
                console.log("status not 200");
            }
        } else {
            console.log("readyState not 4");
        }
    }
};

async function editorFetchLogin() {
    let un = document.getElementById("un").value
    let pw = document.getElementById("pw").value
    let url = "http://localhost:8080/SPMS/editor/login"
    let params = "?username=" + un + "&password=" + pw
    let user = {
        username: un,
        password: pw
    };
    let response = await fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(user)
    });
    try {
        let result = await response.json();
        console.log(result)
        window.location.href = "http://localhost:8080/SPMS/editor.html"
    } catch (error) {
        alert("Invalid username and password")
        //console.error(error)
    }
    
};