function getBaseUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/user";
}

function getAllUsers() {
    var url = getBaseUrl();
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            $.notify("got all users", "success");
            console.log(data);
            setUsers(data);
        },
        error: function (response) {
            $('.notifyjs-corner').empty();
            $.notify("failed to fetch all users", "error");
        }
    });
}

function showAddUserModel() {
    $('#add-party-modal').modal('toggle');
}

function addUser() {
    var $form = $("#add-party-form");
    var json = toJson($form);
    console.log(json);
    var url = getBaseUrl();
    $.ajax({
        url: url,
        type: 'POST',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            $('#edit-brand-modal').modal('toggle');
            $('.notifyjs-corner').empty();
            $.notify("Updated", "success");
            getAllUsers();
        },
        error: function (response) {
            $('.notifyjs-corner').empty();
            $.notify(response['responseJSON']['message'], { autoHide: false });
        }
    });
}


function setUsers(users) {
    var $tbody = $('#brand-table').find('tbody');
    $tbody.empty();
    $.each(users, function (i, user) {
        var $tr = $('<tr>');
        $tr.append($('<td>').html(user.name));
        $tr.append($('<td>').html(user.type));
        // $tr.append($('<td>').html(user.));
        $tbody.append($tr);
        console.log($tr);
    });
}


function toJson($form) {
    var serialized = $form.serializeArray();
    var s = '';
    var data = {};
    for (s in serialized) {
        data[serialized[s]['name']] = serialized[s]['value']
    }
    var json = JSON.stringify(data);
    return json;
}


function init() {
    $('#add-party').click(showAddUserModel);
    $('#update-brand').click(addUser);
}


$(document).ready(init);
$(document).ready(getAllUsers);