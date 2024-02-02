document.addEventListener("DOMContentLoaded", function () {
    const joystickContainer = document.getElementById("joystick-container");
    const joystick = nipplejs.create({
        zone: joystickContainer,
        mode: "static",
        position: { top: "50%", left: "50%" },
        color: "blue"
    });
    
    // Se connecte à la websocket du serveur une seule fois à l'ouverture de la page
    const socket = new WebSocket("ws://" + window.location.hostname + ":" + window.location.port +"/ws");

    joystick.on("move", function (event, data) {
        const angle = data.angle.degree;
        const distance = data.distance;

        // Format the data and send it to the server
        const message = { type: "joystick", angle, distance };
        console.log(message);
        sendWebSocketMessage(message);
    });

    function sendWebSocketMessage(message) {
     if (socket.readyState === WebSocket.OPEN) {
            socket.send(JSON.stringify(message));
        } else {
            console.error("WebSocket connection is closed.");
        }
	}
});

console.log(window.location.hostname);
