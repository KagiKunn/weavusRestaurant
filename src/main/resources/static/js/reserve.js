function cancelReservation(id){
    const f = document.createElement('form');
    f.setAttribute("method", "POST");
    f.setAttribute("action", "/cancelReservation");
    const i = document.createElement('input');
    i.setAttribute("type", "hidden");
    i.setAttribute("name", "id");
    i.value = id;
    f.appendChild(i);
    document.body.appendChild(f);
    f.submit();
}
function ownerCancelReservation(id,rid){
    const f = document.createElement('form');
    f.setAttribute("method", "POST");
    f.setAttribute("action", "/owner/cancelReservation");
    const i = document.createElement('input');
    i.setAttribute("type", "hidden");
    i.setAttribute("name", "id");
    i.value = id;
    f.appendChild(i);
    const j = document.createElement('input');
    j.setAttribute("type", "hidden");
    j.setAttribute("name", "rid");
    j.value = rid;
    f.appendChild(j);
    document.body.appendChild(f);
    f.submit();
}

function confirmReservation(id, rid){
    const f = document.createElement('form');
    f.setAttribute("method", "POST");
    f.setAttribute("action", "/confirmreservation");
    const i = document.createElement('input');
    i.setAttribute("type", "hidden");
    i.setAttribute("name", "id");
    i.value = id;
    f.appendChild(i);
    const j = document.createElement('input');
    j.setAttribute("type", "hidden");
    j.setAttribute("name", "rid");
    j.value = rid;
    f.appendChild(j);
    document.body.appendChild(f);
    f.submit();
}
function favorite(id,rid){
    const f = document.createElement('form');
    f.setAttribute("method", "POST");
    f.setAttribute("action", "/favorite");
    const i = document.createElement('input');
    i.setAttribute("type", "hidden");
    i.setAttribute("name", "id");
    i.value = id;
    f.appendChild(i);
    const j = document.createElement('input');
    j.setAttribute("type", "hidden");
    j.setAttribute("name", "rid");
    j.value = rid;
    f.appendChild(j);
    document.body.appendChild(f);
    f.submit();
}
