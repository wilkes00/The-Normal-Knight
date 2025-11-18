package Main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[10];

    public Sound(){
        soundURL[0] = getClass().getResource("/sounds/inicio.wav");
        soundURL[1] = getClass().getResource("/sounds/base.wav");
    }
    
    public void setFile(int index){
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(soundURL[index]);
            clip = AudioSystem.getClip();
            clip.open(audio);
        } catch (Exception e) {
            
        }
        
    }
    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }
}
