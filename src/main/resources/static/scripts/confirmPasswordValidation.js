function checkPasswords(){
    let first = document.querySelector("#password");
    let second = document.querySelector("#password2");
    if(first.value === second.value){
        document.querySelector("input[type='submit']").disabled = false;
        document.querySelector("#password-fail").style.display = "none";
    }
    else{
        document.querySelector("input[type='submit']").disabled = true;
        document.querySelector("#password-fail").style.display = "block";
    }
}
document.querySelector("#password").onchange = checkPasswords;
document.querySelector("#password2").onchange = checkPasswords;
