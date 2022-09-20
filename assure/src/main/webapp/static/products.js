function getBaseUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/product";
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
    // var globalSkuID = document.getElementById('globalsku').value;
    var url = getBaseUrl() + '/' + '28';
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
}




$(document).ready(init);
$(document).ready(getAllProducts);