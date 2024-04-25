document.addEventListener("DOMContentLoaded", function () {
    // VARIABLES GLOBALES
    // variable pour stocker les données de joystick et envoyé en websocket
    var message = null;
    var executionID ;

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
        message = { type: "joystick", angle, distance };
        console.log(message);
    });

    // Ajouter un gestionnaire d'événement pour l'événement "end" du joystick
    joystick.on("end", function (event, data) {
        // Lorsque le joystick est relâché, vous pouvez définir l'angle et la distance à 0
        const angle = 0;
        const distance = 0;
        // Format the data and send it to the server
        message = { type: "joystick", angle, distance };
        console.log(message);
    });

    function sendWebSocketMessage(message) {
        if (socket.readyState === WebSocket.OPEN) {
            if(message != null) {
                socket.send(JSON.stringify(message));
                //console.log("message envoyé : " + message);
            }
        } else {
            console.error("WebSocket connection is closed.");
        }
    }

    // Envoi de requetes après l'ouverture de la websocket
    socket.addEventListener("open", function(event){
        executionID = setInterval(function() { sendWebSocketMessage(message)}, 20);
    });

    // récupérer et afficher le numéro joueur
	socket.addEventListener("message", (event) => {
		const data = JSON.parse(event.data);

        if (data.playerNumber) {
            document.querySelector(".circle").textContent = data.playerNumber;
        }
	});

    // Arrete l'envoi de requetes lorsque la connexion websocket est fermé
    socket.addEventListener("close", ( event ) => {
        // Arret d'envoi de requetes
        console.log("session fermé");
        clearInterval(executionID);
    });

});

// Affiche l'adresse ip du client (utilisé comme clé dans les listes players et clients)
console.log(window.location.hostname);
