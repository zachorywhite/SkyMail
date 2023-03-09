package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Display;

import java.util.ArrayList;
import java.util.Random;

/**
 * Game World that manipulates objects of the Game and displays data and game states
 */
public class GameWorld {
    private int gameClockTime = 0;
    private int playerLives = 3;
    private GameObjectCollection gameObjects;
    private MapView mv;
    private GlassCockpit gc;

    // numbers of game objects
    private int numOfSkyScrapers = 7;
    private int numOfBirds = 4;
    private int numOfRefuelingBlimps = 4;

    // game sounds
    private Sound explosionSound = new Sound("ExplosionSound.wav"); // from http://d-gun.com/files/sounds/CANNON1.WAV
    private Sound refuelSound = new Sound("RefuelSound.wav"); // from http://david.guerrero.free.fr/Effects/ThrowPowerup.wav
    private Sound deathSound = new Sound("DeathSound.wav"); // from http://tastyspleen.net/~quake2/baseq2/sound/misc/power2.wav
    private Sound birdSound = new Sound("BirdSound.wav"); // from http://gaben.web.elte.hu/Pocket%20Tanks%20Deluxe/weapdata/go/moltenplastic_minihit.wav

    /**
     * Holds Helicopter object in the GameObjectCollection as the Player's Helicopter
     */
    private PlayerHelicopter playerHeli;

    /**
     * Holds a NPHelicopter from the GameObjectCollection
     */
    private NPHelicopter npHeli;

    /**
     * Creates a new Game World
     */
    public GameWorld(){
        gameObjects = new GameObjectCollection();
    }

    /**
     * Initialises Game World with SkyScrapers, Helicopter, Birds, and RefuelingBlimps
     * @param m MapView of the current GameWorld
     * @param g GlassCockpit of the current GameWorld
     */
    public void init(MapView m, GlassCockpit g){
        mv = m;
        gc = g;

        // adds SkyScrapers to object collection
        addSkyScrapers();

        // creates instance of Player Helicopter with initial location at first skyscraper
        gameObjects.add(PlayerHelicopter.getInstance(this, mv.getWidth(), mv.getHeight(),
                gameObjects.get(0).getLocationX(),gameObjects.get(0).getLocationY()));
        playerHeli = (PlayerHelicopter) gameObjects.get(numOfSkyScrapers);

        // adds Non-Player helicopters to collection and sets their strategies
        gameObjects.add(new NPHelicopter(this, mv.getWidth(), mv.getHeight(),gameObjects.get(0).getLocationX() + 180,
                gameObjects.get(0).getLocationY() + 180));
        npHeli = (NPHelicopter) gameObjects.get(numOfSkyScrapers+1);
        npHeli.setStrategy(new AdvanceStrategy(npHeli,getCollection()));

        gameObjects.add(new NPHelicopter(this, mv.getWidth(), mv.getHeight(), gameObjects.get(0).getLocationX() - 180,
                gameObjects.get(0).getLocationY() + 180));
        npHeli = (NPHelicopter) gameObjects.get(numOfSkyScrapers+2);
        npHeli.setStrategy(new AttackStrategy(npHeli, playerHeli));

        gameObjects.add(new NPHelicopter(this, mv.getWidth(), mv.getHeight(),gameObjects.get(0).getLocationX() - 180,
                gameObjects.get(0).getLocationY() - 180));
        npHeli = (NPHelicopter) gameObjects.get(numOfSkyScrapers+3);
        npHeli.setStrategy(new AdvanceStrategy(npHeli, getCollection()));

        // adds birds and blimps to the collection
        addBirds();
        addRefuelingBlimps();
    }

    public void addRefuelingBlimps(){
        for(int i = 0; i < numOfRefuelingBlimps; i++)
            gameObjects.add(new RefuelingBlimp(mv.getWidth(), mv.getHeight()));
    }

    public void addBirds(){
        for(int i = 0; i<numOfBirds; i++)
            gameObjects.add(new Bird(mv.getWidth(), mv.getHeight()));
    }

    public void addSkyScrapers(){
        for(int i = 0; i < numOfSkyScrapers; i++)
            gameObjects.add(new SkyScraper(mv.getWidth(), mv.getHeight(),i+1));
    }

    /**
     * Gets entire collection of game objects
     * @return collection of Game Objects
     */
    public GameObjectCollection getCollection(){
        return this.gameObjects;
    }

    /**
     * Gets the Player Helicopter
     * @return the player's helicopter
     */
    public PlayerHelicopter findPlayerHeli(){
        return playerHeli;
    }

    /**
     * Returns how many lives player has left
     * @return amount of lives left
     */
    public int getPlayerLives(){
        return playerLives;
    }

    /**
     * Accelerates player Helicopter if not already at max speed.
     */
    public void playerHeliAccelerate(){
        if(playerHeli.getSpeed() < playerHeli.getMaximumSpeed()) {
            playerHeli.accelerate();
            System.out.println("Helicopter accelerated.\n");
        }
        else
            System.out.println("Helicopter already at max speed.\n");
    }

    /**
     * Applies brakes to slow down Helicopter if still moving.
     */
    public void playerHeliBrake(){
        if(playerHeli.getSpeed() > 0) {
            playerHeli.decelerate();
            System.out.println("Brakes are applied.\n");
        }
        else
            System.out.println("Helicopter already at zero speed.\n");
    }

    /**
     * Requests change in the Stick Angle of the Helicopter to the left
     * Calls Helicopter function to process request
     */
    public void turnPlayerHeliLeft(){
        playerHeli.changeStickDirection(-5);
        System.out.println("Helicopter turning left.\n");
    }

    /**
     * Requests change in the Stick Angle of the Helicopter to the right
     * Calls Helicopter function to process request
     */
    public void turnPlayerHeliRight(){
        playerHeli.changeStickDirection(5);
        System.out.println("Helicopter turning right.\n");
    }

    /**
     * Player Helicopter takes damage upon collision with another Helicopter
     * After collision checks if Helicopter has taken max damage
     */
    public void playerHeliCollision(){
        playerHeli.takeDamage(20);
        explosionSound.play();
        System.out.println("Player collided with another helicopter.\n");

        // When player has taken max damage, reinitialize world and lose life
        if(playerHeli.getDamageLevel() == 100) {
            loseLife();
            resetHeli();
        }
        gc.update();
    }

    /**
     * NPHelicopter collides with another helicopter
     * @param npH which NPHelicopter is taking damage
     */
    public void npHHeliCollision(NPHelicopter npH){
        npH.takeDamage(20);
        explosionSound.play();
        System.out.println("NPHelicopter collided with another helicopter.\n");
    }

    /**
     * Collision between Player Helicopter and SkyScraper
     */
    public void playerSkyScraperCollision(){
        playerHeli.incLastSkyScraperReached();
        System.out.println("Player has reached the next SkyScraper");
        gc.update();
    }

    /**
     * Collision between NPHelicopter and SkyScraper
     * @param npH which NPHelicopter has reached checkpoint
     */
    public void npHSkyScraperCollision(NPHelicopter npH){
        npH.incLastSkyScraperReached();
        System.out.println("NPH has reached the next SkyScraper");
    }

    /**
     * Collision between RefuelingBlimp and Player Helicopter
     * Amount fueled based on Blimp size/capacity
     * @param blimp which refueling blimp has been hit
     */
    public void playerRefuelingBlimpCollision(RefuelingBlimp blimp){
        if(blimp.getCapacity() != 0) {
            playerHeli.gainFuel(blimp.getCapacity());

            refuelSound.play();
            gameObjects.add(new RefuelingBlimp(mv.getWidth(), mv.getHeight()));// add new random blimp on map

            System.out.println("Helicopter is refueled.\n");
            gc.update();
        }
    }

    /**
     * Collision between NPHelicopter and RefuelingBlimp
     * @param blimp correct blimp that has been hit
     * @param npH which NPHelicopter has hit blimp
     */
    public void npHRefuelingBlimpCollision(RefuelingBlimp blimp, NPHelicopter npH){
        if(blimp.getCapacity() != 0) {
            npH.gainFuel(blimp.getCapacity());

            refuelSound.play();
            gameObjects.add(new RefuelingBlimp(mv.getWidth(), mv.getHeight()));// add new random blimp on map

            System.out.println("NPHelicopter is refueled.\n");
        }
    }

    /**
     * Player Helicopter takes damage upon collision with a Bird
     * After collision checks if Helicopter has taken max damage
     */
    public void playerBirdCollision(Bird bird){
        playerHeli.takeDamage(bird.getDamageToHeli());
        birdSound.play();
        System.out.println("Player has collided with bird.\n");

        // When player has taken max damage, reinitialize world and lose life
        if(playerHeli.getDamageLevel() == 100) {
            loseLife();
            resetHeli();
        }
        gc.update();
    }

    /**
     * NPHelicopter takes damage when hits bird
     * @param bird which bird was hit
     * @param npH which NPHelicopter hit the bird
     */
    public void npHBirdCollision(Bird bird, NPHelicopter npH){
        npH.takeDamage(bird.getDamageToHeli());
        birdSound.play();
        System.out.println("NPHelicopter has collided with bird.\n");
    }

    /**
     * Tells the GameWorld the game clock has ticked,
     * Player Helicopter turns according to the Stick Angle,
     * Birds turn in random direction,
     * All movable objects call their respective move functions to simulate movement,
     * Collisions are checked for every game object,
     * Checks if player has died,
     * Player Helicopter loses appropriate amount of fuel.
     * @param eTime amount of time between clock ticks
     */
    public void clockTick(double eTime){
        // turn player according to stick angle
        if(playerHeli.fuelNotEmpty()) {
            playerHeli.turn();
        }

        // turn bird random direction
        for(int i =0; i<gameObjects.size(); i++)
        {
            if(gameObjects.get(i) instanceof Bird){
                ((Bird) gameObjects.get(i)).turnBird();
            }
        }

        // move every movable object in GameObject array
        for(int i =0; i<gameObjects.size(); i++){
            if(gameObjects.get(i) instanceof MovableGameObject){
                MovableGameObject moveObject = (MovableGameObject) gameObjects.get(i);
                moveObject.move(eTime);
            }
        }

        // collisions
        for(int i = 0; i < gameObjects.size(); i++){
            for(int j = 0; j < gameObjects.size(); j++)
            {
                if(gameObjects.get(i) != gameObjects.get(j)){
                    if(gameObjects.get(i).collidesWith(gameObjects.get(j)))
                        gameObjects.get(i).handleCollision(gameObjects.get(j));
                }
            }
        }

        // Lose fuel according to fuel consumption rate
        // and lose life if ran out of fuel
        playerHeli.loseFuel();
        if (playerHeli.getFuelLevel() == 0) {
            loseLife();
            resetHeli();
        }
        gameClockTime++;

        checkWin();

        gc.update();
        mv.update();
    }

    /**
     * Checks if either the player or the non-player helicopters have won the game
     */
    public void checkWin(){
        for(int i = 0; i < gameObjects.getNPHelicopter().size(); i++)
        {
            npHeli = (NPHelicopter) gameObjects.getNPHelicopter().get(i);
            if(npHeli.getLastSkyScraperReached() == numOfSkyScrapers)
            {
                System.out.println("Game over, a non-player helicopter wins!");
                Display.getInstance().exitApplication();
            }
        }

        if(playerHeli.getLastSkyScraperReached() == numOfSkyScrapers)
        {
            System.out.println("Good job, you won!");
            Display.getInstance().exitApplication();
        }
    }

    /**
     * Reduces Player Lives by one until Player has zero lives,
     * Exits program upon reaching zero lives
     */
    public void loseLife(){
        if(playerLives > 0) {
            deathSound.play();
            playerLives--;
            System.out.println("-1 life.\n");
        }
        else
        {
            System.out.println("Game over, better luck next time!");
            System.exit(0);
        }
    }

    /**
     * Displays current world: Each object in the Game World's details
     *      Ex: Location, color, speed, heading, size, max speed, stick angle, fuel level,
     *          damage level of Player Helicopter
     */
    public void map(){
        for(int i=0; i<gameObjects.size(); i++){
            System.out.println(gameObjects.get(i).toString());
        }
        System.out.println();
    }

    /**
     * Upon player death, resets helicopter to original position,
     * fuel level, speed, color, etc..
     */
    private void resetHeli(){
        playerHeli.setFuelLevel(1000);
        playerHeli.setLocationX(gameObjects.get(0).getLocationX());
        playerHeli.setLocationY(gameObjects.get(0).getLocationY());
        playerHeli.setDamageLevel(0);
        playerHeli.setHeading(0);
        playerHeli.setStickAngle(0);
        playerHeli.setSpeed(0);
        playerHeli.setColor(ColorUtil.rgb(0,0,255));
        playerHeli.updateMaxSpeed();
    }
}
