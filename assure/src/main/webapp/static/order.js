function getBaseUrl() {
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/order";
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


function fetchAllOrders() {
	var url = getBaseUrl();
	$.ajax({
		url: url,
		type: 'GET',
		success: function (orderData) {
			$.notify("got all orders", "success");
			setBinData(orderData);
		},
		error: function (response) {
			$('.notifyjs-corner').empty();
			$.notify("failed to fetch all orders", "error");
		}
	});
}

function setBinData(orderData) {
	console.log(orderData);
	var $tbody = $('#order-table').find('tbody');
	$tbody.empty();
	var button = 'type="button"';
	for (var index in orderData) {
		var color = 'class="btn btn-info"';
		var message = "Allocate"
		var data = orderData[index];
		console.log(data.status, data.status == 'CREATED');
		if (data.status == 'FULFILLED') { color = 'class="btn btn-success"'; message = "Invoice" }
		var buttonHtml = ' <button ' + button + color + ' onclick="editBinSku(' + data.id + ')">' + message + '</button>';
		var row = '<tr>'
			+ '<td>' + data.id + '</td>'
			+ '<td>' + data.clientId + '</td>'
			+ '<td>' + data.customerId + '</td>'
			+ '<td>' + data.channelId + '</td>'
			+ '<td>' + data.channelOrderId + '</td>'
			+ '<td>' + data.status + '</td>'
			+ '<td>' + buttonHtml + '</td>'
			+ '</tr>';
		$tbody.append(row);
	}
}

function showPlaceOrderModel() {
	$('#place-order-modal').modal('toggle');
}

function init() {
	$('#place-order').click(showPlaceOrderModel);
	$('#order-submit').click(processData);
	// $('#generate-submit').click(createBins);
	// $('#Add-submit').click(processData);
	// $('#edit-submit').click(editQuantity);
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;

function processData() {
	var file = $('#customFile')[0].files[0];
	// console.log(file);
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
}
//     }

// }

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
	var $form = $("#place-order-form");
	var json = toJson($form);
	json['orderItems'] = fileData;
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
$(document).ready(fetchAllOrders);

