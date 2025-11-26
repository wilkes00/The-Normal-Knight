package Main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
/**
 * Clase para manejar efectos de sonido y música de fondo.
 */
public class Sound {
    Clip clip;
    URL soundURL[] = new URL[10];
    
    public Sound(){
        soundURL[0] = getClass().getResource("/resources/sounds/inicio.wav");
        soundURL[1] = getClass().getResource("/resources/sounds/base.wav");
        soundURL[2] = getClass().getResource("/resources/sounds/open_chest1.wav");
    }
    
    /**
     * Establece el archivo de sonido a reproducir.
     * @param index Índice del archivo de sonido en el arreglo soundURL.
     */
    public void setFile(int index){
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(soundURL[index]);
            clip = AudioSystem.getClip();
            clip.open(audio);
        } catch (Exception e) {
            
        }
        
    }
    /**
     * Reproduce el sonido configurado.
     */
    public void play(){
        clip.start();
    }
    public void playSoundEfect(int index){
        setFile(index);
        play();
    }
    /**
     * Reproduce el sonido en un bucle continuo.
     */
    public void loop(){
        clip.loop(clip.LOOP_CONTINUOUSLY);
    }
    /**
     * Detiene la reproducción del sonido.
     */
    public void stop(){
        clip.stop();
    }
}
