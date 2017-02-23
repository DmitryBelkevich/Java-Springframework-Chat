$(document).ready(function() {
	
});

/** --- default-settings --- */

/*
 * focus to textField
 */
$("#inputField").focus();

/*
 * don't send form
 */
$("form").submit(function(event) {
	event.preventDefault();
});

/** --- Actions --- */

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
 * show active clients
 */
function showActiveClients(data) {
	$("#onlineClients").html(data);
}

/*
 * show typing clients
 */
function showTypingClients(data) {
	$("#typingClients").html(data);
}

/*
 * clear typing clients
 */
function clearTypingClients() {
	$("#typingClients").empty();
}

/*
 * scroll to bottom
 */
function scrollToBottom(elementId) {
	var frame = document.getElementById(elementId);
	var magnet = 200;
	
	if (frame.scrollTop >= (frame.scrollHeight - frame.offsetHeight - magnet))
		frame.scrollTop = frame.scrollHeight - frame.offsetHeight;
}

/*
 * play Sound
 */
function playSound() {
	// TODO
}

/** --- Ajax --- */

/*
 * send message
 */
function sendMessage(message) {
	$.ajax({
		url: "long_polling/sendMessage",
		type: "POST",
		data: message,
		contentType : "application/json; charset=UTF-8",
		//cache: false,
		success: function(data) {
			//getMessages();
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
		url: "long_polling/getMessages",
		type: "POST",
		//cache: false,
		success: function(data) {
			if (data != 0) {
				printMessages(data);
				playSound();
				scrollToBottom("content");
			}
			
			getMessages();
		},
		error : function(xhr, status, errorThrown) {
			//alert("sending failed with status: " + status + ". " + errorThrown);
		}
	});
}

/*
 * get active clients
 */
function getActiveClients() {
	$.ajax({
		url: "long_polling/getActiveClients",
		type: "POST",
		//cache: false,
		success: function(data) {
			if (data != 0) {
				showActiveClients(data);
			}
			
			getActiveClients();
		},
		error : function(xhr, status, errorThrown) {
			//alert("sending failed with status: " + status + ". " + errorThrown);
		}
	});
}

/*
 * get typing clients
 */
function getTypingClients() {
	$.ajax({
		url: "long_polling/getTypingClients",
		type: "POST",
		//cache: false,
		success: function(data) {
			if (data != 0) {
				showTypingClients(data);
			} else {
				clearTypingClients();
			}
			
			getTypingClients();
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
		url: "long_polling/close",
		type: "POST",
		//cache: false,
		error : function(xhr, status, errorThrown) {
			//alert("sending failed with status: " + status + ". " + errorThrown);
		}
	});
}

/*
 * set typing client
 */
function setTyping(b) {
	$.ajax({
		url: "long_polling/setTyping",
		type: "POST",
		data: b,
		contentType : "application/json; charset=UTF-8",
		//cache: false,
		error : function(xhr, status, errorThrown) {
			//alert("sending failed with status: " + status + ". " + errorThrown);
		}
	});
}

/** --- listeners --- */

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
 * if to close window then delete client
 */
window.onbeforeunload = function(event) {
	close();
}

/*
 * if to write characters into input-field then set typing client
 */
$("#inputField").keypress(function() {
	setTyping("true");
	setTimeout('setTyping("false")', 3000);
});

/** --- Invoke Polling --- */

getMessages();
getActiveClients();
getTypingClients();