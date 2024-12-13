import nl.saxion.app.audio.MediaPlayer;

import java.util.ArrayList;


public class GameManager {
    // Creates backgrounds
    Map mapFire = new Map();
    Map mapLightning = new Map();
    Map mapWater = new Map();

    // Creates fighters
    Fighter fire = new Fighter();
    Fighter water = new Fighter();
    Fighter lightning = new Fighter();

    // Creates an array of fighters that players select from on the Character Select screen
    ArrayList<Fighter> fighterChoices = new ArrayList();
    Fighter[] fighters = new Fighter[3];

    // Creates the music player
    MediaPlayer musicPlayer = new MediaPlayer("intro.mp3", true);
    boolean musicPlaying = false;

    public GameManager() {
        //load fighter choices;


    }

    public void jukebox(BasicGame.gameState state) {
        if (!musicPlaying) {
            new Thread(() -> {
                switch (state) {
                    case INTRO:
                        playSong("resources/m1.wav", true);
                        break;
                    case CHARACTER_SELECT:
//                        playSong("menu.mp3", true);
                        break;
                }
            }).start();
            musicPlaying = true;
        }
    }

    public void playSong(String song, boolean loop) {
        musicPlayer.setFile(song);
        musicPlayer.setLoop(loop);
        musicPlayer.play();
    }

    public void chooseFighter(int player, Fighter fighter) {

    }

    public Fighter getFighter(String name) {
        for (Fighter fighter : fighters) {
            if (fighter.name.equalsIgnoreCase(name)){
                return fighter;
            }
        }
        return null;
    }

    public void startFight() {

    }

    public void setPlayer1Fighter() {

    }

    public void draw() {
        for (int i = 0; i < fighters.length; i++) {
//            fighters[i].draw();
        }
    }

    public void initFighters() {
        // Sets path of images for each fighter
        fire.imagePath = "resources/fire/";
        water.imagePath = "resources/water/";
        lightning.imagePath = "resources/lightning/";

        fire.name = "Fire";
        water.name = "Water";
        lightning.name = "Lightning";

        fire.setMap(mapFire);
        water.setMap(mapWater);
        lightning.setMap(mapLightning);

        fire.initFighter();
        water.initFighter();
        lightning.initFighter();

        fighters[0] = fire;
        fighters[1] = water;
        fighters[2] = lightning;
    }

    public void initMaps() {
        mapFire.createBackgroundArray("fire", 1, 8);
        mapLightning.createBackgroundArray("lightning", 2, 16);
        mapWater.createBackgroundArray("water", 3, 8);
    }
}
