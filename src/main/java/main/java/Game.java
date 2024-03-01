package main.java;

import java.util.Iterator;
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
		int res = 0;
		for(Map.Entry<String, Player> entry : this.players.entrySet()) {
			if(entry.getValue().getStatut() != 2) {
				res++;
			}
		}
		
		return res;
	}
	
	public int getNbDeadPlayers() {
		int res = 0;
		for(Map.Entry<String, Player> entry : this.players.entrySet()) {
			if(entry.getValue().getStatut() == 2) {
				res++;
			}
		}
		return res;
	}
	
	public int getNbPlayers2Infect() {
		int nbPlayers = this.players.size();
		int nb2infec = 0;
		
		if(nbPlayers <= 5) {
			nb2infec = 1;
		}
		else if(nbPlayers <= 20) {
			nb2infec = 3;
		}
		else if(nbPlayers <= 40) {
			nb2infec = 5;
		}
		else if(nbPlayers <= 70) {
			nb2infec = 7;
		}
		else if(nbPlayers <= 100) {
			nb2infec = 10;
		}

		return nb2infec;
	}
	
	// méthode qui met à jour le nb des jours au cours du jeu
	private void updateNbPlayers() {
		// utiliser iterator, car on ne peut supp un element dans liste qu'on parcours
		Iterator<Map.Entry<String, Player>> iterator = this.players.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<String, Player> entry = iterator.next();
			if(entry.getValue().getStatut() == 2) {
				iterator.remove();
			}
		}
	}
	
	// infecte un joueurs (changer son statut et sa couleur)
	public void infectPlayer(Player p) {
		
	}
	
	// utilise les joueurs pour une partit du jeu
	public void infectPlayers() {
		
	}
	
	// méthode qui effectue le jeu
	public void play() {
		//...
	}

}
