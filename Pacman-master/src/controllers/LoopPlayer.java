package controllers;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

// This class loops sounds or stops sounds from looping.
public class LoopPlayer {

    private Clip clip;
	private AudioInputStream inputStream;

    // Loads the sound that will be played
    public LoopPlayer(String soundname){
        try {
            clip = AudioSystem.getClip();
            inputStream = AudioSystem.getAudioInputStream(
                    Main.class.getResourceAsStream("/resources/sounds/" + soundname));
            clip.open(inputStream);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    // Loops the sound
    public void start(){
        try {
        	double gain;
        	
        	// Get the gain control from clip
        	FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        	// set the gain (between 0.0 and 1.0
        	if(SysData.getGameMode() == 0) {
        		gain = 0.05;  
        	}
        	else {
        		gain = 0.1; 
        	}
        	float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
        	gainControl.setValue(dB);
        	
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
    

    public Clip getClip() {
		return clip;
	}

	public void setClip(Clip clip) {
		this.clip = clip;
	}
}
