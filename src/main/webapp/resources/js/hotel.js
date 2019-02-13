function validateSignInForm() {
    var validated;

    var login = document.forms["sign-in-form"]["login"].value;
    var firstName = document.forms["sign-in-form"]["firstName"].value;
    var lastName = document.forms["sign-in-form"]["lastName"].value;
    var password = document.forms["sign-in-form"]["password"].value;
    var confirm = document.forms["sign-in-form"]["confirm"].value;

    var loginMatcher = login.match(/^\w{5,20}$/);
    var firstNameMatcher = firstName.match(/^[a-zA-Z]{3,20}$/);
    var lastNameMatcher = lastName.match(/^[a-zA-Z]{3,20}$/);
    var passwordMatcher = password.match(/^.{6,50}$/);


    validated = validate($('#login-input'), loginMatcher);
    validated = validate($('#fn-input'), firstNameMatcher) && validated;
    validated = validate($('#ln-input'), lastNameMatcher) && validated;
    validated = validate($('#pw-input'), passwordMatcher) && validated;

    if (password !== confirm) {
        $('#pw-input').css({border: '2px solid #ff5a5a'});
        $('#confirm-input').css({border: '2px solid #ff5a5a'});
        validated = false;
    } else {
        $('#confirm-input').css({border: '1px solid #ced4da'});
    }
    return validated;
}

function validate(elem, matcher) {
    if (matcher == null) {
        elem.css({border: '2px solid #ff5a5a'});
        return false;
    } else {
        elem.css({border: '1px solid #ced4da'});
        return true;
    }
}

//ToDo
function calendarValidate(){
    var checkin =$('#checkIn').value;
    alert(checkin);
   // new Date().format('m-d-Y h:i:s');
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth() + 1;
    var yyyy = today.getFullYear();

}

$(function () {
    $(".datepicker").datepicker();
});