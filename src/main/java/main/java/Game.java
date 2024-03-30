package main.java;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;

public class Game {
	private Map<String, Player> players ;
	private int nbDeadPlayers;
	private DecimalFormat decimalFormat = new DecimalFormat("000");
	public boolean canPlay;
	public boolean inPause;
	
	public Game(Map<String, Player> p) {
		this.players = p;
		this.canPlay = false;
		this.inPause = false;
	}
	
	public Map<String, Player> getPlayers() {
		return players;
	}

	public void setPlayers(Map<String, Player> players) {
		this.players = players;
	}
	
	public String getNbLivingPlayers() {
		int res = 0;
		for(Map.Entry<String, Player> entry : this.players.entrySet()) {
			if(entry.getValue().getStatut() != 2) {
				res++;
			}
		}
		
		// renvoie le résultat au format "000"
		return decimalFormat.format(res);
	}
	
	public String getNbDeadPlayers() {		
		// renvoie le résultat au format "000
		return decimalFormat.format(this.nbDeadPlayers);
	}
	
	// Renvoie le nb de joueurs à infecté pour une partie
	public int getNbPlayers2Infect() {
		int nbPlayers = this.players.size();
		int nb2infec = 0;
		
		if(nbPlayers > 1) { // ? l'infecté (game over after the round) ou pas
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
		}

		return nb2infec;
	}
	
	// renvoie les aléatoirement les identifiants (numéro du joueur) des joueurs à infecter
	public ArrayList<Integer> getIdPlayers2infecte(){
		// Récup et mélanger la liste des players
		ArrayList<Integer> idPlayers = new ArrayList<>();
		ArrayList<Player> listPlayers = new ArrayList<>(this.players.values());	// récup les players
		Collections.shuffle(listPlayers);										// mélanger la liste de players
		
		// Récup le nb de players à infecter
		int nbPlayer2infecte = this.getNbPlayers2Infect();
		
		// Remplir la liste des id
		int i = 0;
		while(idPlayers.size() < nbPlayer2infecte) {
			idPlayers.add(listPlayers.get(i).getNumPlayer());
			i++;
		}
		
		return idPlayers;
	}
	
	// méthode qui met à jour le nb des jours au cours du jeu (élimine les infectés)
	public void updateNbPlayers() {
		// utiliser iterator, car on ne peut supp un element dans liste qu'on parcours
		Iterator<Map.Entry<String, Player>> iterator = this.players.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<String, Player> entry = iterator.next();
			if(entry.getValue().getStatut() == 1) {
				entry.getValue().setStatut(2);
				this.nbDeadPlayers++;
				iterator.remove();
			}
		}		
	}
	
	// infecte un joueurs (changer son statut et sa couleur)
	public static void infectPlayer(Player p) {
		p.setCouleur(Color.RED);
		p.setStatut(1);		
	}
	
	// infecte les joueurs pour une partit du jeu
	public void infectPlayers() {
		int cpt = 0;
		for(int id : this.getIdPlayers2infecte()) {
			for(Player p : this.players.values()) {
				if(p.getNumPlayer() == id) {
					this.infectPlayer(p);
					break; // pas besoin de continuer la boucle
				}
			}
		}
	}

	public static void healPlayer(Player p) {
		p.setCouleur(p.getColorInit());
		p.setStatut(2);
	}
}
