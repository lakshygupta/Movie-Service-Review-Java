package movies;

import java.util.List;

public class Movie {
	String moviename;
	int yearOfRelease;
	List<String> genre;
	public Movie(String moviename, int yearOfRelease, List<String> genre) {
		this.moviename = moviename;
		this.yearOfRelease = yearOfRelease;
		this.genre = genre;
	}
}
