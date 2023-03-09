package org.csc133.a3;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.table.TableLayout;
import org.csc133.a3.cmnds.*;

import java.io.IOException;

/**
 * Manages the flow of control in the game
 * A controller that allows player to make actions
 */
public class Game extends Form implements Runnable{
    // parts of the Game
    private GameWorld gw;
    private GlassCockpit gc;
    private MapView mv;
    private Toolbar tb;
    private Container bottomContainer;

    // commands of the game
    private AccelerateCmnd accelerate;
    private BrakeCmnd brake;
    private LeftTurnCmnd turnLeft;
    private RightTurnCmnd turnRight;
    private AboutCmnd about;
    private ChangeStratCmnd csc;
    private HelpCmnd help;
    private ExitCmnd exit;
    private MuteSoundCmnd mute;

    // button images
    private Image upA;
    private Image downA;
    private Image leftA;
    private Image rightA;

    // sounds
    private BGSound bgSound;

    /**
     * Creates new Game
     */
    public Game() {
        this.setLayout(new BorderLayout());

        // creates parts for the game
        gw = new GameWorld();
        mv = new MapView(gw);
        gc = new GlassCockpit(gw);
        tb = new Toolbar();

        // sounds
        bgSound = new BGSound("HeliSound.wav"); // from http://vieille.merde.free.fr/skm/sounds/camion.wav
        bgSound.play();

        // creates commands for the game
        accelerate = new AccelerateCmnd(gw);
        brake = new BrakeCmnd(gw);
        turnLeft = new LeftTurnCmnd(gw);
        turnRight = new RightTurnCmnd(gw);
        about = new AboutCmnd(gw);
        csc = new ChangeStratCmnd(gw);
        help = new HelpCmnd(gw);
        exit = new ExitCmnd(gw);
        mute = new MuteSoundCmnd(gw, bgSound);

        // sets up commands with their correct buttons/keys/menu
        setUpToolbar();
        setUpButtons();
        setUpKeys();

        // adds components to their correct places
        this.addComponent(BorderLayout.NORTH, gc);
        this.addComponent(BorderLayout.CENTER, mv);
        this.addComponent(BorderLayout.SOUTH, bottomContainer);

        show();
        gw.init(mv,gc);
        run();
    }

    /**
     * Sets up the toolbar with correct commands
     */
    public void setUpToolbar(){
        setToolbar(tb);
        tb.addComponentToLeftSideMenu(new Label(" "));
        tb.addCommandToLeftSideMenu(about);
        tb.addCommandToLeftSideMenu(mute);
        tb.addCommandToLeftSideMenu(csc);
        tb.addCommandToLeftSideMenu(help);
        tb.addCommandToLeftSideMenu(exit);
    }

    /**
     * Sets up keybindings for the game
     */
    public void setUpKeys(){
        addKeyListener('a', accelerate);
        addKeyListener('b', brake);
        addKeyListener('l', turnLeft);
        addKeyListener('r', turnRight);
        addKeyListener('x', exit);
    }

    /**
     * Adds images for the buttons at the southern area of the screen and assigns commands
     */
    public void setUpButtons(){
        try {
            upA = Image.createImage("/up arrow.png");
            downA = Image.createImage("/down arrow.png");
            leftA = Image.createImage("/left arrow.png");
            rightA = Image.createImage("/right arrow.png");
        }catch(IOException e){e.printStackTrace();}

        bottomContainer = new Container();
        bottomContainer.setLayout(new TableLayout(1,4));
        bottomContainer.getAllStyles().setPaddingLeft(60);

        Button bDown = new Button(downA);
        Button bUp = new Button(upA);
        Button bLeft = new Button(leftA);
        Button bRight = new Button(rightA);

        bDown.addActionListener(brake);
        bUp.addActionListener(accelerate);
        bLeft.addActionListener(turnLeft);
        bRight.addActionListener(turnRight);

        bottomContainer.add(bLeft);
        bottomContainer.add(bDown);
        bottomContainer.add(bUp);
        bottomContainer.add(bRight);
    }

    /**
     * Executes methods every timer tick
     */
    @Override
    public void run() {
        gw.clockTick(1);
    }
}
