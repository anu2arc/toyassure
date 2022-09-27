function getBaseUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/channel-listing";
}

function toJson($form) {
    var serialized = $form.serializeArray();
    console.log(serialized);
    var s = '';
    var data = {};
    for (s in serialized) {
        data[serialized[s]['name']] = serialized[s]['value']
    }
    return data;
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
    $('#upload-submit').click(processData);
}


// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;

function processData() {
    var file = $('#customFile')[0].files[0];
    console.log(file);
    // if (document.getElementById("clientId").value == "") {
    //     document.getElementById("missingClientId").innerHTML = "*please provide a clientId";
    // }
    // else {
    //     document.getElementById("missingClientId").innerHTML = "";
    //     if (file == undefined) {
    //         document.getElementById("missingFile").innerHTML = "*Please select a file";
    //     }
    //     else {
    //         document.getElementById("missingFile").innerHTML = "";
    readFileData(file, readFileDataCallback);
    //     }
    // }
}

function readFileData(file, callback) {
    var config = {
        header: true,
        delimiter: "\t",
        skipEmptyLines: "greedy",
        complete: function (results) {
            callback(results);
        }
    }
    Papa.parse(file, config);
}

function readFileDataCallback(results) {
    fileData = results.data;
    uploadRows();
}

function uploadRows() {
    var $form = $("#add-product-form");
    var json = toJson($form);
    json['channelList'] = fileData;
    console.log(json);
    var url = getBaseUrl();
    $.ajax({
        url: url,
        type: 'POST',
        data: JSON.stringify(json),
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            $('.notifyjs-corner').empty();
            $.notify("Upload Successful", "success");
            fecthAllChannels();
        },
        error: function (response) {
            console.log(response['responseJSON']);
            errorData.push(response['responseJSON']);
            console.log(errorData);
            processCount = fileData.length;
            $('.notifyjs-corner').empty();
            $.notify("Error please download error report", { autoHide: false });
            // updateUploadDialog();
            downloadErrors();
        }
    });
}

function downloadErrors() {
    writeFileData(errorData);
}

function resetUploadDialog() {
    //Reset file name
    var $file = $('#employeeFile');
    $file.val('');
    $('#employeeFileName').html("Choose File");
    //Reset various counts
    processCount = 0;
    fileData = [];
    errorData = [];
    //Update counts	
    updateUploadDialog();
}

function updateUploadDialog() {
    // $('#rowCount').html("" + fileData.length);
    // $('#processCount').html("" + processCount);
    // if (errorData.length > 0)
    //     document.getElementById('download-errors').disabled = false;
    // else
    //     document.getElementById('download-errors').disabled = true;

}

function updateFileName() {
    var $file = $('#employeeFile');
    var fileName = $file.val();
    $('#employeeFileName').html(fileName);
}

function displayUploadData() {
    resetUploadDialog();
    $('#upload-employee-modal').modal('toggle');
}



function writeFileData(arr) {
    console.log(arr);
    var config = {
        quoteChar: '',
        escapeChar: '',
        delimiter: "\n"
    };

    var data = Papa.unparse(arr, config);
    var blob = new Blob([data], { type: 'text/tsv;charset=utf-8;' });
    var fileUrl = null;

    if (navigator.msSaveBlob) {
        fileUrl = navigator.msSaveBlob(blob, 'download.tsv');
    } else {
        fileUrl = window.URL.createObjectURL(blob);
    }
    var tempLink = document.createElement('a');
    tempLink.href = fileUrl;
    tempLink.setAttribute('download', 'download.tsv');
    tempLink.click();
}


$(document).ready(init);
$(document).ready(fecthAllChannels);