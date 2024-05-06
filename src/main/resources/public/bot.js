document.addEventListener("DOMContentLoaded", function () {
    // VARIABLES GLOBALES
    var executionID ;
    var movementID ;
    var angle = 0 ;
    var distance  = 0 ;

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

    // Envoi de requetes après l'ouverture de la websocket
    socket.addEventListener("open", function(event){
        activerBot();
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
        clearInterval(executionID);

    });

    // envoyer une requete chaque intervalle de temps
    function envoyerRequete(){

        const message = { type: "joystick", angle, distance };
        sendWebSocketMessage(message);

    }

    // genere un mouvement aléatoire
    function genereMovement(){
        angle = Math.floor(Math.random()*360) + 1;
        distance = Math.floor(Math.random()*25) + 25 ;

    }

    // Active un bot qui envoie chaque intervalle de temps
    function activerBot(){
        console.log("fonction activé");
        // Envoi de requete chaque 20ms
        executionID = setInterval(envoyerRequete,20);
        movementID = setInterval(genereMovement,1000);

    }
});
