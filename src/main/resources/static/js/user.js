let idpass=false
let pwpass=false

function pwCheck() {
    const pw1 = document.getElementById("pw1").value;
    const pw2 = document.getElementById("pw2").value;
    let msg = document.getElementById("pwmsg");
    if(pw1 === "" || pw2==="") return false;
    if (pw1 === pw2) {
        msg.style.color = "green";
        msg.textContent = "Passwords match";
        msg.classList.remove("hidden-msg");
        msg.classList.add("show-msg");
        pwpass = true;
    } else {
        msg.style.color = "red";
        msg.textContent = "Passwords mismatch.";
        msg.classList.remove("hidden-msg");
        msg.classList.add("show-msg");
        pwpass = false;
    }
}

function idalert(res){
    let msg = document.getElementById("idmsg");
    if (!res) {
        msg.style.color = "green";
        msg.textContent = "This is an available ID";
        msg.classList.remove("hidden-msg");
        msg.classList.add("show-msg");
        idpass = true;
    } else {
        msg.style.color = "red";
        msg.textContent = "ID already exists";
        msg.classList.remove("hidden-msg");
        msg.classList.add("show-msg");
        idpass = false;
    }
}

async function idcheck() {
    const id = document.getElementById("id").value;
    if(id===""){
        return false;
    }
    try {
        const response = await fetch('/idcheck', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json' // ✅ JSON을 보낼 경우 필수
            },
            body: JSON.stringify({id: id}) // ✅ JSON.stringify() 필요
        });

        if (!response.ok) {
            throw new Error(`Server error: ${response.status}`);
        }

        const res = await response.json();
        idalert(res);

    } catch (error) {
        console.error('Error:', error);
        alert('오류가 발생했습니다.');
    }
}

function signUpPass(){
    if(!idpass && pwpass){
        alert("Empty field exists")
    }
    return idpass && pwpass;
}