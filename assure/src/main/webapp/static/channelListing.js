function getBaseUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/channel-listing";
}



function uploadChannelListing() {

}

function fecthAllChannels() {
    var url = getBaseUrl();
    $.ajax({
        url: url,
        type: 'GET',
        success: function (ChannelData) {
            $.notify("got channels", "success");
            setChannel(ChannelData);
        },
        error: function (response) {
            $('.notifyjs-corner').empty();
            $.notify("failed to fetch all users", "error");
        }
    });
}

function setChannel(ChannelData) {
    var $tbody = $('#brand-table').find('tbody');
    $tbody.empty();
    $.each(ChannelData, function (i, channel) {
        var $tr = $('<tr>');
        $tr.append($('<td>').html(channel.channelId));
        $tr.append($('<td>').html(channel.channelSkuId));
        $tr.append($('<td>').html(channel.clientId));
        $tr.append($('<td>').html(channel.globalSkuId));
        // $tr.append($('<td>').html(user.));
        $tbody.append($tr);
        console.log($tr);
    });
}


function showaddlistingmodel() {
    $('#add-channel-modal').modal('toggle');
}


function init() {

    $('#add-channel-listing').click(showaddlistingmodel);
    $('#upload-submit').click(uploadChannelListing);
}


$(document).ready(init);
$(document).ready(fecthAllChannels);