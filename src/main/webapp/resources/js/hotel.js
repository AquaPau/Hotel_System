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
        setFieldError(elem, true);
        return false;
    } else {
        setFieldError(elem, false);
        return true;
    }
}

function setFieldError(elem, flag) {
    if (flag) {
        elem.css({border: '2px solid #ff5a5a'});
    } else {
        elem.css({border: '1px solid #ced4da'});
    }
}

function calendarValidate() {
    var validated = true;
    var checkIn = getDate('checkIn');
    var checkOut = getDate('checkOut');
    var today = new Date();
    if (checkIn <= today) {
        setFieldError($('#checkIn'), true);
        validated = false;
    } else {
        setFieldError($('#checkIn'), false);
    }
    if (checkOut <= checkIn) {
        setFieldError($('#checkOut'), true);
        validated = false;
    } else {
        setFieldError($('#checkOut'), false);
    }
    return validated;
}

function getDate(elemId) {
    var checkin = document.getElementById(elemId).value;
    var split = checkin.split("/");
    var month = split[0];
    var day = split[1];
    var year = split[2];
    return new Date("" + year + "-" + month + "-" + day);
}

$(function () {
    $(".datepicker").datepicker();
});