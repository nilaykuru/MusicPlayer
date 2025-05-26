public class SongDetails implements Comparable
{

	private final String title;

	private final String musicFilename;
	private final String artFilename;

	private final String album;


	public SongDetails(String title, String filename)
	{
		this(title, filename, filename, "");
	}

	public SongDetails(String title, String musicFilename, String artFilename,
	  String album)
	{
		this.title = title;
		this.musicFilename = musicFilename;
		this.artFilename = artFilename;
		this.album = album;
	}

	public String getTitle()
	{
		return this.title;
	}

	public String getFilename()
	{
		return this.getMusicFilename();
	}

	public String getMusicFilename()
	{
		return this.musicFilename;
	}
	public String getArtFilename()
	{
		return this.artFilename;
	}

	public String getAlbum()
	{
		return this.album;
	}


	public String toString()
	{
		return String.format("%s\n%s\n%s\n%s", this.title, this.musicFilename,
		this.artFilename, this.album);
	}


	public int compareTo(Object o)
	{
		/* SongDetails sort first before other Objects */
		if(!(o instanceof SongDetails))
			return 1;

		SongDetails songO = (SongDetails) o;

		/* Exit always sorts first */
		if(this.getFilename().equals("exit"))
			return -1;
		if(songO.getFilename().equals("exit"))
			return 1;

		/* If same album then compare titles */
		if(this.album.equals(songO.album))
			return this.title.compareTo(songO.title);
		return this.album.compareTo(songO.album);
	}
}
