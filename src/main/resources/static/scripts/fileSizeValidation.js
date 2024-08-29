let files = document.querySelector("#files");
let pfp = document.querySelector("#profilePicture");

if(files != null){
    files.onchange = (event) => {
        let submit = document.querySelector("input[type='submit']");
        let cantSubmit = false;
        let errors = document.querySelector("#file-errors");
        errors.innerHTML = "";
        for(let el of event.target.files){
            if(el.size > 1049000){
                errors.innerHTML += `File ${el.name} is too large (MAX: 1MB)<br>`;
                cantSubmit = true;
            }
        }
        submit.disabled = cantSubmit;
    };
}

if(pfp != null){
    pfp.onchange = (event) => {
        let submit = document.querySelector("input[type='submit']");
        let cantSubmit = false;
        let errors = document.querySelector("#file-errors");
        errors.innerHTML = "";
        if(event.target.files[0].size > 1049000){
            errors.innerHTML += `File ${event.target.files[0].name} is too large (MAX: 1MB)<br>`;
            cantSubmit = true;
        }
        submit.disabled = cantSubmit;
    };
}
