package control;

import java.io.IOException;

import javax.microedition.midlet.MIDlet;

import app.App;

import model.Configuration;
import model.User;

import view.UserForm;
import de.enough.polish.io.RmsStorage;
import de.enough.polish.ui.ChoiceGroup;
import de.enough.polish.ui.Command;
import de.enough.polish.ui.CommandListener;
import de.enough.polish.ui.Display;
import de.enough.polish.ui.Displayable;
import de.enough.polish.ui.TextField;
import de.enough.polish.util.Locale;

public class LoginForm extends UserForm implements CommandListener
{
	private TextField txtUsername = new TextField("Tên đăng nhập: ","", 50,TextField.ANY);
	private TextField txtPassword = new TextField("Mật khẩu: ","",50, TextField.PASSWORD);
	private Command cmdLogin = new Command("Đăng nhập",Command.SCREEN,1);
	private Command cmdRegister = new Command("Đăng ký", Command.SCREEN,1);
	private Command cmdExit = new Command(Locale.get("cmd.exit"), Command.EXIT, 10);
	private ChoiceGroup cgRemember = new ChoiceGroup("", ChoiceGroup.MULTIPLE);
	private Display display;
	public User user;
	private Configuration configuration;
	private RmsStorage storage;
	private App midlet;
	public LoginForm(App midlet) {
		super("Đăng nhập",null);
		this.midlet = midlet;
		display=Display.getDisplay(midlet);
		this.storage = new RmsStorage();
		this.configuration = configurationLoad();
		addTextField(txtUsername);
		addTextField(txtPassword);
		//#style checkBoxItem
		cgRemember.append("Ghi nhớ mật khẩu", null);
		addCheckBox(cgRemember);
		
		addMenu(cmdLogin);
		addMenu(cmdRegister);
		addMenu(cmdExit);
		user=new User();
		setCommandListener(this);
		
	}


	public void commandAction(Command cmd, Displayable disp) {
		if(cmd==cmdLogin)
		{
			user=new User(txtUsername.getString(),txtPassword.getString());
			if(user.Login())
			{
				this.display.setCurrent(MessageBox.Show("Đăng nhập thành công!"),this);
			}
			else
			{
				this.display.setCurrent(MessageBox.Show("Đăng nhập thất bại!"),this);
			}
		}
		else if(cmd==cmdRegister)
		{
			
		}
		else if(cmd==cmdExit)
		{
			if (this.configuration.isDirty()) {
				configurationSave();
			}
			this.midlet.exit();
		}
	}
	private Configuration configurationLoad() {
		try {
			Configuration cfg = (Configuration) this.storage.read(Configuration.KEY);
			return cfg;
		} catch (IOException e) {
			//#debug info
			System.out.println("Unable to load configuration" + e);
		}
		return new Configuration();
	}
	private boolean configurationSave() {
		try {
			this.storage.save(this.configuration, Configuration.KEY);
			return true;
		} catch (IOException e) {
			//#debug error
			System.out.println("Unable to store the configuration" + e);
			return false;
		}
	}
	
}
