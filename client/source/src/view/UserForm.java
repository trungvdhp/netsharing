package view;

//import de.enough.polish.ui.Choice;
import de.enough.polish.ui.ChoiceTextField;
import de.enough.polish.ui.DateField;
import de.enough.polish.ui.Form;
import de.enough.polish.ui.ChoiceGroup;
import de.enough.polish.ui.ListItem;
import de.enough.polish.ui.StringItem;
import de.enough.polish.ui.TextField;
import de.enough.polish.ui.Command;

public class UserForm extends Form
{
	public Object data;
	public int[] pageId;
	public int id;
	public UserForm(String title) {
		//#style userForm
		super(title);
		pageId = new int[]{0, 0};
		id=0;
	}
	public UserForm(String title,Object data) {
		//#style userForm
		super(title);
		this.data=data;
		pageId = new int[]{0, 0};
		id=0;
		// TODO Auto-generated constructor stub
	}
	public UserForm(String title,Object data, int id) {
		//#style userForm
		super(title);
		this.data=data;
		pageId = new int[]{0, 0};
		this.id=id;
		// TODO Auto-generated constructor stub
	}
	public void addTextField(TextField text)
	{
		//#style textField
		append(text);
	}
	
	public void addStringItemField(StringItem string)
	{
		//#style stringField
		append(string);
	}
	
	public void addStringItemBox(StringItem string)
	{
		//#style stringBox
		append(string);
	}
	
	public void addCheckBox(ChoiceGroup choice)
	{
		//#style checkBox
		append(choice);
	}

	public void addEmailField(ChoiceTextField email)
	{
		//#style addressInput
		append(email);
	}
	
	public void addDateField(DateField date)
	{
		//#style dateInput
		append(date);
	}
	
	public void addMenu(Command cmd)
	{
		addCommand(cmd);
	}
	
	public void addTextBox(TextField text)
	{
		//#style textBox
		append(text);
	}
	
	public void addListItem(ListItem list)
	{
		//#style comment
		append(list);
	}
}
