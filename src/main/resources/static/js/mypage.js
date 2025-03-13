document.addEventListener("DOMContentLoaded", () => {
    console.log("DOMContentLoaded");
    document.querySelectorAll('input').forEach(el => {
        if(el.id===document.getElementById("mo-in").id){
            return;
        }
        el.setAttribute('disabled', 'true');
        el.classList.add('readonly-box')
    })
});

function changeInfo(){
    const cbtn= document.getElementById("changebtn");
    const sbtn = document.getElementById("submitbtn");
    const dlink = document.getElementById("delete");
    cbtn.classList.add("hidden-msg");
    sbtn.classList.remove("hidden-msg");
    dlink.classList.remove("text-warning");
    dlink.classList.add("text-danger");
    dlink.textContent = "Are you sure you want to delete?";
    document.querySelectorAll('input').forEach(el => {
        el.removeAttribute('disabled');
        el.classList.remove('readonly-box');
    })
    dlink.addEventListener("click", function() {
        let userId = this.getAttribute("data-id");
        deleteUser(userId);
    });
}

async function originalPwCheck() {
    const pw = document.getElementById("mo-in").value;
    const csrfToken = document.querySelector('input[name="_csrf"]').value;
    if(pw===""){
        return false;
    }
    try {
        const response = await fetch('/pwcheck', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json', // ✅ JSON을 보낼 경우 필수
                'X-CSRF-TOKEN' : csrfToken
            },
            body: JSON.stringify({pw: pw}) // ✅ JSON.stringify() 필요
        });

        if (!response.ok) {
            throw new Error(`Server error: ${response.status}`);
        }

        const res = await response.json();
        if(res){
            changeInfo();
        }
        else{
            console.log('false'+res);
        }

    } catch (error) {
        console.error('Error:', error);
        alert('오류가 발생했습니다.');
    }
}

function deleteUser(id) {
    let f = document.createElement("form");
    f.setAttribute("method", "POST");
    f.setAttribute("action", "/deleteuser");

    let i = document.createElement("input");
    i.setAttribute("type", "hidden");  // 🔹 숨겨진 입력 필드로 설정
    i.setAttribute("name", "id");      // 🔹 서버에서 받을 수 있도록 name 설정
    i.value = id;

    f.appendChild(i);
    document.body.appendChild(f);
    f.submit();
}
