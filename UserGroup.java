import java.util.ArrayList;
import java.util.List;

public class UserGroup extends User implements UserInterface {
	private List <String> groupMembers; 
	private String groupID;
	private long timeCreated;
	
	//initializes the list 
	public UserGroup() {
		groupMembers = new ArrayList<String>();
	}
	
	//adds user to the group 
	public void addUserToGroup(String user) {
		groupMembers.add(user);
	}
	
	//retrieve list of users in group
	public List<String> getMembers() {
		return groupMembers;
	}
	
	//set the group name 
	public void setID(String groupID) {
		this.groupID = groupID;
	}
	
	public void setCreationTime() {
		this.timeCreated = 0;
	}
	
	public long getGroupCreationTime() {
		return this.timeCreated;
	}
	
}
