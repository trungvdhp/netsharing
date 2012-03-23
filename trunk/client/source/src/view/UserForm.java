package view;

import control.UserItem;
import de.enough.polish.ui.Form;
import de.enough.polish.ui.ChoiceGroup;
import de.enough.polish.ui.TextField;
import de.enough.polish.ui.Command;

public class UserForm extends Form
{
	public Object data;
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
	public void addCheckBox(ChoiceGroup choice)
	{
		//#style checkBox
		append(choice);
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
}
