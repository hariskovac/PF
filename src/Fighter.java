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

    public ArrayList<String> attackR = new ArrayList<>();
    public ArrayList<String> attackL = new ArrayList<>();
    public int attackIndex = 0;

    public ArrayList<String> deadR = new ArrayList<>();
    public ArrayList<String> deadL = new ArrayList<>();
    public int deadIndex = 0;

    public ArrayList<String> defendR = new ArrayList<>();
    public ArrayList<String> defendL = new ArrayList<>();
    public int defendIndex;

    public ArrayList<String> hitR = new ArrayList<>();
    public ArrayList<String> hitL = new ArrayList<>();
    public int hitIndex = 0;

    public ArrayList<String> idleR = new ArrayList<>();
    public ArrayList<String> idleL = new ArrayList<>();
    public int idleIndex = 0;

    public ArrayList<String> runR = new ArrayList<>();
    public ArrayList<String> runL = new ArrayList<>();
    public int runIndex;

    public ArrayList<String> sattackR = new ArrayList<>();
    public ArrayList<String> sattackL = new ArrayList<>();
    public int sattackIndex = 0;

    public ArrayList<String> specialR = new ArrayList<>();
    public ArrayList<String> specialL = new ArrayList<>();
    public int specialIndex = 0;

    public ArrayList<String> ultimateR = new ArrayList<>();
    public ArrayList<String> ultimateL = new ArrayList<>();
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
                createAnimationArrayR(this.attackR, stats.getInt(8), this.imagePath, "attack");
                createAnimationArrayR(this.deadR, stats.getInt(9), this.imagePath, "dead");
                createAnimationArrayR(this.defendR, stats.getInt(10), this.imagePath, "defend");
                createAnimationArrayR(this.hitR, stats.getInt(11), this.imagePath, "hit");
                createAnimationArrayR(this.idleR, stats.getInt(12), this.imagePath, "idle");
                createAnimationArrayR(this.runR, stats.getInt(13), this.imagePath, "run");
                createAnimationArrayR(this.sattackR, stats.getInt(14), this.imagePath, "sattack");
                createAnimationArrayR(this.specialR, stats.getInt(15), this.imagePath, "special");
                createAnimationArrayR(this.ultimateR, stats.getInt(16), this.imagePath, "ultimate");

                createAnimationArrayL(this.attackL, stats.getInt(8), this.imagePath, "attack");
                createAnimationArrayL(this.deadL, stats.getInt(9), this.imagePath, "dead");
                createAnimationArrayL(this.defendL, stats.getInt(10), this.imagePath, "defend");
                createAnimationArrayL(this.hitL, stats.getInt(11), this.imagePath, "hit");
                createAnimationArrayL(this.idleL, stats.getInt(12), this.imagePath, "idle");
                createAnimationArrayL(this.runL, stats.getInt(13), this.imagePath, "run");
                createAnimationArrayL(this.sattackL, stats.getInt(14), this.imagePath, "sattack");
                createAnimationArrayL(this.specialL, stats.getInt(15), this.imagePath, "special");
                createAnimationArrayL(this.ultimateL, stats.getInt(16), this.imagePath, "ultimate");
            }
        }
    }

    public void createAnimationArrayR(ArrayList<String> animationArray, int frameCount, String path, String animationName) {
        for (int i = 0; i < frameCount; i++) {
            animationArray.add(path + animationName + i + "r.gif");
        }
    }

    public void createAnimationArrayL(ArrayList<String> animationArray, int frameCount, String path, String animationName) {
        for (int i = 0; i < frameCount; i++) {
            animationArray.add(path + animationName + i + "l.gif");
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
