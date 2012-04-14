package view;

import model.Group;
//import model.Request;
//import model.Topic;
import model.GroupTopic;
//import control.MessageBox;
import control.UserItem;
//import de.enough.polish.ui.Display;
//import de.enough.polish.ui.Displayable;
import de.enough.polish.ui.Command;
import de.enough.polish.ui.Item;
import de.enough.polish.ui.List;
import de.enough.polish.util.ArrayList;

public class UserList extends List{
	private ArrayList items;
	public int pageId;
	public int id;
	public Object data;
	public UserList(String title) 
	{
		//#style userList
		super(title,List.IMPLICIT);
		// TODO Auto-generated constructor stub
		items=new ArrayList();
		pageId = 0;
		id = 0;
	}
	public UserList(String title, Object data) 
	{
		//#style userList
		super(title,List.IMPLICIT);
		// TODO Auto-generated constructor stub
		items=new ArrayList();
		pageId = 0;
		id = 0;
		this.data = data;
	}
	public UserList(String title, int id) 
	{
		//#style userList
		super(title,List.IMPLICIT);
		// TODO Auto-generated constructor stub
		items=new ArrayList();
		pageId = 0;
		this.id = id;
	}
	public UserList(String title, Object data, int id) 
	{
		//#style userList
		super(title,List.IMPLICIT);
		// TODO Auto-generated constructor stub
		items=new ArrayList();
		pageId = 0;
		this.id = id;
		this.data = data;
	}
	public void addEntry(String item)
	{
		//#style userListItem
		this.append(item, null);
		
	}
	public void addMenu(Command cmd)
	{
		addCommand(cmd);
	}
	public void removeAllEntry()
	{
		this.deleteAll();
		items.clear();
	}
	public void addEntry(UserItem item,String type)
	{
		items.add(item);
		if(type.equals("group"))
		{
			Group g=(Group) item.data;
			//#style groupItem
			this.append(g.groupName,null);
		}
		else if(type.equals("topic"))
		{
			//GroupTopic t=(GroupTopic)item.data;
			//#style topicItem
			this.append(item.getText(),null);
		}
		else if(type.equals("request"))
		{
			//#style requestItem
			this.append(item.getText(), null);
		}
		else if(type.equals("user"))
		{
			//#style userItem
			this.append(item.getText(), null);
		}
		else if(type.equals("leader"))
		{
			//#style leaderItem
			this.append(item.getText(), null);
		}
	}
	public Item getCurrentItem()
	{
		UserItem i=(UserItem)items.get(this.getCurrentIndex());
		return i; 
	}
	/*public Item getCurrentItem(Display display,Displayable disp)
	{
		UserItem i=(UserItem)items.get(this.getCurrentIndex());
		//MessageBox.Show("a",display,disp);
		return i; 
	}*/
}
