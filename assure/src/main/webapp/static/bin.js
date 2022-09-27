function getBaseUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/bin";
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

function createBins() {
    var url = getBaseUrl() + '?noOfBin=' + document.getElementById('noOfBins').value;
    json = "";
    $.ajax({
        url: url,
        type: 'POST',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            $('.notifyjs-corner').empty();
            $.notify("Bins created", "success");
        },
        error: function (response) {
            $('.notifyjs-corner').empty();
            console.log(response);
            $.notify("error", { autoHide: false });
        }
    });
}

function fetchAllBins() {
    var url = getBaseUrl() + "/sku";
    $.ajax({
        url: url,
        type: 'GET',
        success: function (binSkuData) {
            $.notify("got all bins list", "success");
            setBinData(binSkuData);
        },
        error: function (response) {
            $('.notifyjs-corner').empty();
            $.notify("failed to fetch all users", "error");
        }
    });
}


function setBinData(binSkuData) {
    console.log(binSkuData);
    var $tbody = $('#binSku-table').find('tbody');
    $tbody.empty();
    var button = 'type="button" class="btn btn-primary"';
    for (var index in binSkuData) {
        var data = binSkuData[index];
        var buttonHtml = ' <button ' + button + ' onclick="editBinSku(' + data.id + ')">Edit</button>';
        var row = '<tr>'
            + '<td>' + data.id + '</td>'
            + '<td>' + data.binId + '</td>'
            + '<td>' + data.globalSkuId + '</td>'
            + '<td>' + data.quantity + '</td>'
            + '<td>' + buttonHtml + '</td>'
            + '</tr>';
        $tbody.append(row);
    }
}

function editQuantity() {
    var url = getBaseUrl() + '/sku/' + document.getElementById('hiddengsku').value;
    var $form = $("#edit-quantity-form");
    var json = toJson($form);
    $.ajax({
        url: url,
        type: 'PUT',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            $('#edit-bins-modal').modal('toggle');
            $('.notifyjs-corner').empty();
            $.notify("Updated", "success");
            fetchAllBins();     //...
        },
        error: function (response) {
            $('.notifyjs-corner').empty();
            $.notify(response['responseJSON']['message'], { autoHide: false });
        }
    });
}

function editBinSku(id) {
    $('#edit-bins-modal').modal('toggle');
    document.getElementById('hiddengsku').value = id;

}

function generatebinModel() {
    $('#generate-bins-modal').modal('toggle');
}

function addbinSkuModel() {
    $('#add-binsku-modal').modal('toggle');
}



function init() {

    $('#generate-bin').click(generatebinModel);
    $('#add-bin').click(addbinSkuModel);
    $('#generate-submit').click(createBins);
    $('#Add-submit').click(processData);
    $('#edit-submit').click(editQuantity);
}

// csv handling for productDataList

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;

function processData() {
    var file = $('#customFile')[0].files[0];
    console.log(file);
    if (document.getElementById("clientId").value == "") {
        document.getElementById("missingClientId").innerHTML = "*please provide a clientId";
    }
    else {
        document.getElementById("missingClientId").innerHTML = "";
        if (file == undefined) {
            document.getElementById("missingFile").innerHTML = "*Please select a file";
        }
        else {
            document.getElementById("missingFile").innerHTML = "";
            readFileData(file, readFileDataCallback);
        }
    }

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
    var json = JSON.stringify(fileData);
    console.log(json);
    var url = getBaseUrl() + '/upload/' + document.getElementById('clientId').value;
    $.ajax({
        url: url,
        type: 'POST',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            $('.notifyjs-corner').empty();
            $.notify("Upload Successful", "success");
            fetchAllBins();
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
$(document).ready(fetchAllBins);
