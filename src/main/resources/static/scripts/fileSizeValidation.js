document.querySelector("#files").onchange = (event) => {
    let submit = document.querySelector("input[type='submit']");
    let canSubmit = false;
    let errors = document.querySelector("#file-errors");
    errors.innerHTML = "";
    for(let el of event.target.files){
        if(el.size > 1049000){
            errors.innerHTML += `File ${el.name} is too large (MAX: 1MB)<br>`;
            canSubmit = true;
        }
    }
    submit.disabled = canSubmit;
};
