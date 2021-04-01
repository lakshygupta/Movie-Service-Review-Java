package movies;

public class User {
	String username;
	String status;
	int totalReviews;
	public User(String name) {
		this.username = name;
		this.status = "viewer";
		this.totalReviews = 0;
	}
}
