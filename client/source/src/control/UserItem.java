package control;


import de.enough.polish.ui.ChoiceItem;
import de.enough.polish.ui.List;

public class UserItem extends ChoiceItem{
	public Object data;
	public UserItem(String text,  Object data) {
		super(text, null, List.IMPLICIT);
		// TODO Auto-generated constructor stub
		this.data=data;
	}
	
	public UserItem(String text,  int choiceType, Object data) {
		super(text, null, choiceType);
		// TODO Auto-generated constructor stub
		this.data=data;
	}

}
