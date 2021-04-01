package movies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class MovieManager {
	private ArrayList<User> users = new ArrayList<>();
	private ArrayList<Movie> movies = new ArrayList<>();
	private ArrayList<Reviews> reviews = new ArrayList<>();
	private HashMap<String,ArrayList<Reviews>> ReviewMap = new HashMap<>();
	private HashMap<String,User> UserMap = new HashMap<>();
	//add a new user
	public void addUser(String username) {
		User u = new User(username);
		UserMap.put(username,u);
		users.add(u);
	}
	//check for unique user
	public boolean checkUniqueUser(String username) {
		return UserMap.containsKey(username);
	}
	//add a new movie
	public void addMovie(String moviename,int yearOfRelease, List<String> genre) {
		movies.add(new Movie(moviename,yearOfRelease,genre));
	}
	//add a new review
	public void addReviews(String username, String moviename, int rating) throws Exception {
		boolean youCanAddReview = false;
		for(User user : users) {
			if(user.username.equals(username)) {
				if(user.status.equals("critic")) {
					rating *= 2;
					youCanAddReview = true;
				}else {
					youCanAddReview = true;
				}
			}
		}
		if(youCanAddReview == true) {
			Reviews obj = new Reviews(username,moviename,rating);
			ArrayList<Reviews> list = ReviewMap.getOrDefault(moviename, new ArrayList<>());
			list.add(obj);
			ReviewMap.put(moviename,list);
			reviews.add(obj);
		}else {
			throw new Exception("Review cannot be added for this user");
		}
	}
	//find movie by name and year should be less than 2021
	public boolean findMovieByName(String mName) {
		for (Movie movie : movies) {
			if(movie.moviename.equals(mName)) {
				if(movie.yearOfRelease < 2021) {
					return true;
				}
			}
		}
		return false;
	}
	//find the review if already inserted before.
	public boolean findSameReview(String username, String moviename, int rating) {
		for (Reviews review : reviews) {
			if(review.moviename.equals(moviename) && review.username.equals(username)) {
				return true;
			}
		}
		return false;
	}
	//update the user based on its behaviour
	public void updateUser(String username) {
		for (User user : users) {
			if(user.username.equals(username)) {
				User u = UserMap.get(username);
				user.totalReviews += 1;
				UserMap.put(username, u);
			}
			if(user.totalReviews > 3) {
				User u = UserMap.get(username);
				user.status = "critic";
				UserMap.put(username, u);
			}
		}
	}
	//print all movies
	public void printMovies() {
		for (Movie movie : movies) {
			System.out.println(movie.moviename + " is released in Year " +  movie.yearOfRelease + " for Genres " +  movie.genre);
		}
	}
	//print all users
	public void printUsers() {
		for (User user : users) {
			System.out.println(user.username + " has rated " + user.totalReviews + " and has status of " + user.status);
		}
	}
	//return the reviews arraylist length
	public int reviewsLength() {
		return reviews.size();
	}
	//print all reviews
	public void printReviews() {
		for(Reviews review : reviews) {
			System.out.println(review.toString());
		}
	}
	//print the TOP N movies for a particular genre
	public LinkedHashMap<String,Integer> printTopNMovies(String genre) throws Exception {
		ArrayList<Movie> movi = new ArrayList<>();
		for (Movie movie : movies) {
			if(movie.genre.contains(genre)){
				movi.add(movie);
			}
		}
		List<Reviews> list  = new ArrayList<Reviews>();
		LinkedHashMap<String, Integer> movieWiseSum = new LinkedHashMap<>();
		for (Movie movie : movi) {
			ArrayList<Reviews> ll = ReviewMap.get(movie.moviename);
			for (Reviews review : ll) {
				User u = UserMap.get(review.username);
				if(u.status == "critic") {
					list.add(review);
				}
			}
		}
		if(list.size() == 0) {
			throw new Exception("No user has upgraded to Critic as of now");
		}
		Collections.sort(list);
		for(int i=0;i<list.size();i++) {
			Reviews obj = list.get(i);
			movieWiseSum.put(obj.moviename, movieWiseSum.getOrDefault(obj.moviename, 0)+obj.rating);
		}
		return movieWiseSum;
	}
	//find the average of review score particularly for a  year
	public double averageReviewScoreYear(int year) {
		double ans = 0.0d;
		ArrayList<Movie> list = new ArrayList<>();
		for (Movie movie : movies) {
			if((int)movie.yearOfRelease == year) {
				list.add(movie);
			}
		}
		for(Movie mov : list) {
			ArrayList<Reviews> rev = ReviewMap.get(mov.moviename);
			for(Reviews reve : rev) {
				ans += reve.rating;
			}
		}
		ans = ans/list.size();
		return ans;
	}
	//find the average of review score particularly for a movies
	public double averageReviewScoreMovie(String moviename) {
		double ans = 0.0d;
		int count = 0;
		ArrayList<Reviews> rev = ReviewMap.get(moviename);
		for(Reviews reve : rev) {
			ans += reve.rating;
			count++;
		}
		return ans/count;
	}
}
