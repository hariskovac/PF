import nl.saxion.app.CsvReader;
import nl.saxion.app.SaxionApp;

import java.util.ArrayList;

public class Fighter {
    public int hp;
    public int sp;
    public int atk;
    public int satk;
    public int def;
    public int sdef;
    public int spd;

    public String name;
    public String pic;
    public Map map;
    public String imagePath;

    public ArrayList<String> attack = new ArrayList<>();
    public int attackIndex = 0;
    public ArrayList<String> dead = new ArrayList<>();
    public int deadIndex = 0;
    public ArrayList<String> defend = new ArrayList<>();
    public int defendIndex;
    public ArrayList<String> hit = new ArrayList<>();
    public int hitIndex = 0;
    public ArrayList<String> idle = new ArrayList<>();
    public int idleIndex = 0;
    public ArrayList<String> run = new ArrayList<>();
    public int runIndex;
    public ArrayList<String> sattack = new ArrayList<>();
    public int sattackIndex = 0;
    public ArrayList<String> special = new ArrayList<>();
    public int specialIndex = 0;
    public ArrayList<String> ultimate = new ArrayList<>();
    public int ultimateIndex = 0;

    public void initFighter() {
        // Sets path of images for the fighter
        this.imagePath = "resources/" + this.name.toLowerCase() + "/";
        this.pic = this.imagePath + "/profile.png";

        // Sets all fighter attributes
        setFighterAttributes();
        createAllAnimations();
    }

    public void createAllAnimations() {
        CsvReader stats = new CsvReader("resources/stats.csv");
        stats.setSeparator(',');
        stats.loadRow();

        while (stats.loadRow()) {
            if (this.name.equalsIgnoreCase(stats.getString(0))) {
                createAnimationArray(this.attack, stats.getInt(8), this.imagePath, "attack");
                createAnimationArray(this.dead, stats.getInt(9), this.imagePath, "dead");
                createAnimationArray(this.defend, stats.getInt(10), this.imagePath, "defend");
                createAnimationArray(this.hit, stats.getInt(11), this.imagePath, "hit");
                createAnimationArray(this.idle, stats.getInt(12), this.imagePath, "idle");
                createAnimationArray(this.run, stats.getInt(13), this.imagePath, "run");
                createAnimationArray(this.sattack, stats.getInt(14), this.imagePath, "sattack");
                createAnimationArray(this.special, stats.getInt(15), this.imagePath, "special");
                createAnimationArray(this.ultimate, stats.getInt(16), this.imagePath, "ultimate");
            }
        }
    }

    public void createAnimationArray(ArrayList<String> animationArray, int frameCount, String path, String animationName) {
        for (int i = 0; i < frameCount; i++) {
            animationArray.add(path + animationName + i + ".gif");
        }
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void setFighterAttributes() {
        CsvReader stats = new CsvReader("resources/stats.csv");
        stats.setSeparator(',');
        stats.loadRow();

        while (stats.loadRow()) {
            if (this.name.equalsIgnoreCase(stats.getString(0))) {
                this.hp = stats.getInt(1);
                this.sp = stats.getInt(2);
                this.atk = stats.getInt(3);
                this.satk = stats.getInt(4);
                this.def = stats.getInt(5);
                this.sdef = stats.getInt(6);
                this.spd = stats.getInt(7);
            }
        }

    }

    public void playAnimation(ArrayList<String> animation, String soundEffect) {

        // Sound effects here
//        MediaPlayer player = new MediaPlayer(soundEffect,false);
//        player.play();

        for (String frame : animation) {
            SaxionApp.drawImage(frame, 0, 240);
            SaxionApp.sleep(0.001);
        }

    }
}
