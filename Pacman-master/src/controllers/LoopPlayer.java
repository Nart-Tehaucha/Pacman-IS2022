package controllers;
//hi delete it plss dddd
//ok ok
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

// This class loops sounds or stops sounds from looping.
public class LoopPlayer {

    Clip clip;
    AudioInputStream inputStream;

    // Loads the sound that will be played
    public LoopPlayer(String soundname){
        try {
            clip = AudioSystem.getClip();
            inputStream = AudioSystem.getAudioInputStream(
                    Main.class.getResourceAsStream("../resources/sounds/" + soundname));
            clip.open(inputStream);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    // Loops the sound
    public void start(){
        try {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    // Stops looping the sound
    public void stop(){
        try {
            clip.stop();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
