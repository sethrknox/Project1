
boxes_exist = false;
document.getElementById("abtn").onclick = updateAuthorLogin;

function updateAuthorLogin () {
    boxes_exist = true;
    section = document.getElementById("login_boxes");

    let auth_un = document.createElement('span');
    let auth_un_tb = document.createElement('input');
    let auth_pw = document.createElement('span');
    let auth_pw_tb = document.createElement('input');
    let login_btn = document.createElement('button')
    

    auth_un.innerHTML = "Username: "
    auth_pw.innerHTML = " Password: "
    auth_un_tb.type = "text"
    auth_un_tb.id = "a_un"
    auth_pw_tb.type = "text"
    auth_pw_tb.id = "a_pw"
    login_btn.id = "login_btn"
    login_btn.innerHTML = "login"
    login_btn.onclick = authorLogin;

    section.appendChild(auth_un)
    section.appendChild(auth_un_tb)
    section.appendChild(auth_pw)
    section.appendChild(auth_pw_tb)
    section.appendChild(login_btn)
}

function authorLogin () {
    let un = document.getElementById("a_un").value
    let pw = document.getElementById("a_pw").value
    let url = "http://localhost:8080/SPMS/author/login"
    let params = "?username=" + un + "&password=" + pw
    // let params = new FormData();
    // params.append("username", un);
    // params.append("password", pw);
    // alert(un + pw)
    let xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = receiveData;

    xhttp.open("GET", url+params, true)

    xhttp.send()

    function receiveData () {
        if (xhttp.readyState == 4) {
            if (xhttp.status == 200) {
                //window.location.href = "http://localhost:8080/SPMS/author.html"
                
                let res = xhttp.responseText;
                if (res != "null") { // logged in
                    console.log(res);
                    // document.open()
                    // document.write(res)
                    // document.close()
                } else {
                    alert("username or password incorrect");
                }
                
            }
        }
    }
}

