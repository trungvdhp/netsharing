package view;

import de.enough.polish.ui.*;


public class UserForm extends Form
{

	public UserForm(String title) {
		//#style userForm
		super(title, de.enough.polish.ui.StyleSheet.userformStyle );
		// TODO Auto-generated constructor stub
	}
	public void addTextField(TextField text)
	{
		//#style textField
		append(text, de.enough.polish.ui.StyleSheet.textfieldStyle );
	}
	public void addCheckBox(ChoiceGroup choice)
	{
		//#style checkBox
		append(choice, de.enough.polish.ui.StyleSheet.checkboxStyle );
	}
}
