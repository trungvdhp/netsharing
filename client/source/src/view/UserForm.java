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
	public UserForm(String title,Object data) {
		//#style userForm
		super(title);
		this.data=data;
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
