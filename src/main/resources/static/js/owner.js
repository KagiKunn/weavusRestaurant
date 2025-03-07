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