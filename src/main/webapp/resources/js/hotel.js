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

    validated = validate($('#login-input'), loginMatcher, $('#login-error'));
    validated = validate($('#fn-input'), firstNameMatcher, $('#fn-error')) && validated;
    validated = validate($('#ln-input'), lastNameMatcher, $('#ln-error')) && validated;
    validated = validate($('#pw-input'), passwordMatcher, $('#pw-error')) && validated;

    if (password !== confirm) {
        setFieldErrorColor($('#pw-input'), true);
        setFieldErrorColor($('#confirm-input'), true);
        showError($('#confirm-error'), true);
        validated = false;
    } else {
        setFieldErrorColor($('#confirm-input'), false);
        showError($('#confirm-error'), false);
    }
    return validated;
}

function validate(elem, matcher, error) {
    if (matcher == null) {
        setFieldErrorColor(elem, true);
        showError(error, true);
        return false;
    } else {
        setFieldErrorColor(elem, false);
        showError(error, false);
        return true;
    }
}

function setFieldErrorColor(elem, flag) {
    if (flag) {
        elem.css({border: '2px solid #ff5a5a', backgroundColor: '#ffc6c6'});
    } else {
        elem.css({border: '1px solid #ced4da', backgroundColor: '#fff'});
    }
}

function calendarValidate() {
    var validated = true;
    var checkIn = getDate('checkIn');
    var checkOut = getDate('checkOut');
    var today = new Date();
    if (checkIn <= today) {
        setFieldErrorColor($('#checkIn'), true);
        showError($('#checkIn-error'), true);
        validated = false;
    } else {
        setFieldErrorColor($('#checkIn'), false);
        showError($('#checkIn-error'), false);
    }
    if (checkOut <= checkIn) {
        setFieldErrorColor($('#checkOut'), true);
        showError($('#checkOut-error'), true);
        validated = false;
    } else {
        setFieldErrorColor($('#checkOut'), false);
        showError($('#checkOut-error'), false);
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

function showError(error, flag) {
    flag = !flag;
    error.attr("hidden", flag);
}

function validateLoginPage() {
    var currentLocation = window.location.toString();
    if (currentLocation.endsWith("error")) {
        setFieldErrorColor($('#un-login-input'), true);
        setFieldErrorColor($('#pw-login-input'), true);
        showError($('#lgn-error'), true);
    }
}

$(function () {
    $(".datepicker").datepicker();
});