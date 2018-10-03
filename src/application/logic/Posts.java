package application.logic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import application.database.Content;
import javafx.scene.control.Label;

public class Posts {

	public static ArrayList<Post> getPosts() {
		ArrayList<Post> posts = new ArrayList<Post>();
		ResultSet r = Content.getPosts();
		try {
			while (r.next())
				posts.add(new Post(r.getString("header"), r.getString("text"), r.getString("poster")));
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		Collections.reverse(posts);
		return posts;
	}
	
	public static ArrayList<Label> getLabels() {
		ArrayList<Label> labels = new ArrayList<Label>();
		for (Post post : getPosts()) {
			String[] wordList = post.getBody().split(" ");
			String body = "";
			int i = 0;
			for (int j = 0; j < wordList.length; j++) {
				if (i > 100) {
					body += "\n";
					i = 0;
				}
				i += wordList[j].length() + 1;
				body += wordList[j] + " ";
			}
			labels.add(new Label("____________________________________________________________________________________________________\n____________________________________________________________________________________________________\n\n\n" + post.getHeader() + ", by " + post.getPoster() + "\n--------------------------------------------------\n" + body));
		}
		return labels;
	}
	
}
