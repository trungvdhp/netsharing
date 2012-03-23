/*
 * Created on Dec 15, 2010 at 9:19:09 AM.
 * 
 * Copyright (c) 2010 Robert Virkus / Enough Software
 *
 * This file is part of J2ME Polish.
 *
 * J2ME Polish is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * J2ME Polish is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with J2ME Polish; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 * Commercial licenses are also available, please
 * refer to the accompanying LICENSE.txt or visit
 * http://www.j2mepolish.org for details.
 */
package control;

import java.io.IOException;
import javax.microedition.lcdui.Image;

import base.Constants;

import util.UtilString;
import view.*;
import model.*;
import app.App;


import de.enough.polish.io.RmsStorage;
import de.enough.polish.ui.Alert;
import de.enough.polish.ui.AlertType;
import de.enough.polish.ui.ChoiceGroup;
import de.enough.polish.ui.Command;
import de.enough.polish.ui.CommandListener;
import de.enough.polish.ui.Display;
import de.enough.polish.ui.Form;
import de.enough.polish.ui.List;
import de.enough.polish.ui.StringItem;
import de.enough.polish.ui.TextField;
import de.enough.polish.ui.Displayable;
import de.enough.polish.ui.SimpleScreenHistory;
import de.enough.polish.ui.splash2.ApplicationInitializer;
import de.enough.polish.ui.splash2.InitializerSplashScreen;
import de.enough.polish.util.ArrayList;
import de.enough.polish.util.Locale;

/**
 * <p>Controls the UI of the mobile app</p>
 *
 * <p>Copyright Enough Software 2010</p>
 * @author Robert Virkus, j2mepolish@enough.de
 */
public class Controller
implements ApplicationInitializer, CommandListener
{

	private final App midlet;
	private Display display;
	private Configuration configuration;
	private RmsStorage storage;
	
	private TextField txtUsername = new TextField("Tên đăng nhập: ","", 50,TextField.ANY);
	private TextField txtPassword = new TextField("Mật khẩu: ","",50, TextField.PASSWORD);
	private TextField txtConfirm = new TextField("Xác nhận mật khẩu: ","",50, TextField.PASSWORD);
	private TextField txtSearch = new TextField("Từ khóa: ","", 50,TextField.ANY);
	private TextField txtTopicTitle = new TextField("Tiêu đề: ","", 50,TextField.ANY);
	private TextField txtTopicContent = new TextField("Nội dung: ","", 50,TextField.ANY);
	private TextField txtGroupName = new TextField("Tên nhóm: ","", 50,TextField.ANY);
	private TextField txtDescription = new TextField("Mô tả: ","", 100,TextField.ANY);
	private TextField txtGroupRule = new TextField("Luật tham gia: ","", 300,TextField.ANY);
	
	private ChoiceGroup cgRemember = new ChoiceGroup("", ChoiceGroup.MULTIPLE);
	
	private Command cmdLogin = new Command("Đăng nhập",Command.SCREEN,1);
	private Command cmdRegister = new Command("Đăng ký", Command.SCREEN,1);
	private Command cmdExit = new Command(Locale.get("cmd.exit"), Command.EXIT, 10);
	private Command cmdBack = new Command(Locale.get("cmd.back"), Command.BACK, 2);
	private Command cmdCreateGroup=new Command("Tạo nhóm mới",Command.SCREEN,1);
	private Command cmdCreateTopic=new Command("Đăng bài mới",Command.SCREEN,1);
	private Command cmdDeleteTopic=new Command("Xóa bài viết",Command.SCREEN,1);
	private Command cmdDeleteGroup=new Command("Xóa nhóm",Command.SCREEN,1);
	private Command cmdViewTopic=new Command("Xem bài viết",Command.SCREEN,1);
	private Command cmdConfirm=new Command("Xác nhận",Command.SCREEN,1);
	private Command cmdReject=new Command("Từ chối",Command.SCREEN,1);
	private Command cmdMyGroup=new Command("Nhóm của bạn",Command.SCREEN,1);
	private Command cmdShareTopic=new Command("Chia sẻ bài viết",Command.SCREEN,1);
	private Command cmdUpdateGroup=new Command("Cập nhật thông tin",Command.SCREEN,1);
	private Command cmdUpdateTopic=new Command("Chỉnh sửa",Command.SCREEN,1);
	
	
	private MainMenuList screenMainMenu;
	private SimpleScreenHistory screenHistory;
	private UserForm frmLogin;
	private UserForm frmRegister;
	private User user;
	private UserList frmGroup;
	private UserForm frmCreateGroup;
	private UserForm frmTopicDetail;
	private UserList frmGroupDetail;
	private UserList frmNewTopic;
	private UserForm frmCreateTopic;
	private UserList frmJoinRequest;
	private UserForm frmJoinRequestDetail;
	private Alert frmConfirmDelGroup;
	private UserForm frmUpdateGroup;
	

	/**
	 * Creates a new controller.
	 * @param midlet the main application
	 */
	public Controller(App midlet) {
		this.midlet = midlet;
		this.display = Display.getDisplay(midlet);
		this.screenHistory = new SimpleScreenHistory(this.display);
	}

	/**
	 * Lifecycle: starts the application for the first time.
	 */
	public void appStart() {
		String splashUrl = "/Splash.png";
		Image splashImage = null;
		try {
			splashImage = Image.createImage(splashUrl);
		} catch (Exception e) {
			//#debug error
			System.out.println("Unable to load splash image " + splashUrl +  e);
		}
		int backgroundColor = 0xffffff;
		InitializerSplashScreen splash = new InitializerSplashScreen(splashImage, backgroundColor,  this);
		this.display.setCurrent( splash );
		
	}

	public void openTopicForm(Topic[] topics)
	{
		UserList topicForm=new UserList("Bài viết mới");
		for(int i=0;i<topics.length;i++)
		{
			topicForm.addEntry(topics[i].title);
		}
		this.display.setCurrent(topicForm);
	}
	public void showMessage(String content,Displayable disp,AlertType type)
	{
		//#style alertBox
		Alert alert=new Alert("",content,null,type);
		this.display.setCurrent(alert,disp);
	}
	public void openRegisterForm()
	{
		frmRegister = new UserForm("Đăng ký tài khoản",null);
		frmRegister.addTextField(txtUsername);
		frmRegister.addTextField(txtPassword);
		frmRegister.addTextField(txtConfirm);
		frmRegister.addMenu(cmdConfirm);
		frmRegister.addMenu(cmdBack);
		frmRegister.setCommandListener(this);
		this.display.setCurrent(frmRegister);
	}
	public void openGroupForm(ArrayList groups)
	{
		frmGroup=new UserList("Nhóm tham gia");
		frmGroup.addCommand(cmdUpdateGroup);
		frmGroup.addCommand(cmdCreateGroup);
		frmGroup.addCommand(cmdDeleteGroup);
		frmGroup.addCommand(cmdMyGroup);
		frmGroup.addCommand(cmdBack);
		for(int i=0;i<groups.size();i++)
		{
			Group group=(Group)groups.get(i);
			frmGroup.addEntry(new UserItem(group.groupName,group),"group");
		}
		frmGroup.setCommandListener(this);
		this.display.setCurrent(frmGroup);
	}
	public void openGroupDetail(Group group)
	{
		Topic[] topics =(Topic[]) group.GetTopics().toArray();
		frmGroupDetail = new UserList(group.groupName);
		for(int i=0;i<topics.length;i++)
		{
			UserItem topic=new UserItem(topics[i].title,topics[i]);
			frmGroupDetail.addEntry(topic,"topic");
		}
		
		frmGroupDetail.addCommand(cmdViewTopic);
		frmGroupDetail.addCommand(cmdCreateTopic);
		frmGroupDetail.addCommand(cmdDeleteTopic);
		frmGroupDetail.addCommand(cmdBack);
		frmGroupDetail.setCommandListener(this);
		this.display.setCurrent(frmGroupDetail);
	}
	/**
	 * Lifecycle: pauses the application, e.g. when there is an incoming call.
	 */
	public void appPause() {
		// TODO implement pauseApp, e.g. stop streaming
	}

	/**
	 * Lifecycle: continues the application after it has been paused.
	 */
	public void appContinue() {
		// TODO implement continueApp, e.g. start streaming again
	}

	/**
	 * Initializes this application in a background thread that is called from within the splash screen.
	 */
	public void initApp() {
		long initStartTime = System.currentTimeMillis();
		this.storage = new RmsStorage();
		this.configuration = configurationLoad();
		// create main menu:
		
		this.screenMainMenu = createMainMenu();
		this.display.setCurrent( this.screenMainMenu );
		user = new User("34061","000000");
		user.Login();
		/*frmLogin=new UserForm("Đăng nhập");
		frmLogin.addTextField(txtUsername);
		frmLogin.addTextField(txtPassword);
		//#style checkBoxItem
		cgRemember.append("Ghi nhớ mật khẩu", null);
		frmLogin.addCheckBox(cgRemember);
		
		frmLogin.addMenu(cmdLogin);
		frmLogin.addMenu(cmdRegister);
		frmLogin.addMenu(cmdExit);
		frmLogin.setCommandListener(this);
		
		
		this.display.setCurrent(frmLogin);*/
		/*LoginForm form = new LoginForm(midlet);
		this.display.setCurrent(form);*/
		long currentTime = System.currentTimeMillis();
		if (currentTime - initStartTime < 2000) { // show the splash at least for 2000 ms / 2 seconds:
			try {
				Thread.sleep(2000 - currentTime + initStartTime);
			} catch (InterruptedException e) {
				// ignore
			}
		}
	}
	private MainMenuList createMainMenu() {
		MainMenuList list = new MainMenuList();
		list.setCommandListener(this);
		list.addCommand(this.cmdExit);
		list.addEntry("Tin mới");
		list.addEntry("Nhóm");
		list.addEntry("Yêu cầu");
		list.addEntry("Tìm kiếm");
		list.addEntry("Hộp thư");
		list.addEntry("Cá nhân");
		list.addEntry("Cài đặt");
		return list;
	}

	/**
	 * Loads the configuration of this app.
	 * @return the loaded configuration or an new one.
	 */
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

	/**
	 * Persists the configuration.
	 * @return true when saving was successful, otherwise false is returned.
	 */
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

	/*
	 * (non-Javadoc)
	 * @see de.enough.polish.ui.CommandListener#commandAction(de.enough.polish.ui.Command, de.enough.polish.ui.Displayable)
	 */
	public void commandAction(Command cmd, Displayable disp) 
	{
		if (cmd == this.cmdExit) {
			if (this.configuration.isDirty()) {
				configurationSave();
			}
			this.midlet.exit();
		} else if (disp == this.screenMainMenu) {
			if (handleCommandMainMenu()) {
				return;
			}
		} else if (cmd == this.cmdBack) {
			if (this.screenHistory.hasPrevious()) {
				this.screenHistory.showPrevious();
			} else {
				this.display.setCurrent(this.screenMainMenu);
			}
		} else if(cmd == this.cmdLogin) {
			String username = txtUsername.getString();
			String password = txtPassword.getString();
			User user=new User(username,password);
			if(user.Login())
			{
				this.display.setCurrent(screenMainMenu);
			}
			else
			{
				showMessage("Đăng nhập thất bại! Lỗi: "+user.userId, frmLogin, AlertType.INFO);
			}
		} else if(cmd == this.cmdRegister)
		{
			openRegisterForm();
		} else if(cmd == cmdConfirm)
		{
			if(disp==frmRegister){
				if(txtPassword.getString().equals(txtConfirm.getString()))
				{
					user = new User(txtUsername.getString(),txtPassword.getString());
					if(user.Register())
					{
						showMessage("Bạn đã đăng ký thành công!", frmLogin, AlertType.INFO);
					}
					else
					{
						showMessage("Đăng ký không thành công!", frmRegister, AlertType.INFO);
					}
				}
				else
				{
					showMessage("Xác nhận mật khẩu không khớp", frmRegister, AlertType.INFO);
				}
			}
			
			else if(disp==frmCreateTopic)
			{
				Topic t;
				
				if(user.CreateTopic(txtTopicTitle.getString(), txtTopicContent.getString())!=null);
				showMessage("Đã tạo topic", frmCreateTopic, AlertType.INFO);
			}
			else if(disp==frmCreateGroup)
			{
				Group g=user.CreateGroup(txtGroupName.getString());
				
				if(!g.groupId.equals(""))
				{
					txtGroupName.setString("");
					showMessage("Tạo nhóm mới thành công!", frmCreateGroup, AlertType.INFO);
				}
				else
				{
					showMessage("Không thể tạo nhóm mới!", frmCreateGroup, AlertType.INFO);
				}
			}
			else if(disp==frmConfirmDelGroup)
			{
				UserItem item=(UserItem)frmGroup.getCurrentItem();
				Group g=(Group)item.data;
				if(user.DeleteGroup(g.groupId))
				{
					showMessage("Nhóm đã được xóa!", frmGroup, AlertType.INFO);
				}
				else
				{
					showMessage("Không thể xóa nhóm!", frmGroup, AlertType.INFO);	
				}
					
			}
			else if(disp==frmUpdateGroup)
			{
				Group g=(Group)frmUpdateGroup.data;
				if(user.UpdateGroup(g.groupId, txtGroupName.getString(), txtDescription.getString(), txtGroupRule.getString()))
				{
					showMessage("Cập nhật thành công!", frmGroup, AlertType.INFO );
				}
				else
				{
					showMessage("Cập nhật không thành công!", frmGroup, AlertType.ERROR);
				}
			}
		}
		else if(cmd==List.SELECT_COMMAND)
		{
			if(disp==screenMainMenu)
				handleCommandMainMenu();
			else if(disp==frmGroup)
			{
				UserItem g=(UserItem)frmGroup.getCurrentItem();
				openGroupDetail((Group)g.data);
			}
			else if(disp==frmGroupDetail||disp==frmNewTopic)
			{
				UserItem t=(UserItem)frmNewTopic.getCurrentItem();
				openTopicDetailForm((Topic)t.data);
			}
			else if(disp==frmJoinRequest)
			{
				UserItem t=(UserItem)frmJoinRequest.getCurrentItem();
				openJoinRequestDetail((Request)t.data);
			}
		}
		else if(cmd==cmdCreateGroup)
		{
			openCreateGroupForm();
		}
		else if(cmd==cmdCreateTopic)
		{
			openCreateTopicForm();
		}
		else if(cmd==cmdConfirm)
		{
			if(disp==frmJoinRequest)
			{
				//user.ConfirmRequest();
			}
		}
		else if(cmd==cmdDeleteGroup)
		{
			UserItem item=(UserItem)frmGroup.getCurrentItem();
			Group g=(Group)item.data;
			if(g.userId!=user.userId)
			{
				showMessage("Không thể xóa nhóm không phải do bạn tạo!", frmGroup, AlertType.INFO);
				return;
			}
			frmConfirmDelGroup=MessageBox.Show("Bạn có chắc chắn muốn xóa nhóm "+g.groupName+" không?",AlertType.CONFIRMATION);
			frmConfirmDelGroup.addCommand(cmdConfirm);
			frmConfirmDelGroup.addCommand(cmdBack);
			frmConfirmDelGroup.setCommandListener(this);
			this.display.setCurrent(frmConfirmDelGroup);
		}
		else if(cmd==cmdUpdateGroup)
		{
			openUpdateGroupForm();
			
		}
	}

	private void openUpdateGroupForm() {
		// TODO Auto-generated method stub
		UserItem item=(UserItem)frmGroup.getCurrentItem();
		Group g=(Group)item.data;
		frmUpdateGroup = new UserForm(g.groupName,g);
		txtGroupName.setString(g.groupName);
		txtDescription.setString(g.description);
		txtGroupRule.setString(g.rule);
		frmUpdateGroup.addTextField(txtGroupName);
		frmUpdateGroup.addTextField(txtDescription);
		frmUpdateGroup.addTextBox(txtGroupRule);
		frmUpdateGroup.addCommand(cmdConfirm);
		frmUpdateGroup.addCommand(cmdBack);
		frmUpdateGroup.setCommandListener(this);
		this.display.setCurrent(frmUpdateGroup);
	}

	private void openJoinRequestDetail(Request request) {
		// TODO Auto-generated method stub
		String body="";
		body+="Nhóm muốn tham gia: "+request.groupName;
		body+="Họ tên: "+request.userFullname+"\n";
		body+="Ngày yêu cầu: "+request.requestDate+"\n";
		frmJoinRequestDetail=new UserForm(request.userFullname,new UserItem("",request));
		frmJoinRequestDetail.append(new StringItem(request.groupName,body));
		frmJoinRequestDetail.addMenu(cmdConfirm);
		frmJoinRequestDetail.addMenu(cmdReject);
		frmJoinRequestDetail.setCommandListener(this);
		this.display.setCurrent(frmJoinRequestDetail);
	}

	private void openCreateTopicForm() {
		// TODO Auto-generated method stub
		frmCreateTopic=new UserForm("Viết bài mới",null);
		frmCreateTopic.addTextField(txtTopicTitle);
		frmCreateTopic.addTextField(txtTopicContent);
		frmCreateTopic.addCommand(cmdConfirm);
		frmCreateTopic.addCommand(cmdBack);
		frmCreateTopic.setCommandListener(this);
		this.display.setCurrent(frmCreateTopic);
	}

	private void openCreateGroupForm() {
		// TODO Auto-generated method stub
		frmCreateGroup = new UserForm("Tạo nhóm mới",null);
		txtGroupName.setString("");
		frmCreateGroup.addTextField(txtGroupName);
		frmCreateGroup.addMenu(cmdConfirm);
		frmCreateGroup.addMenu(cmdBack);
		frmCreateGroup.setCommandListener(this);
		this.display.setCurrent(frmCreateGroup);
	}

	/**
	 * Handles commands for the main menu
	 * @return true when a command was handled
	 */
	private boolean handleCommandMainMenu()
	{
		int selectedItem = screenMainMenu.getSelectedIndex();
		/*		list.addEntry("Tin mới");
		list.addEntry("Nhóm");
		list.addEntry("Yêu cầu");
		list.addEntry("Tìm kiếm");
		list.addEntry("Hộp thư");
		list.addEntry("Cá nhân");
		list.addEntry("Cài đặt");*/
		switch(selectedItem)
		{
		case 0:
			ArrayList newTopics=user.GetNewTopics();
			openNewTopicForm(newTopics);
			break;
		case 1:
			ArrayList group=user.GetMyGroups(display,screenMainMenu);
			openGroupForm(group);
			break;
		case 2:
			ArrayList requests=user.GetJoinRequests();
			openJoinRequestsForm((Request[])requests.toArray());
			break;
		case 3:
			openSearchForm();
			break;
		case 4:
			
			break;
		case 5:
			openProfileForm();
			break;
		case 6:
			openSettingForm();
		}
		return false;
	}

	private void openSettingForm() {
		// TODO Auto-generated method stub
		
	}

	private void openProfileForm() {
		// TODO Auto-generated method stub
		
	}

	private void openSearchForm() {
		// TODO Auto-generated method stub
		UserForm form=new UserForm("Tìm kiếm",null);
		
		form.addTextField(txtSearch);
		form.addMenu(cmdConfirm);
		form.addMenu(cmdBack);
		this.display.setCurrent(form);
		form.setCommandListener(this);
	}

	private void openJoinRequestsForm(Request[] requests) {
		// TODO Auto-generated method stub
		frmJoinRequest=new UserList("Danh sách yêu cầu");
		for(int i=0;i<requests.length;i++)
		{
			frmJoinRequest.addEntry(new UserItem(requests[i].userFullname,requests[i].requestId),"request");
		}
		frmJoinRequest.addCommand(cmdConfirm);
		frmJoinRequest.addCommand(cmdReject);
		frmJoinRequest.addCommand(cmdBack);
		frmJoinRequest.setCommandListener(this);
		this.display.setCurrent(frmJoinRequest);
	}

	private void openNewTopicForm(ArrayList topics) {
		// TODO Auto-generated method stub
		frmNewTopic = new UserList("Bài viết mới");
		for(int i=0;i<topics.size();i++)
		{
			Topic t=(Topic)topics.get(i);
			frmNewTopic.addEntry(new UserItem(t.title,t),"topic");
		}
		frmNewTopic.addCommand(cmdViewTopic);
		frmNewTopic.addCommand(cmdBack);
		frmNewTopic.setCommandListener(this);
		this.display.setCurrent(frmNewTopic);
	}
	private void openTopicDetailForm(Topic topic)
	{
		frmTopicDetail = new UserForm(topic.title,new UserItem("",topic));
		String body = "Ngày tạo: "+topic.createDate+"\n";
		body+=topic.content;
		frmTopicDetail.append(new StringItem(topic.title, body));
		frmTopicDetail.addCommand(cmdBack);
		frmTopicDetail.setCommandListener(this);
	}
}
