package main.java;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameFrame extends ApplicationAdapter {
	private ShapeRenderer shapeRenderer;
    private Viewport viewport;
    private Map<String, Player> players ;
    public static List<Rectangle2D> murs;
    
    public static Rectangle2D murHaut = new Rectangle2D.Float(-IConfig.LARGEUR_FENETRE/2, IConfig.LONGUEUR_FENETRE/2-10, IConfig.LARGEUR_FENETRE, 10); // Mur supérieur
    public static Rectangle2D murGauche =new Rectangle2D.Float(-IConfig.LARGEUR_FENETRE/2,(float)(-IConfig.LONGUEUR_FENETRE/2.5), 10, IConfig.LONGUEUR_FENETRE); // Mur gauche
    public static Rectangle2D murBas = new Rectangle2D.Float(-IConfig.LARGEUR_FENETRE/2, (float)(-IConfig.LONGUEUR_FENETRE/2.5), IConfig.LARGEUR_FENETRE, 10); // Mur inférieur
    public static Rectangle2D murDroit = new Rectangle2D.Float(IConfig.LARGEUR_FENETRE/2-10, (float)(-IConfig.LONGUEUR_FENETRE/2.5), 10, IConfig.LONGUEUR_FENETRE); // Mur droitG

    BitmapFont font;
    Chrono chrono;
    SpriteBatch batch;  // pour dessiner les polices	
    GlyphLayout layout;	// pour obtenir la taille du texte afin de dessiner un cercle/rect autour
    
    private int nbLivingPlayers;
    private int nbDeadPlayers;
    
    private Game game;
    
    //GameFrame(Map<String, Player> players){
    	//this.players = players;
    GameFrame(Game g){
    	this.game = g;
    	this.players = g.getPlayers();
    	
    	murs = new ArrayList<>();
        // Initialisation des murs
    	murs.add(murHaut); // Mur supérieur
        murs.add(murGauche); // Mur gauche
        murs.add(murBas); // Mur inférieur
        murs.add(murDroit); // Mur droitG
		
        murs.add(new Rectangle2D.Float(-60,100,IConfig.LARGEUR_FENETRE/3,10));
        
        // récupéer le nb joueurs vivants/morts
        this.nbLivingPlayers = this.game.getNbLivingPlayers();
        this.nbDeadPlayers = this.game.getNbDeadPlayers();
        
    }
    @Override
    public void create () {
        shapeRenderer = new ShapeRenderer();
        viewport = new FitViewport(IConfig.LARGEUR_FENETRE, IConfig.LONGUEUR_FENETRE); // Crée un viewport de taille 800x600
        
        batch = new SpriteBatch();
        layout = new GlyphLayout();
        
	     // initialisation de la police
	    font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
	    
	    // initialiser le timer dans un thread, sinon bloque le thread principale
	    Thread timerThread = new Thread(() -> {
	    	chrono = new Chrono();
	        chrono.startTimer();
	    });
	    timerThread.start();
	    
    }
    
    
    @Override
    public void render () {
        Gdx.gl.glClearColor(245, 236, 236, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        
        // les mûrs
        drawWalls();
        
        // translater l'origine du "batch" vers le centre de la fenêtre pour que coords shapeRenderer = coords batch 
        Matrix4 translationMatrix = new Matrix4();
        translationMatrix.translate(IConfig.LARGEUR_FENETRE / 2, IConfig.LONGUEUR_FENETRE / 2, 0);
        batch.setTransformMatrix(translationMatrix);
        
        // dessiner les joueurs et le menu bas
        drawPlayers();
        drawBottomMenu();
        
        // faire une translation inverse pour obtenir la matrice d'origine du SpriteBatch
        batch.setTransformMatrix(new Matrix4().translate(-IConfig.LARGEUR_FENETRE / 2, -IConfig.LONGUEUR_FENETRE / 2, 0));
    }

    
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose () {
        shapeRenderer.dispose();
        
        font.dispose();
        batch.dispose();
    }
    
    private void drawCircle(Player player) {
    	shapeRenderer.setColor(player.getCouleur());
        shapeRenderer.circle((float)player.getX(), (float)player.getY(), player.getRadius());
    }
    
    private void drawWalls() {
    	shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Rectangle2D mur : murs) {
            shapeRenderer.setColor(Color.FIREBRICK); 
            shapeRenderer.rect((float)mur.getX(), (float)mur.getY(), (float)mur.getWidth(), (float)mur.getHeight());
        }
        shapeRenderer.end();
    }
    
    private void drawPlayers() {
    	// Déssiner les joueurs
    	shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Player p : players.values()) {
            shapeRenderer.setColor(p.getCouleur());
            shapeRenderer.circle((float) p.getX(), (float) p.getY(), p.getRadius());
        }
    	shapeRenderer.end();
    	
    	// Dessiner les numéros des joueurs
    	batch.begin();
    	for (Player p : players.values()) {
    		layout.setText(font, p.getNumPlayer()+""); 
            font.draw(batch, p.getNumPlayer()+"", (float) p.getX() - layout.width / 2, (float) p.getY() + layout.height / 2);
        }
        batch.end();
    }
    
    void actualiseJoueurs(Map<String, Player> players) {
    	this.players = players ;
    }
    
    private void drawBottomMenu() {
    	String texteLP = this.nbLivingPlayers + "00"; // à gérer quand on aura le bon nb joueurs
        String texteDP = this.nbDeadPlayers + "00";
        String texteTimer = this.chrono.getTimer();
        
        // coordonnées des figures
        int[] coordsTimer = {-50, -IConfig.LONGUEUR_FENETRE/2 + 50};
        int[] coordsLP = {-IConfig.LARGEUR_FENETRE/2 + 200, -IConfig.LONGUEUR_FENETRE/2 + 50};
        int[] coordsDP = {-IConfig.LARGEUR_FENETRE/2 + 350, -IConfig.LONGUEUR_FENETRE/2 + 50};
        
        // dessiner le timer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK); 
        this.roundedRect((float) coordsTimer[0] - 15, coordsTimer[1] - 40, 198, 60, 10);
        shapeRenderer.end();        
        
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line); // pour obtenir des cercles creux
        
        // dessiner le cadre des LP
        shapeRenderer.setColor(Color.GREEN);  
        layout.setText(font, texteLP); 
        shapeRenderer.circle(coordsLP[0] + layout.width / 2, coordsLP[1] -layout.height / 2, layout.width / 2 + 5);
        
        // dessiner le cadre des DP
        shapeRenderer.setColor(Color.RED);  
        layout.setText(font, texteDP); 
        shapeRenderer.circle(coordsDP[0] + layout.width / 2, coordsDP[1] -layout.height / 2, layout.width / 2 + 5);
        shapeRenderer.end();
        
        // Dessiner les textes (timer, LP, DP)
        batch.begin();
        font.setColor(Color.WHITE);
        font.getData().setScale(2f, 2.2f);	// augmenter la taille de la police
        
        font.draw(batch, this.chrono.getTimer(), coordsTimer[0], coordsTimer[1]);
        //font.setColor(Color.BLACK);
        font.draw(batch, texteLP, coordsLP[0], coordsLP[1]);
        font.draw(batch, texteDP, coordsDP[0], coordsDP[1]);
        
        batch.end();
        
    }
    
    /**
    * Dessine un rectangle avec des coins arrondis (pas de méthode native pour ça) 
    */ 
    public void roundedRect(float x, float y, float width, float height, float radius){
        // rectangle central
        shapeRenderer.rect(x + radius, y + radius, width - 2*radius, height - 2*radius);
        
        // quatres rectangles lattéraux dans le sens horaire
        shapeRenderer.rect(x + radius, y, width - 2*radius, radius);
        shapeRenderer.rect(x + width - radius, y + radius, radius, height - 2*radius);
        shapeRenderer.rect(x + radius, y + height - radius, width - 2*radius, radius);
        shapeRenderer.rect(x, y + radius, radius, height - 2*radius);
        
        // quatres arcs dans le sens horaire
        shapeRenderer.arc(x + radius, y + radius, radius, 180f, 90f);
        shapeRenderer.arc(x + width - radius, y + radius, radius, 270f, 90f);
        shapeRenderer.arc(x + width - radius, y + height - radius, radius, 0f, 90f);
        shapeRenderer.arc(x + radius, y + height - radius, radius, 90f, 90f);
    }
}
