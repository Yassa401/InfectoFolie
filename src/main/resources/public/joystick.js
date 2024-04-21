document.addEventListener("DOMContentLoaded", function () {
    const joystickContainer = document.getElementById("joystick-container");
    const joystick = nipplejs.create({
        zone: joystickContainer,
        mode: "static",
        position: { top: "75%", left: "50%" },
        color: "#1ABC9C",
        restOpacity: 0.5,
        fadeTime: 250,
        threshold: 0.04,
        size: 300
    });

    // Se connecte à la websocket du serveur une seule fois à l'ouverture de la page
    const socket = new WebSocket("ws://" + window.location.hostname + ":" + window.location.port +"/ws");

    // Envoi de requete lorsque le joueur bouge le joystick
    joystick.on("move", function (event, data) {
        const angle = (data.angle.degree) % 360;
        const distance = data.distance;


        // Format the data and send it to the server
        const message = { type: "joystick", angle, distance };
        console.log(message);
        sendWebSocketMessage(message);
    });

    function sendWebSocketMessage(message) {
        if (socket.readyState === WebSocket.OPEN) {
            socket.send(JSON.stringify(message));
            console.log("message envoyé");
        } else {
            console.error("WebSocket connection is closed.");
        }
    }
    
    // récupérer et afficher le numéro joueur
	socket.addEventListener("message", (event) => {
		const data = JSON.parse(event.data);

        if (data.playerNumber) {
            document.querySelector(".circle").textContent = data.playerNumber;
        }
        
	});

});

// Affiche l'adresse ip du client (utilisé comme clé dans les listes players et clients)
console.log(window.location.hostname);
