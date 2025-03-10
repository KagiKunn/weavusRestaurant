async function getAddress() {
    const zipcode = document.getElementById('zipcode').value;
    if(zipcode.length < 7) {
        return false
    }
    let address;
    try {
        const response = await fetch(`https://api.zipaddress.net?zipcode=${zipcode}`, {
            method: 'GET',
        });
        if (!response.ok) {
            throw new Error(`Server error: ${response.status}`);
        }
        const res = await response.json();
        address = res.data.fullAddress;
        console.log(address);
        document.getElementById('address').value = address;
    } catch (error) {
        console.error('Error:', error);
    }
}
function restEditPage(){
    const no = document.getElementById('id').value;
    location.href="/owner/edit/"+no;
}

function deleteRestaurant() {
    const no = document.getElementById('id').value;
    const name = document.getElementById('userId').value;
    let f = document.createElement("form");
    f.setAttribute("method", "POST");
    f.setAttribute("action", "/owner/deleterestaurant");

    let i = document.createElement("input");
    i.setAttribute("type", "hidden");
    i.setAttribute("name", "id");
    i.value = no;
    f.appendChild(i);
    let n = document.createElement("input");
    n.setAttribute("type", "hidden");
    n.setAttribute("name", "userId");
    n.value = name;
    f.appendChild(n);
    document.body.appendChild(f);
    f.submit();
}