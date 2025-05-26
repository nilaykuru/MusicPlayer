public abstract class MusicPlayer
{
	public abstract void playMusic(String filename);
	public abstract void stopMusic() throws NoSongPlayingException;
	public abstract boolean isPlaying();

	private class NoSongPlayingException extends Exception {
	}
}
