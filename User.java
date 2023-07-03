import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User implements UserInterface {
	private String userID;
	//private User user;
	private UserControlPanel controlPanel;
	//private Map<String, User> existingUsers;
    private List<String> tweets;
    private List<String> followers;
    private Map<String, User> usersFollowed;
    private String timeCreated;
    private String lastUpdateTime;
    
    //Constructor of user to initialize all the variables before the first method is called 
    public User() {
    	controlPanel = new UserControlPanel();
    	//existingUsers = Admin.getAdmin().getUsers();
    	tweets = new ArrayList<>();
    	usersFollowed = new HashMap<>();
    	followers = new ArrayList<>();
    }
    
    //this method opens the user control panel after recieving the username and the user object 
    public void openUser(String nodeName, User user) {
    	controlPanel.initializeUI(nodeName, user, getCreationTime());
    	this.userID = nodeName;
    }
        
    //save the username to this user object
	public void setID(String ID) {
		this.userID = ID;
	}
	
	//retrieve the username 
	public String getID() {
		return this.userID;
	}
	
	//follow a user returns a boolean to ensure that the user exists 
	public boolean followUser(String ID) {
		boolean isUser = false;
		if ((Admin.getAdmin().getUsers().containsKey(ID) == true) && (!(ID.equals(getID())))) { //checks with admin to see if user exists 
			isUser = true;
			Admin.getAdmin().getUsers().get(ID).addUserAsFollower(getID()); //enters the user that is being followed user object to add themselves as their follower 
			updateFollowingList(ID, Admin.getAdmin().getUsers().get(ID)); //updates the users following list (not needed in this program but in case it will in the future)
		}
		
		return isUser;
	}
	
	//adds user as a follower
	public void addUserAsFollower(String ID) {
		followers.add(ID);
	}
	
	//retrieve followers 
	public List<String> getFollowers() {
		return followers;
	}

	//posts a tweet 
	public void postTweet(String tweet) {
		lastUpdateTime();
		String time = getLastUpdateTime(); //gets the user creation time 
		Admin.getAdmin().setLastUserToUpdate(getID()); //updates the admin with the user who last updated the tree (tweeted)
		tweets.add(tweet);
		controlPanel.updatePostsPanel(getID(), tweet); //updates the users post panel so that they can see their own tweet 
		Admin.getAdmin().addToTotalMessages();//increments total messages 
		if (positivityCheck(tweet) == true) { //checks if the message is positive 
			Admin.getAdmin().addToPositivity(); //increments total positive messages 
		}
		else {
			Admin.getAdmin().calculatePositvityPercentage(); // if not positive simply recalculate the positivity percentatge 
		}
		for (int i = 0; i < followers.size() - 1; i++) { //iterates through the users followers so their newsfeed is updated 
			String element = followers.get(i);
			Admin.getAdmin().getUsers().get(element).controlPanel.updatePostsPanel(getID(), tweet);
			Admin.getAdmin().getUsers().get(element).setLastUpdateTime(time); //sets the last time the tree was updated 
		}
	}
	
	//update following list 
	public void updateFollowingList(String userFollowedID, User userFollowedObject) {
		usersFollowed.put(userFollowedID, userFollowedObject);
	}
	
	//update news feed 
	public void updateNewsFeed(String follower, String message) {
		this.controlPanel.updatePostsPanel(follower, message);
	}
	
	//checks if tweet is positive
	public boolean positivityCheck(String tweet) {
		boolean isPositive = false;
		for (String word : Admin.getAdmin().getListPositiveWords()) { //retrieves the list of positive words 
			if (tweet.contains(word) == true) { //compares the string to the list of positive words 
				isPositive = true;
			}
		}
		
		return isPositive;
	}
	
	//sets and formats the user creation time 
	public void setCreationTime(long time) {
		Instant instant = Instant.ofEpochMilli(time);
		LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
		String formattedTime = dateTime.format(formatter);
		
		this.timeCreated = formattedTime;
	}
	
	//retrieves user creation time 
	public String getCreationTime() {
		return this.timeCreated;
	}
	
	//formats the last time the tree was updated 
	public void lastUpdateTime() {
		Instant instant = Instant.ofEpochMilli(System.currentTimeMillis());
		LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
		String formattedTime = dateTime.format(formatter);
		
		this.lastUpdateTime = formattedTime;
		System.out.println("Last update at: " + this.lastUpdateTime);
	}
	
	//sets the last time the tree was updated 
	public void setLastUpdateTime(String time) {
		this.lastUpdateTime = time;
	}
	
	//retrieves the last time the tree was updated 
	public String getLastUpdateTime() {
		return this.lastUpdateTime;
	}
}

