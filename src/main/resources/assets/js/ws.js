$(document).ready(function() {  
	if(window.WebSocket) {
        var client, destination;
        var url="ws://54.215.165.222:61623";
        var login = "admin";
        var passcode = "password"

        var destination = "/topic/codeassasins.sentiment";
              
        client = Stomp.client(url, "stomp");
        client.connect(login, passcode, function() {
            client.debug("connected to Stomp");
            client.subscribe(destination, function(message) {
            	 window.location.reload()
            });
          });
	}
});  