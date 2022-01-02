package controllers;
import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

// This class handles all the sounds in the game, loading them and playing them.
public class SoundPlayer {
	
	static Clip clip; // audio clip
    static AudioInputStream inputStream; // stream of the audio clip
    
    public static synchronized void playAsync(final String name) {
        new Thread(new Runnable() {
            // The wrapper thread is unnecessary, unless it blocks on the
            // Clip finishing; see comments.
            public void run() {
                try { 
                	// fetch the audio clip used in the game
                	//read audio data from source
                	InputStream audioSrc = getClass().getResourceAsStream("/resources/sounds/" + name);
                	//add buffer for mark/reset support
                	InputStream bufferedIn = new BufferedInputStream(audioSrc);
                	
                    clip = AudioSystem.getClip();
                    inputStream = AudioSystem.getAudioInputStream(bufferedIn);
                    clip.open(inputStream);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }

    public static void play(final String name) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                    Main.class.getResourceAsStream("/resources/sounds/" + name));
            clip.open(inputStream);
        	// Get the gain control from clip
        	FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        	// set the gain (between 0.0 and 1.0
        	double gain = 0.1;    
        	float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
        	gainControl.setValue(dB);
            clip.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void startSiren(){
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                    Main.class.getResourceAsStream("/resources/sounds/siren.wav"));
            clip.open(inputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }


}