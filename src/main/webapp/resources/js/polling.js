$(document).ready(function() {
	
});

/*
 * print messages
 */
function printMessages(data) {
	$("#content").append(data);
}

/*
 * clear inputField
 */
function clearInputField() {
	$("#inputField").val("");
}

/*
 * play Sound
 */
function playSound() {
	
}

/*
 * send message
 */
function sendMessage(message) {
	$.ajax({
		url: "polling/sendMessage",
		type: "POST",
		data: message,
		//cache: false,
		contentType : "application/json; charset=UTF-8",
		success: function(data) {
			getMessages();
			clearInputField();
		},
		error : function(xhr, status, errorThrown) {
			//alert("sending failed with status: " + status + ". " + errorThrown);
		}
	});
}

/*
 * get messages
 */
function getMessages() {
	$.ajax({
		url: "polling/getMessages",
		type: "POST",
		//cache: false,
		success: function(data) {
			if (data != 0) {
				printMessages(data);
				playSound();
				scrollToBottom("content");
			}
		},
		error : function(xhr, status, errorThrown) {
			//alert("sending failed with status: " + status + ". " + errorThrown);
		}
	});
}

/*
 * show active clients
 */
function showOnline() {
	$.ajax({
		url: "polling/showOnline",
		type: "POST",
		//cache: false,
		success: function(data) {
			if (data != 0) {
				$("#online").html(data);
			}
		},
		error : function(xhr, status, errorThrown) {
			//alert("sending failed with status: " + status + ". " + errorThrown);
		}
	});
}

/*
 * show typing clients
 */
function showTyping() {
	$.ajax({
		url: "polling/showTyping",
		type: "POST",
		//cache: false,
		success: function(data) {
			if (data != 0) {
				$("#typing").html(data);
			}
		},
		error : function(xhr, status, errorThrown) {
			//alert("sending failed with status: " + status + ". " + errorThrown);
		}
	});
}

/*
 * close
 */
function close() {
	$.ajax({
		url: "polling/close",
		type: "POST",
		//cache: false,
		error : function(xhr, status, errorThrown) {
			//alert("sending failed with status: " + status + ". " + errorThrown);
		}
	});
}

/*
 * scroll to bottom
 */
function scrollToBottom(elementId) {
	var frame = document.getElementById(elementId);
	
	if (frame.scrollTop >= (frame.scrollHeight - frame.offsetHeight - 200))
		frame.scrollTop = frame.scrollHeight - frame.offsetHeight;
}

/* listeners */

/*
 * if to press send-button then send message
 */
$("#sendButton").click(function() {
	if ($("#inputField").val().length !== 0) {
		sendMessage($("#inputField").val());
		$("#inputField").focus();
	}
});

/*
 * focus to textField
 */
$("#inputField").focus();

/*
 * if to press enter key then send message
 */
$("#inputField").keypress(function(event) {
	if ($("#inputField").val().length !== 0) {
		if (event.which == 13) {
			sendMessage($("#inputField").val());
		}
	}
});

/*
 * if close window then delete client
 */
window.onbeforeunload = function(event) {
	close();
}

/*
 * don't send form
 */
$("form").submit(function(event) {
	event.preventDefault();
});

/*
 * Polling
 */
var poolGetMessages = 1000 * 1;
var poolShowOnline = 1000 * 5;
var poolShowTyping = 1000 * 1;

getMessages();
showOnline();
showTyping();
setInterval('getMessages()', poolGetMessages);
setInterval('showOnline()', poolShowOnline);
setInterval('showTyping()', poolShowTyping);