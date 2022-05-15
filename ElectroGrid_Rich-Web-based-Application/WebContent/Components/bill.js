/**
 * 
 */

//initially hide the error msg and success msg
$(document).ready(function()
{
$("#alertSuccess").hide();
$("#alertError").hide();
})

$(document).on("click", "#CalcKwh", function(event)
{
	console.log("calculate btn clicked");
	var units=$("#units").val();
	console.log("no of units",units);
	if(units<300){
		$("#KWHCharge").val(units*10);
	}else{
		$("#KWHCharge").val(units*12);
	}
	
	
	
});

$(document).on("click", "#CalcTot",function(event){
	var kwh=parseFloat($("#KWHCharge").val());
	var fixed=parseFloat($("#fixedCharge").val());
	var rebate=parseFloat($("#rebate").val());
	$("#total").val(kwh+fixed-rebate);
});




$(document).on("click", "#btnSave", function(event)
{
	console.log("save button clicked");
// Clear alerts---------------------
$("#alertSuccess").text("");
$("#alertSuccess").hide();
$("#alertError").text("");
$("#alertError").hide();

// Form validation-------------------
var status = validateItemForm();
if (status != true)
{
$("#alertError").text(status);
$("#alertError").show();
return;
}
// If valid------------------------
var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT";
$.ajax(
{
url : "BillAPI",
type : type,
data : $("#formItem").serialize(),
dataType : "text",
complete : function(response, status)
{
onItemSaveComplete(response.responseText, status);
}
});
});


function validateItemForm()
{
//billcode
if ($("#billCode").val().trim() == "")
{
return "Insert bill Code.";
}

//cusId
if ($("#customerID").val().trim() == "")
{
return "Insert customer Id.";
}
//month
if ($("#month").val().trim() == "")
{
return "Insert month.";
}
//units
if ($("#units").val().trim() == "")
{
return "Insert no of units.";
}
// is numerical value
var tmpUnits = $("#units").val().trim();
if (!$.isNumeric(tmpUnits))
{
return "Insert a numerical value for units";
}
//kwh charge
if ($("#KWHCharge").val().trim() == "")
{
return "Insert kwh charge.";
}
// is numerical value
var tmpKCharge = $("#KWHCharge").val().trim();
if (!$.isNumeric(tmpKCharge))
{
return "Insert a numerical value for KWH Charge";
}
// convert to decimal price
$("#KWHCharge").val(parseFloat(tmpKCharge).toFixed(2));
//fixedcharge
if ($("#fixedCharge").val().trim() == "")
{
return "Insert fixed charge.";
}
// is numerical value
var tmpf = $("#fixedCharge").val().trim();
if (!$.isNumeric(tmpf))
{
return "Insert a numerical value for fixed Charge";
}
// convert to decimal price
$("#fixedCharge").val(parseFloat(tmpf).toFixed(2));
//rebate
if ($("#rebate").val().trim() == "")
{
return "Insert rebate.";
}
// is numerical value
var tmpreb = $("#rebate").val().trim();
if (!$.isNumeric(tmpreb))
{
return "Insert a numerical value for rebate";
}
// convert to decimal price
$("#rebate").val(parseFloat(tmpreb).toFixed(2));
//total
if ($("#total").val().trim() == "")
{
return "Insert total";
}
var tmptot = $("#total").val().trim();
if (!$.isNumeric(tmptot))
{
return "Insert a numerical value for total";
}
// convert to decimal price
$("#total").val(parseFloat(tmptot).toFixed(2));
return true;
}

function onItemSaveComplete(response, status)
{
if (status == "success")
{
var resultSet = JSON.parse(response);
if (resultSet.status.trim() == "success")
{
$("#alertSuccess").text("Successfully saved.");
$("#alertSuccess").show();
$("#divItemsGrid").html(resultSet.data);
} else if (resultSet.status.trim() == "error")
{
$("#alertError").text(resultSet.data);
$("#alertError").show();
}
} else if (status == "error")
{
$("#alertError").text("Error while saving.");
$("#alertError").show();
} else
{
$("#alertError").text("Unknown error while saving..");
$("#alertError").show();
}
$("#hidItemIDSave").val("");
$("#formItem")[0].reset();
}


$(document).on("click", ".btnUpdate", function(event)
{
$("#hidItemIDSave").val($(this).data("itemid"));
$("#billCode").val($(this).closest("tr").find('td:eq(0)').text());
$("#customerID").val($(this).closest("tr").find('td:eq(1)').text());
$("#month").val($(this).closest("tr").find('td:eq(2)').text());
$("#units").val($(this).closest("tr").find('td:eq(3)').text());
$("#KWHCharge").val($(this).closest("tr").find('td:eq(4)').text());
$("#fixedCharge").val($(this).closest("tr").find('td:eq(5)').text());
$("#rebate").val($(this).closest("tr").find('td:eq(6)').text());
$("#total").val($(this).closest("tr").find('td:eq(7)').text());
});

$(document).on("click", ".btnRemove", function(event)
{

$.ajax(
{
	
url : "BillAPI",
type : "DELETE",
data : "billId=" + $(this).data("itemid"),
dataType : "text",
complete : function(response, status)
{
onItemDeleteComplete(response.responseText, status);
}
});
});

function onItemDeleteComplete(response, status)
{
if (status == "success")
{
var resultSet = JSON.parse(response);
if (resultSet.status.trim() == "success")
{
$("#alertSuccess").text("Successfully deleted.");
$("#alertSuccess").show();
$("#divItemsGrid").html(resultSet.data);
} else if (resultSet.status.trim() == "error")
{
$("#alertError").text(resultSet.data);
$("#alertError").show();
}
} else if (status == "error")
{
$("#alertError").text("Error while deleting.");
$("#alertError").show();
} else
{
$("#alertError").text("Unknown error while deleting..");
$("#alertError").show();
}
}



$(document).on("click", "#btnView", function(event)
{
		const value = $("#billCode").val();
		console.log(value);
$.ajax(
{
url : "BillAPI",
type : "GET",
data : "billCode=" +  $("#billCode").val(),
dataType : "text",
complete : function(response, status)
{
onItemViewComplete(response.responseText, status);
}
});
});


function onItemViewComplete(response, status)
{
if (status == "success")
{
var resultSet = JSON.parse(response);

if (resultSet.status.trim() == "success")
{
$("#alertSuccess").text("Successfully Get.");
$("#alertSuccess").show();
$("#divBillGrid").html(resultSet.data);
} else if (resultSet.status.trim() == "error")
{
$("#alertError").text(resultSet.data);
$("#alertError").show();
}
} else if (status == "error")
{
$("#alertError").text("Error while get.");
$("#alertError").show();
} else
{
$("#alertError").text("Unknown error while getting..");
$("#alertError").show();
}
}
