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
        showElement($('#confirm-error'), true);
        validated = false;
    } else {
        setFieldErrorColor($('#confirm-input'), false);
        showElement($('#confirm-error'), false);
    }
    return validated;
}

function validate(elem, matcher, error) {
    if (matcher == null) {
        setFieldErrorColor(elem, true);
        showElement(error, true);
        return false;
    } else {
        setFieldErrorColor(elem, false);
        showElement(error, false);
        return true;
    }
}

function setActive(id) {
    $('.menu-btn').removeClass("menu-active");
    $('#' + id).addClass('menu-active');
}

function adjustUrl(url, role) {
    const href = window.location.href.toString();
    if (!href.includes(url)) {
        window.history.pushState("", "", `/${role}?${url}`);
    }
}

function userMenuNavigation(id) {
    setActive(id);
    showElement($('.index-table'), false);
    const role = "index";
    switch (id) {
        case 'menu-rending':
            showElement($('#unprocessed-requests'), true);
            adjustUrl('ur_page', role);
            break;
        case 'menu-approved':
            showElement($('#processed-requests'), true);
            adjustUrl('pr_page', role);
            break;
        case 'menu-denied':
            showElement($('#denied-requests'), true);
            adjustUrl('dr_page', role);
            break;
    }
}

function indexUrls() {
    var href = window.location.href.toString();
    if (href.includes('index')) {
        if (href.includes('ur_page')) {
            userMenuNavigation('menu-rending');
            return
        }
        if (href.includes('pr_page')) {
            userMenuNavigation('menu-approved');
            return
        }
        if (href.includes('dr_page')) {
            userMenuNavigation('menu-denied');
            return
        }
        userMenuNavigation('menu-rending');
    }
    if (href.includes('rooms')) {
        userMenuNavigation('menu-rooms');
        return
    }
    if (href.includes('request/new')) {
        userMenuNavigation('menu-newrequest');
        return
    }

}

function adminMenuNavigation(id) {
    setActive(id);
    showElement($('.index-table'), false);
    const role = "admin";
    switch (id) {
        case 'menu-pending':
            showElement($('#pending-requests'), true);
            adjustUrl('prq_page', role);
            break;
        case 'menu-approved':
            showElement($('#approved-requests'), true);
            adjustUrl('arq_page', role);
            break;
        case 'menu-denied':
            showElement($('#denied-requests'), true);
            adjustUrl('drq_page', role);
            break;
        case 'menu-rooms':
            showElement($('#room-list'), true);
            adjustUrl('rl_page', role);
            break;
    }
}

function adminUrls() {
    const href = window.location.href.toString();
    if (href.includes('index')) {
        if (href.includes('prq_page')) {
            adminMenuNavigation('menu-pending');
            return
        }
        if (href.includes('arq_page')) {
            adminMenuNavigation('menu-approved');
            return
        }
        if (href.includes('drq_page')) {
            adminMenuNavigation('menu-denied');
            return
        }
        adminMenuNavigation('menu-pending');
    }
    if (href.includes('rooms')) {
        adminMenuNavigation('menu-rooms');
        return
    }
    if (href.includes('rooms/new')) {
        adminMenuNavigation('menu-newroom');
        return
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
        showElement($('#checkIn-error'), true);
        validated = false;
    } else {
        setFieldErrorColor($('#checkIn'), false);
        showElement($('#checkIn-error'), false);
    }
    if (checkOut <= checkIn) {
        setFieldErrorColor($('#checkOut'), true);
        showElement($('#checkOut-error'), true);
        validated = false;
    } else {
        setFieldErrorColor($('#checkOut'), false);
        showElement($('#checkOut-error'), false);
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

function showElement(element, flag) {
    element.attr("hidden", !flag);
}

function validateLoginPage() {
    var currentLocation = window.location.toString();
    if (currentLocation.endsWith("error")) {
        setFieldErrorColor($('#un-login-input'), true);
        setFieldErrorColor($('#pw-login-input'), true);
        showElement($('#lgn-error'), true);
    }
}

function showDenyPopup(flag, id) {
    showElement($('#deny-div-' + id), flag);
    showElement($('#deny-a-' + id), !flag);
}

function showPaymentMessage(flag) {
    showElement($('#payment'), flag);
}

function back() {
    window.history.back();
}

$(".datepicker").datepicker({dateFormat: 'mm/dd/yy'});

$(".price-changer").change(function () {
    $('#price').val(getPrice());
});

function getPrice() {
    var classIndex = document.getElementById('selClass').selectedIndex;
    var capacityIndex = document.getElementById('selCapacity').selectedIndex;

    var basic = 15;
    var classFactor = 1.5;
    var capacityFactor = 0.6;

    var price = (basic + basic * capacityIndex * capacityFactor) * Math.pow(classFactor, classIndex);
    return price.toFixed(2).toString().replace(',','.');
}

function setInputFilter(textbox, inputFilter) {
    ["input", "keydown", "keyup", "mousedown", "mouseup", "select", "contextmenu", "drop"].forEach(function(event) {
        textbox.addEventListener(event, function() {
            if (inputFilter(this.value)) {
                this.oldValue = this.value;
                this.oldSelectionStart = this.selectionStart;
                this.oldSelectionEnd = this.selectionEnd;
            } else if (this.hasOwnProperty("oldValue")) {
                this.value = this.oldValue;
                this.setSelectionRange(this.oldSelectionStart, this.oldSelectionEnd);
            }
        });
    });
}
setInputFilter(document.getElementById("price"), function(value) {
    return /^-?\d*[.,]?\d{0,2}$/.test(value); });
