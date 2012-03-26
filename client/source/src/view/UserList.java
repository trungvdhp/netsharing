package view;

import model.Group;
import model.Topic;
import model.TopicGroup;
import control.MessageBox;
import control.UserItem;
import de.enough.polish.ui.Display;
import de.enough.polish.ui.Displayable;
import de.enough.polish.ui.Item;
import de.enough.polish.ui.List;
import de.enough.polish.util.ArrayList;

public class UserList extends List{
	private ArrayList items;
	public UserList(String title) {
		
		//#style userList
		super(title,List.IMPLICIT);
		// TODO Auto-generated constructor stub
		items=new ArrayList();
	}
	public void addEntry(String item)
	{
		//#style userListItem
		this.append(item, null);
		
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
			TopicGroup t=(TopicGroup)item.data;
			//#style topicItem
			this.append(t.topic.title,null);
		}
	}
	public Item getCurrentItem()
	{
		Item i=(Item)items.get(this.getCurrentIndex());
		
		return i; 
	}
	public Item getCurrentItem(Display display,Displayable disp)
	{
		UserItem i=(UserItem)items.get(this.getCurrentIndex());
		//MessageBox.Show("a",display,disp);
		return i; 
	}
}
