package main.java;

import java.util.Map;

public class Game {
	private Map<String, Player> players ;
	
	public Game(Map<String, Player> p) {
		this.players = p;
	}
	
	public Map<String, Player> getPlayers() {
		return players;
	}

	public void setPlayers(Map<String, Player> players) {
		this.players = players;
	}
	
	public int getNbLivingPlayers() {
		// boucle pour calculer le nb de joueurs vivants
		return 0;
	}
	
	public int getNbDeadPlayers() {
		// boucle pour calculer le ng joueurs morts
		return 0;
	}
	
	// méthode qui met à jour le nb des jours au cours du jeu
	private void updatePlayers() {
		//...
	}
	
	// méthode qui effectue le jeu
	public void paly() {
		//...
	}

}
