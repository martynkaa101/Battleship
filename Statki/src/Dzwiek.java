package src;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import java.io.File;

public class Dzwiek {
    private String nazwa;
    public Dzwiek(String nazwa) {
        this.nazwa = nazwa;
    }

    public void zagraj()
    {
        try
        {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(nazwa).getAbsoluteFile( ));
            Clip clip = AudioSystem.getClip( );
            clip.open(audioInputStream);
            clip.start();
        }
        catch(Exception ex)
        {
            System.out.println("Error");
            ex.printStackTrace( );
        }
    }
}
