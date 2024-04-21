document.addEventListener("DOMContentLoaded", function () {


    // Se connecte à la websocket du serveur une seule fois à l'ouverture de la page
    const socket = new WebSocket("ws://" + window.location.hostname + ":" + window.location.port +"/ws");

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

    // envoyer une requete chaque intervalle de temps
    function envoyerRequete(){
        var angle = Math.floor(Math.random()*360) + 1;
        var distance = Math.floor(Math.random()*50) + 1 ;

        const message = { type: "joystick", angle, distance };
        sendWebSocketMessage(message);

    }

    // Active un bot qui envoie chaque intervalle de temps
    function activerBot(){
        console.log("fonction activé");
        // Envoi de requete chaque 20ms
        setInterval(envoyerRequete,20);
    }

    // Envoi de requetes après l'ouverture de la websocket
    socket.addEventListener("open", function(event){
        activerBot();
    });

});
