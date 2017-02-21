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
		url: "long_polling/getMessages",
		type: "POST",
		//cache: false,
		success: function(data) {
			if (data != 0) {
				printMessages(data);
				playSound();
				scrollToBottom("content");
				
				getMessages();
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
		url: "long_polling/showOnline",
		type: "POST",
		//cache: false,
		success: function(data) {
			if (data != 0) {
				$("#online").html(data);
				
				//setTimeout('showOnline()', 1000);
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
		url: "long_polling/showTyping",
		type: "POST",
		//cache: false,
		success: function(data) {
			if (data != 0) {
				$("#typing").html(data);
				
				setTimeout('showTyping()', 1000);//showTyping();
			} else {
				$("#typing").empty();
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
 * if close window then delete client
 */
window.onbeforeunload = function(event) {
	close();
}

/*
 * if to input-field to write characters then set typing client
 */
$("#inputField").keypress(function() {
	setTyping("true");
	setTimeout('setTyping("false")', 3000);
});

/** --- Invoke Polling --- */

getMessages();
showOnline();
showTyping();