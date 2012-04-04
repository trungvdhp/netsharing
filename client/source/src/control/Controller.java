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

//import util.UtilString;
import view.*;
import model.*;
import app.App;


import de.enough.polish.event.ThreadedCommandListener;
import de.enough.polish.io.RmsStorage;
import de.enough.polish.ui.Alert;
import de.enough.polish.ui.ChoiceTextField;
//import de.enough.polish.ui.DateField;
import de.enough.polish.ui.AlertType;
import de.enough.polish.ui.ChoiceGroup;
import de.enough.polish.ui.Command;
import de.enough.polish.ui.CommandListener;
import de.enough.polish.ui.Display;
//import de.enough.polish.ui.Gauge;
import de.enough.polish.ui.List;
//import de.enough.polish.ui.ScreenInfo;
import de.enough.polish.ui.StringItem;
import de.enough.polish.ui.TextField;
import de.enough.polish.ui.Displayable;
import de.enough.polish.ui.SimpleScreenHistory;
import de.enough.polish.ui.UiAccess;
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
	private TextField txtPassword = new TextField("Mật khẩu: ","",32, TextField.PASSWORD);
	private TextField txtOldPassword = new TextField("Mật khẩu cũ: ","",32, TextField.PASSWORD);
	private TextField txtNewPassword = new TextField("Mật khẩu mới: ","",32, TextField.PASSWORD);
	private TextField txtConfirm = new TextField("Xác nhận mật khẩu: ","",32, TextField.PASSWORD);
	private TextField txtFirstName = new TextField("Họ đệm: ","", 32,TextField.ANY);
	private TextField txtLastName = new TextField("Tên: ","", 32,TextField.ANY);
	private TextField txtAddress = new TextField("Địa chỉ: ","", 150,TextField.ANY);
	private TextField txtPhone = new TextField("Điện thoại: ","", 20,TextField.PHONENUMBER);
	
	private TextField txtSearch = new TextField("Từ khóa: ","", 50,TextField.ANY);
	private TextField txtTopicTitle = new TextField("Tiêu đề: ","", 100,TextField.ANY);
	private TextField txtTopicContent = new TextField("Nội dung: ","", 512,TextField.ANY);
	private TextField txtGroupName = new TextField("Tên nhóm: ","", 50,TextField.ANY);
	private TextField txtDescription = new TextField("Mô tả: ","", 150,TextField.ANY);
	private TextField txtGroupRule = new TextField("Quy tắc: ","", 300,TextField.ANY);
	
	private StringItem strCreateDate = new StringItem("Ngày cập nhật: ","");
	
	private StringItem strFirstName = new StringItem("Họ đệm: ","");
	private StringItem strLastName = new StringItem("Tên: ","");
	private StringItem strEmail = new StringItem("Email: ","");
	private StringItem strGender = new StringItem("Giới tính: ","");
	private StringItem strPhone = new StringItem("Điện thoại: ","");
	private StringItem strAddress = new StringItem("Địa chỉ: ","");
	private StringItem strTopicsCount = new StringItem("Số bài viết: ","");
	private StringItem strMembersCount = new StringItem("Số thành viên: ","");
	
	private StringItem strGroupLeader = new StringItem("Nhóm trưởng: ","");
	private StringItem strGroupName = new StringItem("Tên nhóm: ","");
	private StringItem strDescription = new StringItem("Mô tả: ","");
	private StringItem strRule = new StringItem("Quy tắc: ","");
	
	private CommandListener commandListener;
	
	private ChoiceGroup cgRemember = new ChoiceGroup("", ChoiceGroup.MULTIPLE);
	private ChoiceGroup cgGender = new ChoiceGroup("Giới tính: ", ChoiceGroup.MULTIPLE);
	
	private ChoiceTextField email;
	//private DateField dateBirthday = new DateField("Ngày sinh: ", DateField.DATE);
	
	private Command cmdLogin = new Command("Đăng nhập",Command.SCREEN,1);
	private Command cmdLogout = new Command("Đăng xuất",Command.SCREEN,1);
	private Command cmdRegister = new Command("Đăng ký", Command.SCREEN,1);
	//private Command cmdViewUserProfile=new Command("Thông tin tài khoản",Command.OK,1);
	private Command cmdChangePassword = new Command("Đổi mật khẩu", Command.SCREEN,1);
	private Command cmdExit = new Command(Locale.get("cmd.exit"), Command.EXIT, 10);
	private Command cmdBack = new Command(Locale.get("cmd.back"), Command.BACK, 2);
	private Command cmdCreateGroup=new Command("Tạo nhóm mới",Command.SCREEN,1);
	private Command cmdViewGroupInfo=new Command("Thông tin nhóm",Command.OK,1);
	private Command cmdCreateTopic=new Command("Đăng bài mới",Command.SCREEN,1);
	private Command cmdDeleteTopic=new Command("Xóa bài viết",Command.SCREEN,1);
	private Command cmdDeleteGroup=new Command("Xóa nhóm",Command.SCREEN,1);
	private Command cmdViewTopic=new Command("Xem bài viết",Command.OK,1);
	private Command cmdConfirm=new Command("Xác nhận",Command.SCREEN,1);
	private Command cmdReject=new Command("Từ chối",Command.SCREEN,1);
	//private Command cmdConfirmAll=new Command("Xác nhận hết",Command.SCREEN,1);
	//private Command cmdRejectAll=new Command("Từ chối hết",Command.SCREEN,1);
	private Command cmdViewGroupRequest=new Command("Danh sách yêu cầu",Command.SCREEN,1);
	private Command cmdViewGroupMember=new Command("Danh sách thành viên",Command.SCREEN,1);
	private Command cmdViewMyGroup=new Command("Nhóm bạn tạo",Command.SCREEN,1);
	private Command cmdViewJoinGroup=new Command("Nhóm bạn tham gia",Command.SCREEN,1);
	private Command cmdViewUpdateGroup=new Command("Sửa nhóm",Command.SCREEN,1);
	private Command cmdUpdate=new Command("Cập nhật",Command.SCREEN,1);
	private Command cmdDetail=new Command("Chi tiết",Command.SCREEN,1);
	private Command cmdGroupDetail=new Command("Chi tiết nhóm",Command.SCREEN,1);
	//private Command cmdRefreshGroup = new Command("Làm mới", Command.SCREEN, 1);
	private Command cmdUpdateGroup =new Command("Cập nhật nhóm",Command.SCREEN,1);
	private Command cmdSendRequest =new Command("Tham gia",Command.OK,1);
	
	private User user;
	
	private MainMenuList screenMainMenu;
	private SimpleScreenHistory screenHistory;
	
	private UserForm frmLogin;
	private UserForm frmRegister;
	private UserForm frmUpdateProfile;
	private UserForm frmMemberProfile;
	private UserForm frmChangePassword;
	private UserForm frmCreateGroup;
	private UserForm frmGroupInfo;
	private UserForm frmCreateTopic;
	private UserForm frmTopicDetail;
	private UserForm frmUpdateGroup;
	private UserForm frmSearch;
	//private UserForm frmJoinRequestDetail;
	private Alert frmConfirmDelGroup;
	
	private UserList frmGroup;
	private UserList frmMyGroup;
	private UserList frmJoinGroup;
	private UserList frmSearchGroup;
	private UserList frmGroupRequest;
	private UserList frmGroupMember;
	private UserList frmGroupTopic;
	private UserList frmNewTopic;
	private UserList frmMemberRequest;
	
	private void openUpdateProfileForm() {
		// TODO Auto-generated method stub
		if(user.GetInfo())
		{
			frmUpdateProfile=new UserForm("Tài khoản",null);
			
			frmUpdateProfile.addTextField(txtFirstName);
			frmUpdateProfile.addTextField(txtLastName);
			//frmProfile.addDateField(dateBirthday);
			initEmailField();
			frmUpdateProfile.addEmailField(email);
			//#style checkBoxItem
			cgGender = new ChoiceGroup("Giới tính", ChoiceGroup.EXCLUSIVE);
			cgGender.append(" Nam", null);
			cgGender.append(" Nữ", null);
			
			frmUpdateProfile.addCheckBox(cgGender);
			frmUpdateProfile.addTextField(txtPhone);
			frmUpdateProfile.addTextBox(txtAddress);
			frmUpdateProfile.addStringItemField(strCreateDate);
			
			txtFirstName.setString(user.firstName);
			txtLastName.setString(user.lastName);
			strCreateDate.setText(user.createDate);
			email.setString(user.email);
			if(user.gender.equals("0"))
			{
				cgGender.setSelectedIndex(0,true);
			}
			else
			{
				cgGender.setSelectedIndex(1,true);
			}
			txtPhone.setString(user.phone);
			txtAddress.setString(user.address);
			
			frmUpdateProfile.addMenu(cmdUpdate);
			frmUpdateProfile.addMenu(cmdChangePassword);
			frmUpdateProfile.addMenu(cmdLogout);
			frmUpdateProfile.addMenu(cmdBack);
			frmUpdateProfile.setCommandListener(this.commandListener);
			screenHistory.show(frmUpdateProfile);
		}
		else ShowError();
	}
	
	private void ShowError()
	{
		MessageBox.Show("Có lỗi xảy ra. Yêu cầu tạm thời không thể thực hiện!",  AlertType.ERROR);
	}
	
	private void openMemberProfileForm(User u) {
		// TODO Auto-generated method stub
		if(u.GetInfo())
		{
			frmMemberProfile=new UserForm(u.username,null);
			
			frmMemberProfile.addStringItemField(strFirstName);
			frmMemberProfile.addStringItemField(strLastName);
			//frmProfile.addDateField(dateBirthday);
			frmMemberProfile.addStringItemBox(strEmail);
			String gender = u.gender.equals("0")?"Nữ":"Nam";
			frmMemberProfile.addStringItemField(strGender);
			frmMemberProfile.addStringItemField(strPhone);
			frmMemberProfile.addStringItemBox(strAddress);
			frmMemberProfile.addStringItemField(strCreateDate);
			
			strFirstName.setText(u.firstName);
			strLastName.setText(u.lastName);
			strEmail.setText(u.email);
			strGender.setText(gender);
			strPhone.setText(u.phone);
			strAddress.setText(u.address);
			strCreateDate.setText(u.createDate);
			frmMemberProfile.addMenu(cmdBack);
			frmMemberProfile.setCommandListener(this.commandListener);
			screenHistory.show(frmMemberProfile);
		}
		else ShowError();
	}
	
	private void openGroupInfoForm(Group g) {
		// TODO Auto-generated method stub
		if(g.GetInfo())
		{
			frmGroupInfo=new UserForm(g.groupName,null);
			frmGroupInfo.addStringItemField(strGroupLeader);
			frmGroupInfo.addStringItemField(strGroupName);
			frmGroupInfo.addStringItemBox(strDescription);
			frmGroupInfo.addStringItemBox(strRule);
			frmGroupInfo.addStringItemField(strCreateDate);
			frmGroupInfo.addStringItemField(strTopicsCount);
			frmGroupInfo.addStringItemField(strMembersCount);
			strGroupLeader.setText(g.leader.username);
			strGroupName.setText(g.groupName);
			strDescription.setText(g.description);
			strRule.setText(g.rule);
			strTopicsCount.setText(g.topicsCount);
			strMembersCount.setText(g.membersCount);
			strCreateDate.setText(g.createDate);
			frmGroupInfo.addMenu(cmdBack);
			frmGroupInfo.setCommandListener(this.commandListener);
			screenHistory.show(frmGroupInfo);
		}
		else ShowError();
	}
	
	private void initEmailField()
	{
		String[] mailServices = new String[] {"gmail.com", "yahoo.com", "vimaru.edu.vn", "msn.com", "somewhere.com" };
		boolean allowFreeText = true;
		boolean appendSelectedChoice = true;
		String appendDelimiter = ";";
		email = new ChoiceTextField("Email: " , "", 100, TextField.EMAILADDR, 
			mailServices, allowFreeText, appendSelectedChoice, appendDelimiter );
		char choiceTriggerChar = '@';
		boolean allowChoicesBeforeChoiceTriggerHasBeenEntered = false;
		email.setChoiceTrigger( choiceTriggerChar, allowChoicesBeforeChoiceTriggerHasBeenEntered );
	}
	
	private void openChangePasswordForm() {
		// TODO Auto-generated method stub
		txtOldPassword.setString("");
		txtNewPassword.setString("");
		txtConfirm.setString("");
		frmChangePassword=new UserForm("Đổi mật khẩu",null);
		frmChangePassword.addTextField(txtOldPassword);
		frmChangePassword.addTextField(txtNewPassword);
		frmChangePassword.addTextField(txtConfirm);
		frmChangePassword.addMenu(cmdConfirm);
		frmChangePassword.addMenu(cmdBack);
		frmChangePassword.setCommandListener(this.commandListener);
		screenHistory.show(frmChangePassword);
	}
	
	public void openLoginForm()
	{
		frmLogin=new UserForm("Đăng nhập",null);
		frmLogin.addTextField(txtUsername);
		frmLogin.addTextField(txtPassword);
		//#style checkBoxItem
		cgRemember.append("Ghi nhớ mật khẩu", null);
		frmLogin.addCheckBox(cgRemember);
		frmLogin.addMenu(cmdLogin);
		frmLogin.addMenu(cmdRegister);
		frmLogin.addMenu(cmdExit);
		frmLogin.setCommandListener(this.commandListener);
		if(configuration.get("remember").equals("true"))
		{
			txtUsername.setString(configuration.get("username"));
			txtPassword.setString(configuration.get("password"));
			cgRemember.setSelectedFlags(new boolean[]{true});
		}
		screenHistory.show(frmLogin);
	}
	
	public void openRegisterForm()
	{
		frmRegister = new UserForm("Đăng ký tài khoản",null);
		frmRegister.addTextField(txtUsername);
		frmRegister.addTextField(txtPassword);
		frmRegister.addTextField(txtConfirm);
		frmRegister.addMenu(cmdConfirm);
		frmRegister.addMenu(cmdBack);
		frmRegister.setCommandListener(this.commandListener);
		screenHistory.show(frmRegister);
	}
	
	private void openUpdateGroupForm() {
		// TODO Auto-generated method stub
		UserItem item=(UserItem)frmGroup.getCurrentItem();
		Group g=(Group)item.data;
		if(!g.leader.userId.equals(user.userId))
		{
			MessageBox.Show("Không thể sửa nhóm không phải do bạn tạo!",  AlertType.ERROR);
			return;
		}
		if(g.GetInfo())
		{
			frmUpdateGroup = new UserForm(g.groupName,g);
			txtGroupName.setString(g.groupName);
			txtDescription.setString(g.description);
			txtGroupRule.setString(g.rule);
			strTopicsCount.setText(g.topicsCount);
			strMembersCount.setText(g.membersCount);
			frmUpdateGroup.addTextField(txtGroupName);
			frmUpdateGroup.addTextBox(txtDescription);
			frmUpdateGroup.addTextBox(txtGroupRule);
			frmUpdateGroup.addStringItemField(strTopicsCount);
			frmUpdateGroup.addStringItemField(strMembersCount);
			frmUpdateGroup.addCommand(cmdConfirm);
			frmUpdateGroup.addCommand(cmdBack);
			frmUpdateGroup.setCommandListener(this.commandListener);
			screenHistory.show(frmUpdateGroup);
		}
		else ShowError();
	}

	/*private void openJoinRequestDetailForm(Request request) {
		// TODO Auto-generated method stub
		frmJoinRequestDetail = new UserForm("Yêu cầu " + request.requestId + " của " + request.user.username,request);
		if(request.user.GetInfo())
		{
			
		}
		frmJoinRequestDetail.addMenu(cmdConfirm);
		frmJoinRequestDetail.addMenu(cmdReject);
		frmJoinRequestDetail.setCommandListener(this.commandListener);
		screenHistory.show(frmJoinRequestDetail);
	}*/

	private void openCreateTopicForm() {
		// TODO Auto-generated method stub
		UserItem item=(UserItem)frmGroup.getCurrentItem();
		Group g=(Group)item.data;
		frmCreateTopic=new UserForm("Viết bài mới",g);
		frmCreateTopic.addTextField(txtTopicTitle);
		frmCreateTopic.addTextBox(txtTopicContent);
		frmCreateTopic.addCommand(cmdConfirm);
		frmCreateTopic.addCommand(cmdBack);
		frmCreateTopic.setCommandListener(this.commandListener);
		screenHistory.show(frmCreateTopic);
	}

	private void openCreateGroupForm() {
		// TODO Auto-generated method stub
		frmCreateGroup = new UserForm("Tạo nhóm mới",null);
		txtGroupName.setString("");
		frmCreateGroup.addTextField(txtGroupName);
		frmCreateGroup.addMenu(cmdConfirm);
		frmCreateGroup.addMenu(cmdBack);
		frmCreateGroup.setCommandListener(this.commandListener);
		screenHistory.show(frmCreateGroup);
	}

	//private void openSettingForm() {
		// TODO Auto-generated method stub
		
	//}

	private void openSearchForm() {
		// TODO Auto-generated method stub
		frmSearch=new UserForm("Tìm kiếm",null);
		
		frmSearch.addTextField(txtSearch);
		frmSearch.addMenu(cmdConfirm);
		frmSearch.addMenu(cmdBack);
		screenHistory.show(frmSearch);
		frmSearch.setCommandListener(this.commandListener);
	}

	public void openGroupList()
	{
		ArrayList groups = user.GetGroups();
		frmGroup=new UserList("Nhóm bạn là thành viên");
		frmGroup.addCommand(cmdGroupDetail);
		UiAccess.addSubCommand(cmdViewGroupInfo, cmdGroupDetail,frmGroup);
		UiAccess.addSubCommand(cmdViewGroupMember, cmdGroupDetail,frmGroup);
		UiAccess.addSubCommand(cmdViewGroupRequest, cmdGroupDetail,frmGroup);
		frmGroup.addCommand(cmdUpdateGroup);
		UiAccess.addSubCommand(cmdCreateGroup, cmdUpdateGroup,frmGroup);
		UiAccess.addSubCommand(cmdViewUpdateGroup, cmdUpdateGroup,frmGroup);
		UiAccess.addSubCommand(cmdDeleteGroup, cmdUpdateGroup,frmGroup);
		frmGroup.addCommand(cmdViewMyGroup);
		frmGroup.addCommand(cmdViewJoinGroup);
		frmGroup.addCommand(cmdBack);
		if(groups != null)
		{
			for(int i=0;i<groups.size();i++)
			{
				Group group=(Group)groups.get(i);
				frmGroup.addEntry(new UserItem(group.groupName,group),"group");
			}
		}
		frmGroup.setCommandListener(this.commandListener);
		screenHistory.show(frmGroup);
	}
	
	public void openMyGroupList()
	{
		ArrayList groups = user.GetMyGroups();
		frmMyGroup=new UserList("Nhóm bạn tạo");
		frmMyGroup.addCommand(cmdUpdateGroup);
		UiAccess.addSubCommand(cmdCreateGroup, cmdUpdateGroup,frmMyGroup);
		UiAccess.addSubCommand(cmdViewUpdateGroup, cmdUpdateGroup,frmMyGroup);
		UiAccess.addSubCommand(cmdDeleteGroup, cmdUpdateGroup,frmMyGroup);
		frmMyGroup.addCommand(cmdGroupDetail);
		UiAccess.addSubCommand(cmdViewGroupInfo, cmdGroupDetail,frmMyGroup);
		UiAccess.addSubCommand(cmdViewGroupMember, cmdGroupDetail,frmMyGroup);
		UiAccess.addSubCommand(cmdViewGroupRequest, cmdGroupDetail,frmMyGroup);
		frmMyGroup.addCommand(cmdBack);
		if(groups!=null)
		{
			for(int i=0;i<groups.size();i++)
			{
				Group group=(Group)groups.get(i);
				frmMyGroup.addEntry(new UserItem(group.groupName,group),"group");
			}
		}
		
		frmMyGroup.setCommandListener(this.commandListener);
		screenHistory.show(frmMyGroup);
	}
	
	public void openJoinGroupList()
	{
		ArrayList groups = user.GetJoinGroups();
		frmJoinGroup=new UserList("Nhóm bạn tham gia");
		frmJoinGroup.addCommand(cmdViewGroupInfo);
		frmJoinGroup.addCommand(cmdViewGroupMember);
		frmJoinGroup.addCommand(cmdBack);
		if(groups!=null)
		{
			for(int i=0;i<groups.size();i++)
			{
				Group group=(Group)groups.get(i);
				frmJoinGroup.addEntry(new UserItem(group.groupName,group),"group");
			}
		}
		frmJoinGroup.setCommandListener(this.commandListener);
		screenHistory.show(frmJoinGroup);
	}
	
	public void openSearchGroupList()
	{
		ArrayList groups = user.SearchGroup(txtSearch.getString());
		frmSearchGroup=new UserList("Nhóm bạn chưa tham gia");
		frmSearchGroup.addCommand(cmdSendRequest);
		frmSearchGroup.addCommand(cmdBack);
		if(groups!=null)
		{
			for(int i=0;i<groups.size();i++)
			{
				Group group=(Group)groups.get(i);
				frmSearchGroup.addEntry(new UserItem(group.groupName,group),"group");
			}
		}
		frmSearchGroup.setCommandListener(this.commandListener);
		screenHistory.show(frmSearchGroup);
	}
	
	public void openGroupMemberList(Group group)
	{
		ArrayList members = group.GetMembers();
		frmGroupMember=new UserList("" + members.size() + " thành viên nhóm: " + group.groupName);
		frmGroupMember.addCommand(cmdBack);
		User u=(User)members.get(0);
		frmGroupMember.addEntry(new UserItem(u.username + " - Trưởng nhóm",u),"leader");
		for(int i=1;i<members.size();i++)
		{
			u=(User)members.get(i);
			frmGroupMember.addEntry(new UserItem(u.username,u),"user");
		}
		
		frmGroupMember.setCommandListener(this.commandListener);
		screenHistory.show(frmGroupMember);
	}
	
	public void openGroupTopicList(Group group)
	{
		frmGroupTopic = new UserList(group.groupName);
		ArrayList topics=group.GetTopics();
		if(topics!=null)
		{
			for(int i=0;i<topics.size();i++)
			{
				GroupTopic t=(GroupTopic)topics.get(i);
				UserItem topic=new UserItem(t.topic.title,t);
				frmGroupTopic.addEntry(topic,"topic");
			}
		}
		//frmGroupTopic.addCommand(cmdViewTopic);
		frmGroupTopic.addCommand(cmdCreateTopic);
		frmGroupTopic.addCommand(cmdDeleteTopic);
		frmGroupTopic.addCommand(cmdBack);
		frmGroupTopic.setCommandListener(this.commandListener);
		screenHistory.show(frmGroupTopic);
	}
	
	public void openGroupRequestList(Group group)
	{
		ArrayList requests = group.GetRequests();
		frmGroupRequest=new UserList("Yêu cầu tham gia nhóm: " + group.groupName);
		if(requests!=null)
		{
			for(int i=0;i<requests.size();i++)
			{
				Request r=(Request)requests.get(i);
				frmGroupRequest.addEntry(new UserItem("Tài khoản: " + r.user.username + " - Ngày: " + r.requestDate,r),"request");
			}
		}
		frmGroupRequest.addCommand(cmdConfirm);
		frmGroupRequest.addCommand(cmdReject);
		frmGroupRequest.addCommand(cmdBack);
		frmGroupRequest.setCommandListener(this.commandListener);
		screenHistory.show(frmGroupRequest);
	}
	
	private void openMemberRequestList() {
		// TODO Auto-generated method stub
		ArrayList requests=user.GetMemberRequests();
		frmMemberRequest=new UserList("Danh sách yêu cầu");
		if(requests!=null)
		{
			for(int i=0;i<requests.size();i++)
			{
				Request r = (Request)requests.get(i);
				frmMemberRequest.addEntry(new UserItem("Tài khoản: " + r.user.username + " - Nhóm: " 
				+r.group.groupName + " - Ngày: " + r.requestDate,r),"request");
			}
		}
		frmMemberRequest.addCommand(cmdConfirm);
		frmMemberRequest.addCommand(cmdReject);
		//frmMemberRequest.addCommand(cmdConfirmAll);
		//frmMemberRequest.addCommand(cmdRejectAll);
		frmMemberRequest.addCommand(cmdBack);
		frmMemberRequest.setCommandListener(this.commandListener);
		screenHistory.show(frmMemberRequest);
	}

	private void openNewTopicList() {
		// TODO Auto-generated method stub
		ArrayList topics=user.GetNewTopics();
		frmNewTopic = new UserList("Bài viết mới");
		if(topics!=null)
		{
			for(int i=0;i<topics.size();i++)
			{
				GroupTopic t=(GroupTopic)topics.get(i);
				UserItem topic=new UserItem(t.topic.title,t);
				frmNewTopic.addEntry(topic,"topic");
			}
		}
		//frmNewTopic.addCommand(cmdViewTopic);
		frmNewTopic.addCommand(cmdBack);
		frmNewTopic.setCommandListener(this.commandListener);
		screenHistory.show(frmNewTopic);
	}
	private void openTopicDetailForm(GroupTopic t)
	{
		frmTopicDetail = new UserForm(t.topic.title,t);
		String body="";
		body+="Chia sẻ bởi: "+t.shareUser.username+"\n"+
				"Ngày chia sẻ: "+t.shareDate+"\n"+
				t.topic.content+"\n"+
				t.commentsCount+" bình luận\n";
		frmTopicDetail.append(new StringItem("Đăng bởi: "+t.topic.author.username, body));
		frmTopicDetail.addCommand(cmdBack);
		frmTopicDetail.setCommandListener(this.commandListener);
		screenHistory.show(frmTopicDetail);
	}
	
	/**
	 * Creates a new controller.
	 * @param midlet the main application
	 */
	public Controller(App midlet) {
		this.midlet = midlet;
		this.display = Display.getDisplay(midlet);
		this.screenHistory = new SimpleScreenHistory(this.display);
		this.commandListener = new ThreadedCommandListener(this);
		MessageBox.setDisplay(display, screenHistory);
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
			openNewTopicList();
			break;
		case 1:
			openGroupList();
			break;
		case 2:
			openMemberRequestList();
			break;
		case 3:
			openSearchForm();
			break;
		case 4:
			MessageBox.Show("Chức năng đang xây dựng", AlertType.INFO);
			break;
		case 5:
			openUpdateProfileForm();
			break;
		case 6:
			MessageBox.Show("Chức năng đang xây dựng", AlertType.INFO);
			//openSettingForm();
		}
		return false;
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
		
		long currentTime = System.currentTimeMillis();
		if (currentTime - initStartTime < 1000) { // show the splash at least for 1000 ms / 1 seconds:
			try {
				Thread.sleep(1000 - currentTime + initStartTime);
			} catch (InterruptedException e) {
				// ignore
			}
		}
		openLoginForm();
	}
	
	private MainMenuList createMainMenu() {
		MainMenuList list = new MainMenuList();
		list.setCommandListener(this.commandListener);
		list.addCommand(this.cmdExit);
		list.addEntry("Tin mới");
		list.addEntry("Nhóm");
		list.addEntry("Yêu cầu");
		list.addEntry("Tìm kiếm");
		list.addEntry("Hộp thư");
		list.addEntry("Tài khoản");
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
			if (cmd == this.cmdBack){
				screenHistory.show(frmLogin);
			}
		} else if (cmd == this.cmdBack) {
			if (this.screenHistory.hasPrevious()) {
				this.screenHistory.showPrevious();
			} else {
				screenHistory.show(this.screenMainMenu);
			}
		} else if(cmd == this.cmdLogin) {
			String username = txtUsername.getString();
			String password = txtPassword.getString();
			this.user=new User(username,password);
			if(user.Login())
			{
				boolean[] gets=new boolean[1];
				cgRemember.getSelectedFlags(gets);
				if(gets[0]){
					configuration.set("remember", "true");
					configuration.set("username", txtUsername.getString());
					configuration.set("password", txtPassword.getString());
				}
				else
				{
					configuration.set("remember", "false");
					configuration.set("username", "");
					configuration.set("password", "");
				}
				this.screenMainMenu = createMainMenu();
				screenHistory.show(screenMainMenu);
			}
			else
			{
				MessageBox.Show("Đăng nhập thất bại!",AlertType.INFO);
			}
		}
		else if(cmd == this.cmdRegister)
		{
			openRegisterForm();
		} 
		else if(cmd == this.cmdLogout)
		{
			screenHistory.show(frmLogin);
		}
		else if(cmd == this.cmdChangePassword)
		{
			openChangePasswordForm();
		}
		else if(cmd == cmdConfirm)
		{
			if(disp==frmRegister){
				if(txtPassword.getString().equals(""))
				{
					MessageBox.Show("Mật khẩu không được bỏ trống!",  AlertType.INFO);
				}
				else if(txtPassword.getString().equals(txtConfirm.getString()))
				{
					user = new User(txtUsername.getString(),txtPassword.getString());
					String rs = user.Register();
					if(rs.equals("true"))
					{
						MessageBox.Show("Bạn đã đăng ký thành công!",  AlertType.INFO);
					}
					else if(rs.equals("TonTai"))
					{
						MessageBox.Show("Tài khoản này đã tồn tại!",  AlertType.INFO);
					}
					else
					{
						MessageBox.Show("Đăng ký tài khoản thất bại!",  AlertType.ERROR);
					}
				}
				else
				{
					MessageBox.Show("Xác nhận mật khẩu không khớp",  AlertType.ERROR);
				}
			}
			else if(disp==frmChangePassword)
			{
				if(txtOldPassword.getString().equals(user.password))
				{
					if(txtNewPassword.getString().equals(""))
					{
						MessageBox.Show("Mật khẩu mới không được bỏ trống!",  AlertType.INFO);
					}
					else if(txtNewPassword.getString().equals(txtConfirm.getString()))
					{
						User u = new User(user.userId, "", txtNewPassword.getString());
						if(u.ChangePassword())
						{
							MessageBox.Show("Bạn đã thay đổi mật khẩu thành công!",  AlertType.INFO);
							user.password = txtNewPassword.getString();
							txtPassword.setString(user.password);
						}
						else
						{
							MessageBox.Show("Thay đổi mật khẩu thất bại!",  AlertType.ERROR);
						}
					}
					else
					{
						MessageBox.Show("Xác nhận mật khẩu mới không khớp",  AlertType.ERROR);
					}
				}
				else
				{
					MessageBox.Show("Nhập mật khẩu cũ không chính xác!",  AlertType.ERROR);
				}
			}
			else if(disp==frmMemberRequest || disp==frmGroupRequest)
			{
				UserList ul = (UserList)disp;
				UserItem item=(UserItem)ul.getCurrentItem();
				Request r=(Request)item.data;
				if(!user.ConfirmRequest(r))
					ShowError();
				else
					MessageBox.Show("Đã chấp nhận yêu cầu tham gia nhóm!",  AlertType.INFO);
			}
			else if(disp==frmCreateTopic)
			{
				
				if(user.CreateTopic(txtTopicTitle.getString(), txtTopicContent.getString(),(Group)frmCreateTopic.data)!=null)
				{
					MessageBox.Show("Đã tạo topic " + txtTopicContent.getString(),  AlertType.INFO);
				}
			}
			else if(disp==frmCreateGroup)
			{
				Group g=user.CreateGroup(txtGroupName.getString());
				
				if(!g.groupId.equals(""))
				{
					txtGroupName.setString("");
					MessageBox.Show("Tạo nhóm mới thành công!",  AlertType.INFO);
				}
				else
				{
					MessageBox.Show("Không thể tạo nhóm mới!",  AlertType.ERROR);
				}
			}
			else if(disp==frmConfirmDelGroup)
			{
				UserItem item=(UserItem)frmGroup.getCurrentItem();
				Group g=(Group)item.data;
				if(user.DeleteGroup(g))
				{
					MessageBox.Show("Nhóm đã được xóa!",  AlertType.INFO);
				}
				else
				{
					MessageBox.Show("Không thể xóa nhóm!",  AlertType.ERROR);	
				}
			}
			else if(disp==frmUpdateGroup)
			{
				Group g=(Group)frmUpdateGroup.data;
				if(user.UpdateGroup(g.groupId, txtGroupName.getString(), txtDescription.getString(), txtGroupRule.getString()))
				{
					MessageBox.Show("Bạn đã cập nhật thông tin nhóm thành công!",  AlertType.INFO );
				}
				else
				{
					MessageBox.Show("Cập nhật thông tin nhóm thất bại!",  AlertType.ERROR);
				}
			}
			else if(disp==frmSearch)
			{
				openSearchGroupList();
			}
		}
		else if(cmd==List.SELECT_COMMAND)
		{
			if(disp==screenMainMenu)
				handleCommandMainMenu();
			else if(disp==frmGroup)
			{
				UserItem item=(UserItem)frmGroup.getCurrentItem();
				openGroupTopicList((Group)item.data);
			}
			else if(disp==frmGroupTopic)
			{
				UserItem item=(UserItem)frmGroupTopic.getCurrentItem();
				GroupTopic t=(GroupTopic)item.data;
				openTopicDetailForm(t);
			}
			else if(disp==frmNewTopic)
			{
				UserItem item=(UserItem)frmNewTopic.getCurrentItem();
				GroupTopic t=(GroupTopic)item.data;
				openTopicDetailForm(t);
				user.ViewedNewTopic(t);
			}
			else if(disp==frmMemberRequest)
			{
				UserItem t=(UserItem)frmMemberRequest.getCurrentItem();
				openMemberProfileForm(((Request)t.data).user);
			}
			else if(disp==frmGroupRequest)
			{
				UserItem t=(UserItem)frmGroupRequest.getCurrentItem();
				openMemberProfileForm(((Request)t.data).user);
			}
			else if(disp==frmSearchGroup)
			{
				UserItem item=(UserItem)frmSearchGroup.getCurrentItem();
				Group g=(Group)item.data;
				openGroupInfoForm(g);
			}
			else if(disp==frmGroupMember)
			{
				UserItem item=(UserItem)frmGroupMember.getCurrentItem();
				User u=(User)item.data;
				openMemberProfileForm(u);
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
		else if(cmd==cmdDeleteGroup)
		{
			UserItem item=(UserItem)frmGroup.getCurrentItem();
			Group g=(Group)item.data;
			if(!g.leader.userId.equals(user.userId))
			{
				MessageBox.Show("Không thể xóa nhóm không phải do bạn tạo!",  AlertType.ERROR);
				return;
			}
			frmConfirmDelGroup=MessageBox.Show("Bạn có chắc chắn muốn xóa nhóm "+g.groupName+" không?",AlertType.CONFIRMATION);
			frmConfirmDelGroup.addCommand(cmdConfirm);
			frmConfirmDelGroup.addCommand(cmdBack);
			frmConfirmDelGroup.setCommandListener(this.commandListener);
			//screenHistory.show(frmConfirmDelGroup);
		}
		else if(cmd==cmdDetail)
		{
			commandAction(List.SELECT_COMMAND,disp);
		}
		else if(cmd==cmdViewUpdateGroup)
		{
			openUpdateGroupForm();
		}
		else if(cmd==cmdViewGroupInfo)
		{
			UserList ul = (UserList)disp;
			UserItem item=(UserItem)ul.getCurrentItem();
			Group g=(Group)item.data;
			openGroupInfoForm(g);
		}
		else if(cmd==cmdViewGroupMember)
		{
			UserList ul = (UserList)disp;
			UserItem item=(UserItem)ul.getCurrentItem();
			Group g=(Group)item.data;
			openGroupMemberList(g);
		}
		else if(cmd==cmdViewGroupRequest)
		{
			UserList ul = (UserList)disp;
			UserItem item=(UserItem)ul.getCurrentItem();
			Group g=(Group)item.data;
			if(!g.leader.userId.equals(user.userId))
			{
				MessageBox.Show("Không thể xem các yêu cầu tham gia nhóm không phải do bạn tạo!",  AlertType.ERROR);
				return;
			}
			openGroupRequestList(g);
		}
		else if(cmd==cmdViewMyGroup)
		{
			openMyGroupList();
		}
		else if(cmd==cmdViewJoinGroup)
		{
			openJoinGroupList();
		}
		else if(cmd==cmdUpdate)
		{
			String gender = cgGender.isSelected(0)?"1":"0";
			User u = new User(user.userId, txtFirstName.getString(), txtLastName.getString()
					, email.getString(), gender, txtPhone.getString(), txtAddress.getString());
			if(u.Update())
			{
				MessageBox.Show("Bạn đã cập nhật thông tin tài khoản thành công!",  AlertType.INFO);
			}
			else
			{
				MessageBox.Show("Cập nhật thông tin tài khoản thất bại!",  AlertType.ERROR);
			}
		}
		else if(cmd==cmdSendRequest)
		{
			UserItem item=(UserItem)frmSearchGroup.getCurrentItem();
			Group g=(Group)item.data;
			if(!user.CreateRequest(g))
				ShowError();
			else
				MessageBox.Show("Đã gửi yêu cầu thành công!",  AlertType.INFO);
		}
		else if(cmd==cmdReject)
		{
			UserList ul = (UserList)disp;
			UserItem item=(UserItem)ul.getCurrentItem();
			Request r=(Request)item.data;
			if(!user.DeleteRequest(r))
				ShowError();
			else
				MessageBox.Show("Đã từ chối yêu cầu tham gia nhóm!",  AlertType.INFO);
		}
		else if(cmd==cmdViewTopic)
		{
			commandAction(List.SELECT_COMMAND,disp);
		}
	}
}
