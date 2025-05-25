import javazoom.jl.player.Player;
import java.io.FileInputStream;

public class MP3Player {
    private Player player;
    private Thread thread;

    public void play(String filePath) {
        stop();  // Çalan varsa önce durdur
        thread = new Thread(() -> {
            try {
                FileInputStream fis = new FileInputStream(filePath);
                player = new Player(fis);
                player.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void stop() {
        if (player != null) {
            player.close();
        }
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
        }
    }
}
