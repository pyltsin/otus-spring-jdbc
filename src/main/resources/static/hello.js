$(document).ready(reloadTable());

function reloadTable() {
    $.ajax({
        url: "http://rest-service.guides.spring.io/greeting"
    }).then(function (data) {
        console.log(data);
    });
}