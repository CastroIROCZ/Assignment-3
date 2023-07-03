
public interface UserInterface {
	public void openUser(String nodeName, User user);
	
	public void setID(String ID);
	
	public String getID();
	
	public boolean followUser(String ID);
	
	public void addUserAsFollower(String ID);
	
	public void postTweet(String tweet);
	
	public void updateFollowingList(String userFollowedID, User userFollowedObject);
	
	public void updateNewsFeed(String follower, String message);
	
	public boolean positivityCheck(String tweet);
	
	public void setCreationTime(long time);
	
	public String getCreationTime();
	
	public void lastUpdateTime();
	
	public void setLastUpdateTime(String time);
	
	public String getLastUpdateTime();
}
