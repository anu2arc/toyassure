function getBaseUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/channel";
}

function toJson($form) {
	var serialized = $form.serializeArray();
	console.log(serialized);
	var s = '';
	var data = {};
	for (s in serialized) {
		data[serialized[s]['name']] = serialized[s]['value']
	}
	var json = JSON.stringify(data);
	return json;
}



function getAllChannels() {
    var url = getBaseUrl();
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            $.notify("got all users", "success");
            console.log(data);
            setChannels(data);
        },
        error: function (response) {
            $('.notifyjs-corner').empty();
            $.notify("failed to fetch all users", "error");
        }
    });
}

function setChannels(channels) {
    var $tbody = $('#brand-table').find('tbody');
    $tbody.empty();
    $.each(channels, function (i, channel) {
        var $tr = $('<tr>');
        $tr.append($('<td>').html(channel.name));
        $tr.append($('<td>').html(channel.invoiceType));
        // $tr.append($('<td>').html(user.));
        $tbody.append($tr);
        console.log($tr);
    });
}




function addChannel() {
    var $form = $("#add-channel-form");
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
            $('#add-channel-modal').modal('toggle');
            $('.notifyjs-corner').empty();
            $.notify("Updated", "success"); 
            getAllChannels();
        },
        error: function (response) {
            $('.notifyjs-corner').empty();
            $.notify(response['responseJSON']['message'], { autoHide: false });
        }
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



function showAddUserModel() {
    $('#add-channel-modal').modal('toggle');
}

function init() {
    $('#add-channel').click(showAddUserModel);
    $('#add-submit').click(addChannel);
}


$(document).ready(init);
$(document).ready(getAllChannels);