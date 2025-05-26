import java.io.Serializable;

public class Song{

    private String title;
    private String artist;
    private int duration;
    private String filePath;

    public Song(String title, String artist, int duration, String filePath) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public String toString() {
        return title + " - " + artist + " (" + duration + " sn)";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Song s) {
            return filePath.equals(s.filePath);
        }
        return false;
    }
}
