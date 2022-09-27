function getBaseUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/product";
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

function getAllProducts() {
    var url = getBaseUrl();
    $.ajax({
        url: url,
        type: 'GET',
        success: function (productData) {
            $.notify("got all Products list", "success");
            console.log(productData);
            setProductData(productData);
        },
        error: function (response) {
            $('.notifyjs-corner').empty();
            $.notify("failed to fetch all users", "error");
        }
    });
}

function setProductData(productData) {
    var $tbody = $('#product-table').find('tbody');
    $tbody.empty();
    var button = 'type="button" class="btn btn-primary"';
    for (var index in productData) {
        var data = productData[index];
        console.log(data);
        console.log(data.globalSkuId);
        var buttonHtml = ' <button ' + button + ' onclick="editProduct(' + data.globalSkuId + ')">Edit</button>';
        var row = '<tr>'
            + '<td>' + data.globalSkuId + '</td>'
            + '<td>' + data.clientSkuId + '</td>'
            + '<td>' + data.clientId + '</td>'
            + '<td>' + data.name + '</td>'
            + '<td>' + data.brandId + '</td>'
            + '<td>' + data.mrp + '</td>'
            + '<td>' + data.description + '</td>'
            + '<td>' + buttonHtml + '</td>'
            + '</tr>';
        $tbody.append(row);
    }

}

function showAddUserModel() {
    $('#add-product-modal').modal('toggle');
}

function editProduct(globalSkuID) {
    $('#edit-product-modal').modal('toggle');
    $("#edit-product-form input[name=globalSkuId]").val(globalSkuID);
}

function updateProduct() {
    var url = getBaseUrl() + '/' + document.getElementById('globalsku').value;
    var $form = $("#edit-product-form");
    var json = toJson($form);
    $.ajax({
        url: url,
        type: 'PUT',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            $('#edit-product-modal').modal('toggle');
            $('.notifyjs-corner').empty();
            $.notify("Updated", "success");
            getAllProducts();     //...
        },
        error: function (response) {
            $('.notifyjs-corner').empty();
            $.notify(response['responseJSON']['message'], { autoHide: false });
        }
    });
}


// function addProduct(){

// }

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
    $('#add-product').click(showAddUserModel);
    $('#update-submit').click(updateProduct);
    $('#Add-submit').click(processData);
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
    var url = getBaseUrl() + '/' + document.getElementById('clientId').value;
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
            getAllProducts();//change
            // updateUploadDialog();//change
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
    $('#rowCount').html("" + fileData.length);
    $('#processCount').html("" + processCount);
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
$(document).ready(getAllProducts);