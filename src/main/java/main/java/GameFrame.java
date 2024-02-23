package main.java;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameFrame extends ApplicationAdapter {
	private ShapeRenderer shapeRenderer;
    private Viewport viewport;
    private Map<String, Player> players ;
    public static List<Rectangle2D> murs;
    
    public static Rectangle2D murHaut = new Rectangle2D.Float(-IConfig.LARGEUR_FENETRE/2, IConfig.LONGUEUR_FENETRE/2-10, IConfig.LARGEUR_FENETRE, 10); // Mur supérieur
    public static Rectangle2D murGauche =new Rectangle2D.Float(-IConfig.LARGEUR_FENETRE/2, -IConfig.LONGUEUR_FENETRE/2, 10, IConfig.LONGUEUR_FENETRE); // Mur gauche
    public static Rectangle2D murBas = new Rectangle2D.Float(-IConfig.LARGEUR_FENETRE/2, -IConfig.LONGUEUR_FENETRE/2, IConfig.LARGEUR_FENETRE, 10); // Mur inférieur
    public static Rectangle2D murDroit = new Rectangle2D.Float(IConfig.LARGEUR_FENETRE/2-10, -IConfig.LONGUEUR_FENETRE/2, 10, IConfig.LONGUEUR_FENETRE); // Mur droitG

    
    GameFrame(Map<String, Player> players){
    	this.players = players;
    	
    	murs = new ArrayList<>();
        // Initialisation des murs
    	/*murs.add(new Rectangle2D.Float(-IConfig.LARGEUR_FENETRE/2, IConfig.LONGUEUR_FENETRE/2-10, IConfig.LARGEUR_FENETRE, 10)); // Mur supérieur
        murs.add(new Rectangle2D.Float(-IConfig.LARGEUR_FENETRE/2, -IConfig.LONGUEUR_FENETRE/2, 10, IConfig.LONGUEUR_FENETRE)); // Mur gauche
        murs.add(new Rectangle2D.Float(-IConfig.LARGEUR_FENETRE/2, -IConfig.LONGUEUR_FENETRE/2, IConfig.LARGEUR_FENETRE, 10)); // Mur inférieur
        murs.add(new Rectangle2D.Float(IConfig.LARGEUR_FENETRE/2-10, -IConfig.LONGUEUR_FENETRE/2, 10, IConfig.LONGUEUR_FENETRE)); // Mur droitG
*/
        
        murs.add(new Rectangle2D.Double(0,0,50,10));
    }
    @Override
    public void create () {
        shapeRenderer = new ShapeRenderer();
        viewport = new FitViewport(IConfig.LARGEUR_FENETRE, IConfig.LONGUEUR_FENETRE); // Crée un viewport de taille 800x600
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (Player player : players.values()) {
            drawCircle(player);
        }
        for (Rectangle2D mur : murs) {
            shapeRenderer.setColor(Color.GRAY);
            shapeRenderer.rect((float)mur.getX(), (float)mur.getY(), (float)mur.getWidth(), (float)mur.getHeight());
        }
        
        shapeRenderer.rect((float)murHaut.getX(), (float)murHaut.getY(), (float)murHaut.getWidth(), (float)murHaut.getHeight());
        shapeRenderer.rect((float)murBas.getX(), (float)murBas.getY(), (float)murBas.getWidth(), (float)murBas.getHeight());
        shapeRenderer.rect((float)murDroit.getX(), (float)murDroit.getY(), (float)murDroit.getWidth(), (float)murDroit.getHeight());
        shapeRenderer.rect((float)murGauche.getX(), (float)murGauche.getY(), (float)murGauche.getWidth(), (float)murGauche.getHeight());
        
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose () {
        shapeRenderer.dispose();
    }
    
    private void drawCircle(Player player) {
    	shapeRenderer.setColor(player.getCouleur());
        shapeRenderer.circle((float)player.getX(), (float)player.getY(), player.getRadius());
    }
    
    void actualiseJoueurs(Map<String, Player> players) {
    	this.players = players ;
    }
}
