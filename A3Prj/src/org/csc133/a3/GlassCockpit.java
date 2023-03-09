package org.csc133.a3;

import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.table.TableLayout;

/**
 * Glass Cockpit as a component that holds seven segments displays
 * for the states of the GameWorld: game time, Fuel level, damage level,
 * lives left, last skyscraper reached, current heading
 */
public class GlassCockpit extends Container {
    private GameWorld gw;

    // Components of the cockpit
    private GameClockComponent gameClock;
    private FuelComponent fuelC;
    private DamageComponent damageC;
    private LivesComponent livesC;
    private LastComponent lastC;
    private HeadingComponent headingC;
    private TableLayout table;

    // labels of the cockpit
    private Label gameTime = new Label("TIME");
    private Label fuel = new Label("FUEL");
    private Label damage = new Label("DMG");
    private Label lives = new Label("LIVES");
    private Label last = new Label("LAST");
    private Label heading = new Label("HEAD");

    /**
     * Creates new Glass Cockpit object
     * @param g reference to the current GameWorld
     */
    public GlassCockpit(GameWorld g){
        gw = g;
        table = new TableLayout(2,6);
        gameClock = new GameClockComponent();
        fuelC = new FuelComponent(gw);
        damageC = new DamageComponent(gw);
        livesC = new LivesComponent(gw);
        lastC = new LastComponent(gw);
        headingC = new HeadingComponent(gw);

        // SETTING SIZES OF COMPONENT LABELS
        gameTime.setAutoSizeMode(true);
        gameTime.setMaxAutoSize((float)1.5);
        gameTime.setMinAutoSize((float)1.5);

        fuel.setAutoSizeMode(true);
        fuel.setMaxAutoSize((float)1.5);
        fuel.setMinAutoSize((float)1.5);

        damage.setAutoSizeMode(true);
        damage.setMaxAutoSize((float)1.5);
        damage.setMinAutoSize((float)1.5);

        lives.setAutoSizeMode(true);
        lives.setMaxAutoSize((float)1.5);
        lives.setMinAutoSize((float)1.5);

        last.setAutoSizeMode(true);
        last.setMaxAutoSize((float)1.5);
        last.setMinAutoSize((float)1.5);

        heading.setAutoSizeMode(true);
        heading.setMaxAutoSize((float)1.5);
        heading.setMinAutoSize((float)1.5);

        // adding components to container
        this.setLayout(table);
        this.add(gameTime).add(fuel).add(damage).add(lives).add(last).add(heading).add(gameClock)
                .add(fuelC).add(damageC).add(livesC).add(lastC).add(headingC);
    }

    public void pause(){
        gameClock.stopElapsedTime();
    }

    public void unPause(){
        gameClock.startElapsedTime();
    }

    /**
     * Update and repaint the GlassCockpit upon game state changes
     */
    public void update(){
        fuelC.setFuel();
        damageC.setDamage();
        livesC.setLives();
        lastC.setLastSkyScraperReached();
        headingC.setHeading();

        fuelC.repaint();
        damageC.repaint();
        livesC.repaint();
        lastC.repaint();
        headingC.repaint();
    }
}
