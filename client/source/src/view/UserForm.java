package view;

import de.enough.polish.ui.Choice;
import de.enough.polish.ui.ChoiceTextField;
import de.enough.polish.ui.DateField;
import de.enough.polish.ui.Form;
import de.enough.polish.ui.ChoiceGroup;
import de.enough.polish.ui.StringItem;
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
	
	public void addTextLabel(StringItem string, String text)
	{
		//#style textField
		string = new StringItem("Ngày chỉnh sửa cuối: ", text);
		append(string);
	}
	
	public void addCheckBox(ChoiceGroup choice)
	{
		//#style checkBox
		append(choice);
	}

	public void addEmailField(ChoiceTextField email, String address)
	{
		String[] mailServices = new String[] { "gmail.com", "yahoo.com", "msn.com", "somewhere.com" };
		boolean allowFreeText = false;
		boolean appendSelectedChoice = true;
		String appendDelimiter = ";";
		email = new ChoiceTextField("Email: " , address, 100, TextField.EMAILADDR, 
			mailServices, allowFreeText, appendSelectedChoice, appendDelimiter );
		char choiceTriggerChar = '@';
		boolean allowChoicesBeforeChoiceTriggerHasBeenEntered = false;
		email.setChoiceTrigger( choiceTriggerChar, allowChoicesBeforeChoiceTriggerHasBeenEntered );
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
}
