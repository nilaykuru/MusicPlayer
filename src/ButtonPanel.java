import javax.swing.*;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel {
    public JButton playButton;
    public JButton pauseButton;
    public JButton nextButton;
    public JButton prevButton;
    public JButton addSongButton;
    public JButton likeButton;

    public ButtonPanel() {
        playButton = new JButton("▶️ Çal");
        pauseButton = new JButton("⏸️ Duraklat");
        nextButton = new JButton("⏭️ Sonraki");
        prevButton = new JButton("⏮️ Önceki");
        addSongButton = new JButton("➕ Şarkı Ekle");
        likeButton = new JButton("❤️ Beğen");

        add(prevButton);
        add(playButton);
        add(pauseButton);
        add(nextButton);
        add(addSongButton);
        add(likeButton);
    }

    public void setPlayListener(ActionListener listener) {
        playButton.addActionListener(listener);
    }

    public void setPauseListener(ActionListener listener) {
        pauseButton.addActionListener(listener);
    }

    public void setNextListener(ActionListener listener) {
        nextButton.addActionListener(listener);
    }

    public void setPrevListener(ActionListener listener) {
        prevButton.addActionListener(listener);
    }

    public void setAddSongListener(ActionListener listener) {
        addSongButton.addActionListener(listener);
    }

    public void setLikeListener(ActionListener listener) {
        likeButton.addActionListener(listener);
    }
}
