import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class MusicPlayerGUI extends JFrame {
    private static final String SONG_DATA_FILE = "songs.dat";

    private DefaultListModel<Song> songListModel = new DefaultListModel<>();
    private JList<Song> songJList = new JList<>(songListModel);
    private LinkedList<Song> songs = new LinkedList<>();
    private Queue<Song> playQueue = new LinkedList<>();
    private Stack<Song> previousSongs = new Stack<>();
    private JLabel currentSongLabel = new JLabel("Şu an çalan şarkı yok.");
    private MP3Player mp3Player = new MP3Player();
    private Song currentSong;

    public MusicPlayerGUI() {
        super();  // JFrame constructor çağrısı (isteğe bağlı)
        setTitle("Java Müzik Çalar");
        setSize(600, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Şarkı listesi merkezde
        add(new JScrollPane(songJList), BorderLayout.CENTER);
        // Çalan şarkı etiketi üstte
        add(currentSongLabel, BorderLayout.NORTH);

        // Butonlar paneli altta
        JPanel buttonPanel = new JPanel();

        JButton addSongButton = new JButton("➕ Şarkı Ekle");
        JButton playButton = new JButton("▶️ Çal");
        JButton stopButton = new JButton("⏹ Durdur");
        JButton previousButton = new JButton("⏮ Önceki");
        JButton nextButton = new JButton("⏭ Sonraki");
        JButton likeButton = new JButton("❤️ Beğen");

        buttonPanel.add(addSongButton);
        buttonPanel.add(playButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(previousButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(likeButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Butonlara eylemler ekle
        addSongButton.addActionListener(e -> addSong());
        playButton.addActionListener(e -> playSong());
        stopButton.addActionListener(e -> stopSong());
        nextButton.addActionListener(e -> playNextSong());
        previousButton.addActionListener(e -> playPreviousSong());
        likeButton.addActionListener(e -> likeCurrentSong());

        loadSongsFromFile();

        // Pencere kapanırken şarkıları kaydet
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                saveSongsToFile();
            }
        });

        setVisible(true);
    }

    private void addSong() {
        String title = JOptionPane.showInputDialog(this, "Şarkı Adı:");
        if (title == null || title.trim().isEmpty()) return;

        String artist = JOptionPane.showInputDialog(this, "Sanatçı:");
        if (artist == null || artist.trim().isEmpty()) return;

        String durationStr = JOptionPane.showInputDialog(this, "Süre (saniye):");
        int duration;
        try {
            duration = Integer.parseInt(durationStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Geçerli bir sayı girin!");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);
        if (option != JFileChooser.APPROVE_OPTION) return;

        File file = fileChooser.getSelectedFile();
        String filePath = file.getAbsolutePath();

        Song newSong = new Song(title, artist, duration, filePath);
        songs.add(newSong);
        songListModel.addElement(newSong);
        playQueue.offer(newSong);
    }

    private void playSong() {
        if (currentSong == null && !playQueue.isEmpty()) {
            currentSong = playQueue.peek();
        }
        if (currentSong != null) {
            currentSongLabel.setText("Şu an çalan: " + currentSong.toString());
            mp3Player.play(currentSong.getFilePath());
        } else {
            currentSongLabel.setText("Çalma listesi boş.");
        }
    }

    private void stopSong() {
        mp3Player.stop();
        currentSongLabel.setText("Çalma durduruldu.");
    }

    private void playNextSong() {
        if (currentSong != null) {
            previousSongs.push(currentSong);
        }
        mp3Player.stop();
        playQueue.poll();  // mevcut şarkıyı kuyruktan çıkar
        currentSong = playQueue.peek();
        playSong();
    }

    private void playPreviousSong() {
        if (!previousSongs.isEmpty()) {
            mp3Player.stop();
            if (currentSong != null) {
                playQueue.add(currentSong);
            }
            currentSong = previousSongs.pop();
            playSong();
        } else {
            currentSongLabel.setText("Önceki şarkı yok.");
        }
    }

    private void likeCurrentSong() {
        if (currentSong != null) {
            mp3Player.stop();

            // songs listesinden çıkar
            songs.remove(currentSong);
            // songs listesinin başına ekle
            songs.addFirst(currentSong);

            // playQueue'yu da güncelle
            LinkedList<Song> tempQueue = new LinkedList<>();
            tempQueue.add(currentSong);
            for (Song song : playQueue) {
                if (!song.equals(currentSong)) {
                    tempQueue.add(song);
                }
            }
            playQueue = tempQueue;

            // songListModel'u güncelle (JList'in modeli)
            songListModel.removeElement(currentSong);
            songListModel.add(0, currentSong);

            currentSongLabel.setText("❤️ Beğenildi: " + currentSong.toString());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadSongsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SONG_DATA_FILE))) {
            LinkedList<Song> loadedSongs = (LinkedList<Song>) ois.readObject();
            for (Song s : loadedSongs) {
                songs.add(s);
                songListModel.addElement(s);
                playQueue.offer(s);
            }
        } catch (Exception e) {
            System.out.println("Kayıtlı şarkı bulunamadı veya okunamadı.");
        }
    }

    private void saveSongsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SONG_DATA_FILE))) {
            oos.writeObject(songs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MusicPlayerGUI::new);
    }
}
