package main.java;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

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
		int res = 0;
		for(Map.Entry<String, Player> entry : this.players.entrySet()) {
			if(entry.getValue().getStatut() == 2) {
				res++;
			}
		}
		
		// renvoie le résultat au format "000"
		return decimalFormat.format(res);
	}
	
	// Renvoie le nb de joueurs à infecté pour une partie
	public int getNbPlayers2Infect() {
		int nbPlayers = this.players.size();
		int nb2infec = 0;
		
		if(nbPlayers > 1) { // au relancement s'il n'y a qu'un seul joueur c'est le gagant
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
		ArrayList<Integer> res = new ArrayList<>();
		Random random;
		int nbPlayers = this.players.size();
		int numPlayer;
		int nbPlayer2infecte = this.getNbPlayers2Infect();
		
		while(res.size() < nbPlayer2infecte) {
			random = new Random();
	        do {
	            numPlayer = random.nextInt(nbPlayers) + 1;
	        } while (res.contains(numPlayer));
	        
	        // quand on sort de la boucle, ce qu'on a trouvé une place valide
	        res.add(numPlayer);
		}
		return res;
	}
	
	// méthode qui met à jour le nb des jours au cours du jeu (élimine les infectés)
	public void updateNbPlayers() {
		// utiliser iterator, car on ne peut supp un element dans liste qu'on parcours
		Iterator<Map.Entry<String, Player>> iterator = this.players.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<String, Player> entry = iterator.next();
			if(entry.getValue().getStatut() == 1) {
				entry.getValue().setStatut(2);
				iterator.remove();
			}
		}		
	}
	
	// infecte un joueurs (changer son statut et sa couleur)
	public static void infectPlayer(Player p) {
		p.setCouleur(Color.RED);
		p.setStatut(1);		
	}
	
	/*
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
	}*/

	public static void healPlayer(Player p) {
		p.setCouleur(p.getColorInit());
		p.setStatut(2);
	}


	
	// infecte les joueurs pour une partit du jeu
	public void infectPlayers() {
		int cpt = 0;
		
		// à faire aléatoirement après
		for(Player p : players.values()) {
			if(cpt < this.getNbPlayers2Infect()) {
				this.infectPlayer(p);
				cpt++;
			}
			else {
				break;
			}
		}
	}
	
	// méthode qui effectue le jeu
	public void play() {
		/*
		Chrono.running = true;
		this.infectPlayers();
		//...
		this.updateNbPlayers();
		*/
	}

}
