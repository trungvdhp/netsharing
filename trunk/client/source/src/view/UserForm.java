package view;

import de.enough.polish.ui.*;


public class UserForm extends Form
{

	public UserForm(String title) {
		//#style userForm
		super(title);
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
}
