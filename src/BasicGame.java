import nl.saxion.app.SaxionApp;
import nl.saxion.app.interaction.GameLoop;
import nl.saxion.app.interaction.KeyboardEvent;
import nl.saxion.app.interaction.MouseEvent;

public class BasicGame implements GameLoop {
    GameManager GameManager = new GameManager();

    public enum gameState {
        INTRO, INTRO_OVER, CHARACTER_SELECT, BATTLE
    }

    gameState state = gameState.CHARACTER_SELECT;

    //Variables for intro text
    boolean isTitleShown = false;
    boolean isIntroComplete = false;
    long titleShownTime = 0;

    // Creates intro background and character
    String[] introBackground = new String[64];
    double[] bgIntroDelay = {0.12, 0.24, 0.16, 0.2, 0.2, 0.2, 0.2, 0.2, 0.08, 0.04, 0.04, 0.04, 0.2, 0.2, 0.24, 0.16, 0.2, 0.2, 0.2, 0.2, 0.24, 0.16, 0.08, 0.04, 0.04, 0.04, 0.24, 0.16, 0.16, 0.08, 0.04, 0.04, 0.04, 0.04, 0.2, 0.2, 0.16, 0.2, 0.2, 0.16, 0.2, 0.24, 0.04, 0.04, 0.04, 0.04, 0.16, 0.2, 0.2, 0.2, 0.2, 0.28, 0.16, 0.16, 0.08, 0.04, 0.04, 0.04, 0.04, 0.16, 0.24, 0.2, 0.2, 0.08};
    String[] introCharacter = new String[49];

    // Current animation array index variables
    int bgFrameIndex = 0;
    int charFrameIndex = 0;

    // Tracks time at which last frame played for the intro animation
    long lastBgFrameTime = 0;
    long lastCharFrameTime = 0;

    //Select box variables
    int xBoxPlayer1 = 775;
    int yBoxPlayer1 = 490;
    int xBoxPlayer2 = 775;
    int yBoxPlayer2 = 490;

    public static void main(String[] args) {
        SaxionApp.startGameLoop(new BasicGame(), 1600, 672, 40);

    }

    @Override
    public void init() {

        // Fill intro background animation array
        for (int i = 0; i < introBackground.length; i++) {
            introBackground[i] = Variables.PATH + "intro/frame" + i + ".gif";
        }

        // Fill intro character animation array
        for (int i = 0; i < introCharacter.length; i++) {
            introCharacter[i] = Variables.PATH + "intro/intro" + i + ".gif";
        }

        GameManager.initMaps();
        GameManager.initFighters();

    }


    @Override
    public void loop() {

        SaxionApp.clear();
        long currentTime = System.currentTimeMillis();

        switch (state) {
            case INTRO:
                animateIntroBackground(currentTime);
                showIntroAnimation(currentTime);

                break;
            case INTRO_OVER:
                // Looping for background
                animateIntroBackground(currentTime);
                showIntroAnimation(currentTime);
                displayTitle();

                break;
            case CHARACTER_SELECT:
                SaxionApp.drawImage(Variables.PATH_CS + "char_select.jpg", 0, 0, 1600, 672);
                SaxionApp.drawImage(Variables.PATH_CS + "frame_red.png", Variables.CS_P1X, Variables.CS_P1Y);
                SaxionApp.drawImage(Variables.PATH_CS + "frame_blue.png", Variables.CS_P2X, Variables.CS_P2Y);
                SaxionApp.drawImage(Variables.PATH_CS + "frame_fire.png", Variables.CS_P2X - 475, Variables.CS_P1Y + 140);
                SaxionApp.drawImage(Variables.PATH_CS + "frame_water.png", Variables.CS_P2X - 325, Variables.CS_P2Y + 140);
                SaxionApp.drawImage(Variables.PATH_CS + "frame_lightning.png", Variables.CS_P2X - 475, Variables.CS_P1Y + 290);
                SaxionApp.drawImage(Variables.PATH_CS + "frame_random.png", Variables.CS_P2X - 325, Variables.CS_P2Y + 290);
                SaxionApp.drawImage(Variables.PATH_CS + "frame_p1.png", xBoxPlayer1, yBoxPlayer1);
                SaxionApp.drawImage(Variables.PATH_CS + "frame_p2.png", xBoxPlayer2, yBoxPlayer2);
                SaxionApp.drawImage(Variables.PATH_CS + "cs_topbar.png", 0, 0, 1600, 128);
                SaxionApp.drawImage(Variables.PATH_CS + "label_charselect.png", 544, 20);
                SaxionApp.drawImage(Variables.PATH + "fire/idle0.gif", Variables.CS_P1X - 350, Variables.CS_P1Y - 186);
                SaxionApp.drawImage(Variables.PATH + "fire/idle0.gif", Variables.CS_P2X - 350, Variables.CS_P2Y - 186);
                SaxionApp.drawImage(Variables.PATH_CS + "label_p1.png", Variables.CS_P1X - 40, Variables.CS_P1Y - 120);
                SaxionApp.drawImage(Variables.PATH_CS + "label_p2.png", Variables.CS_P2X - 40, Variables.CS_P2Y - 120);
                SaxionApp.drawImage(Variables.PATH_CS + "cs_statbg.png", Variables.CS_P1X - 89, Variables.CS_P1Y + 280);
                SaxionApp.drawImage(Variables.PATH_CS + "cs_statbg.png", Variables.CS_P2X - 89, Variables.CS_P2Y + 280);
                SaxionApp.drawImage(Variables.PATH_CS + "cs_keysp1.png", 5, 610);
                SaxionApp.drawImage(Variables.PATH_CS + "cs_keysp2.png", 1370, 610);

                SaxionApp.drawImage(Variables.PATH_CS + "label_hp.png", Variables.CS_P1X - 70, Variables.CS_P1Y + 300);
                SaxionApp.drawImage(Variables.PATH_CS + "label_hp.png", Variables.CS_P2X - 70, Variables.CS_P2Y + 300);
                SaxionApp.drawImage(Variables.PATH_CS + "label_sp.png", Variables.CS_P1X + 110, Variables.CS_P1Y + 300);
                SaxionApp.drawImage(Variables.PATH_CS + "label_sp.png", Variables.CS_P2X + 110, Variables.CS_P2Y + 300);
                SaxionApp.drawImage(Variables.PATH_CS + "label_atk.png", Variables.CS_P1X - 70, Variables.CS_P1Y + 330);
                SaxionApp.drawImage(Variables.PATH_CS + "label_atk.png", Variables.CS_P2X - 70, Variables.CS_P2Y + 330);
                SaxionApp.drawImage(Variables.PATH_CS + "label_satk.png", Variables.CS_P1X + 110, Variables.CS_P1Y + 330);
                SaxionApp.drawImage(Variables.PATH_CS + "label_satk.png", Variables.CS_P2X + 110, Variables.CS_P2Y + 330);
                SaxionApp.drawImage(Variables.PATH_CS + "label_def.png", Variables.CS_P1X - 70, Variables.CS_P1Y + 360);
                SaxionApp.drawImage(Variables.PATH_CS + "label_def.png", Variables.CS_P2X - 70, Variables.CS_P2Y + 360);
                SaxionApp.drawImage(Variables.PATH_CS + "label_sdef.png", Variables.CS_P1X + 110, Variables.CS_P1Y + 360);
                SaxionApp.drawImage(Variables.PATH_CS + "label_sdef.png", Variables.CS_P2X + 110, Variables.CS_P2Y + 360);
                SaxionApp.drawImage(Variables.PATH_CS + "label_spd.png", Variables.CS_P1X - 70, Variables.CS_P1Y + 390);
                SaxionApp.drawImage(Variables.PATH_CS + "label_spd.png", Variables.CS_P2X - 70, Variables.CS_P2Y + 390);


                break;
            case BATTLE:

                break;
        }

        GameManager.jukebox(state);


    }

    public void animateIntroBackground(long currentTime) {
        // Looping for background
        if (currentTime - lastBgFrameTime >= bgIntroDelay[bgFrameIndex] * 1000) {
            bgFrameIndex = (bgFrameIndex + 1) % introBackground.length;
            lastBgFrameTime = currentTime;
        }

        SaxionApp.drawImage(introBackground[bgFrameIndex], 0, 0, 1600, 672);
    }

    public void showIntroAnimation(long currentTime) {

        // Stops character animation at final frame
        if (currentTime - lastCharFrameTime >= 50) {
            charFrameIndex = (charFrameIndex + 1);
            lastCharFrameTime = currentTime;

            if (charFrameIndex >= 48) {
                charFrameIndex = 48;
                state = gameState.INTRO_OVER;
            }
        }

        SaxionApp.drawImage(introCharacter[charFrameIndex], 320, -50);
    }

    public void displayTitle() {
        // Draw the title after the intro is complete
        SaxionApp.drawImage(Variables.PATH + "intro/title.png", 300, 50, 950, 192);
        if (!isTitleShown) {
            isTitleShown = true;
            titleShownTime = System.currentTimeMillis();
        }

        // Delay for the "press enter to start" text
        if (System.currentTimeMillis() - titleShownTime >= 1000) {
            SaxionApp.drawImage(Variables.PATH + "intro/press_enter.png", 370, 450, 800, 132);
            isIntroComplete = true;
        }
    }


    @Override
    public void keyboardEvent(KeyboardEvent keyboardEvent) {
        switch (state) {
            case INTRO_OVER:
                if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_ENTER && isIntroComplete) {
                    state = gameState.CHARACTER_SELECT;
                }
                break;
            case CHARACTER_SELECT:
                // Player 1 box movement
                if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_D && keyboardEvent.isKeyPressed()) {
                    xBoxPlayer1 += 150;
                    if (xBoxPlayer1 > 775) {
                        xBoxPlayer1 = 625;
                    }
                } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_A && keyboardEvent.isKeyPressed()) {
                    xBoxPlayer1 -= 150;
                    if (xBoxPlayer1 < 600) {
                        xBoxPlayer1 = 775;
                    }
                } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_W && keyboardEvent.isKeyPressed()) {
                    yBoxPlayer1 -= 150;
                    if (yBoxPlayer1 < 340) {
                        yBoxPlayer1 = 490;
                    }
                } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_S && keyboardEvent.isKeyPressed()) {
                    yBoxPlayer1 += 150;
                    if (yBoxPlayer1 > 490) {
                        yBoxPlayer1 = 340;
                    }
                }


                // Player 2 box movement
                if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_RIGHT && keyboardEvent.isKeyPressed()) {
                    xBoxPlayer2 += 150;
                    if (xBoxPlayer2 > 775) {
                        xBoxPlayer2 = 625;
                    }
                } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_LEFT && keyboardEvent.isKeyPressed()) {
                    xBoxPlayer2 -= 150;
                    if (xBoxPlayer2 < 600) {
                        xBoxPlayer2 = 775
                        ;
                    }
                } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_UP && keyboardEvent.isKeyPressed()) {
                    yBoxPlayer2 -= 150;
                    if (yBoxPlayer2 < 340) {
                        yBoxPlayer2 = 490;
                    }
                } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_DOWN && keyboardEvent.isKeyPressed()) {
                    yBoxPlayer2 += 150;
                    if (yBoxPlayer2 > 490) {
                        yBoxPlayer2 = 340;
                    }
                }
        }
    }

    @Override
    public void mouseEvent(MouseEvent mouseEvent) {

    }
}






