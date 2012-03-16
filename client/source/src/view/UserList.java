package view;

import de.enough.polish.ui.List;

public class UserList extends List{

	public UserList(String title) {
		//#style userList
		super(title,List.IMPLICIT);
		// TODO Auto-generated constructor stub
	}
	public void addEntry(String item)
	{
		//#style userListItem
		this.append(item, null);
		
	}
}
