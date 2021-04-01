package movies;

public class Reviews implements Comparable<Reviews>{
	String username;
	String moviename;
	int rating;
	public Reviews(String username, String moviename, int rating) {
		this.username = username;
		this.moviename = moviename;
		this.rating = rating;
	}
	
	@Override
	public String toString() {
		return this.username+ " has rated "+ this.moviename + " with ratings : " + this.rating;
	}

	@Override
	public int compareTo(Reviews obj) {
		return obj.rating-this.rating;
	}
}
