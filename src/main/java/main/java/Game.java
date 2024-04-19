package main.java;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import com.badlogic.gdx.graphics.Color;

public class Game {
	private Map<String, Player> players ;
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
	
	public String getNbUninfectedPlayers() {
		int res = 0;
		for(Map.Entry<String, Player> entry : this.players.entrySet()) {
			if(entry.getValue().getStatut() == 0) {
				res++;
			}
		}
		
		// renvoie le résultat au format "000"
		return decimalFormat.format(res);
	}
	
	public String getNbInfectedPlayers() {		
		int res = 0;
		for(Map.Entry<String, Player> entry : this.players.entrySet()) {
			if(entry.getValue().getStatut() == 1) {
				res++;
			}
		}
		// renvoie le résultat au format "000"
		return decimalFormat.format(res);
	}
	
	public String getNbDeadPlayers() {	
		int res = 0;
		for(Player p : this.players.values()) {
			if(p.getStatut() == 2) {
				res++;
			}
		}
		// renvoie le résultat au format "000
		return decimalFormat.format(res);
	}
	
	// Renvoie le nb de joueurs à infecté pour une partie
	public int getNbPlayers2Infect() {
		int nbPlayers = this.getLivingPlayers().size();
		int nb2infec = 0;
		
		if(nbPlayers > 1) {
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

		else {
			// il y a qu'un seul
			GameServer.partieCommence = false;
		}
		return nb2infec;
	}
	
	// renvoie les aléatoirement les identifiants (numéro du joueur) des joueurs à infecter
	public ArrayList<Integer> getIdPlayers2infecte(){
		// Récup et mélanger la liste des players vivants
		ArrayList<Integer> idPlayers = new ArrayList<>();
		ArrayList<Player> listPlayers = this.getLivingPlayers();	// récup les living players
		Collections.shuffle(listPlayers);							// mélanger la liste de players
		
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
	
	public ArrayList<Player> getLivingPlayers(){
		ArrayList<Player> res = new ArrayList<>();
		for(Player p : this.players.values()) {
			if(p.getStatut() != 2) {
				res.add(p);
			}
		}
		return res;
	}
	
	// méthode qui fait passer le statut des joueurs de inf (1) à mort (2)
	public void updateStatus() {
		for(Player p : this.getLivingPlayers()) {
			if(p.getStatut() == 1) {
				p.setStatut(2);
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
			for(Player p : this.getLivingPlayers()) {
				if(p.getNumPlayer() == id) {
					this.infectPlayer(p);
					break; // pas besoin de continuer la boucle
				}
			}
		}
	}

	public static void healPlayer(Player p) {
		p.setCouleur(p.getColorInit());
		p.setStatut(0);
	}
	
	public boolean Victoire() {
	    int nbSurvivant = Integer.parseInt(getNbUninfectedPlayers());
	    int nbInfecte = Integer.parseInt(getNbInfectedPlayers());
	    
	    // Condition de victoire : un seul survivant et aucun infecté
	    return nbSurvivant == 1 && nbInfecte == 0;
	}

}
