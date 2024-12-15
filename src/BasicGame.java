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
                GameManager.drawCharacterSelectScreen();


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
                    Variables.csCursorP1X += 150;
                    if (Variables.csCursorP1X > 775) {
                        Variables.csCursorP1X = 625;
                    }
                } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_A && keyboardEvent.isKeyPressed()) {
                    Variables.csCursorP1X -= 150;
                    if (Variables.csCursorP1X < 600) {
                        Variables.csCursorP1X = 775;
                    }
                } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_W && keyboardEvent.isKeyPressed()) {
                    Variables.csCursorP1Y -= 150;
                    if (Variables.csCursorP1Y < 340) {
                        Variables.csCursorP1Y = 490;
                    }
                } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_S && keyboardEvent.isKeyPressed()) {
                    Variables.csCursorP1Y += 150;
                    if (Variables.csCursorP1Y > 490) {
                        Variables.csCursorP1Y = 340;
                    }
                }


                // Player 2 box movement
                if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_RIGHT && keyboardEvent.isKeyPressed()) {
                    Variables.csCursorP2X += 150;
                    if (Variables.csCursorP2X > 775) {
                        Variables.csCursorP2X = 625;
                    }
                } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_LEFT && keyboardEvent.isKeyPressed()) {
                    Variables.csCursorP2X -= 150;
                    if (Variables.csCursorP2X < 600) {
                        Variables.csCursorP2X = 775
                        ;
                    }
                } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_UP && keyboardEvent.isKeyPressed()) {
                    Variables.csCursorP2Y -= 150;
                    if (Variables.csCursorP2Y < 340) {
                        Variables.csCursorP2Y = 490;
                    }
                } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_DOWN && keyboardEvent.isKeyPressed()) {
                    Variables.csCursorP2Y += 150;
                    if (Variables.csCursorP2Y > 490) {
                        Variables.csCursorP2Y = 340;
                    }
                }
        }
    }

    @Override
    public void mouseEvent(MouseEvent mouseEvent) {

    }
}






