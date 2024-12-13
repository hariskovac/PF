import nl.saxion.app.SaxionApp;

import java.util.ArrayList;

public class Map {
    public String name;
    public ArrayList<String> frames = new ArrayList<>();
    public int index = 0;

    public void createBackgroundArray(String name, int backgroundNumber, int frames) {
        this.name = name;

        for (int i = 0; i < frames; i++) {
            String path = "resources/bg" + backgroundNumber + "/frame" + i + ".gif";
            this.frames.add(path);
        }
    }

    public void animateMap() {

            SaxionApp.drawImage(this.frames.get(index), 0, 0, 1600, 672);
            SaxionApp.sleep(0.02);
            index++;
            if (index >= this.frames.size()) {
                index = 0;
            }

    }
}
