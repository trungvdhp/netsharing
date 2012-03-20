package model;

import de.enough.polish.util.ArrayList;

import util.UtilString;
import base.Constants;

public class User {
	public String userId;
	public String username;
	public String password;
	public String hoDem;
	public String ten;
	public String ngaySinh;
	public String gioiTinh;
	public String email;
	public String dienThoai;
	public String diaChi;
	public String anhDaiDien;
	public String ngayTao;
	public String trangThai;
	public String ngayVaoTruong;
	public String maQuyen;
	
	public String firstName;
	public String lastName;
	Html html=new Html();
	Topic topic = new Topic();
	Group group = new Group();
	TopicGroup topicgroup = new TopicGroup();
	Comment comment = new Comment();
	ArrayList newtopics;
	
	public User()
	{
		
	}
	
	public User(String username,String password)
	{
		this.username=username;
		this.password=password;
	}

	public User(String id,String username,String firstName,String lastName)
	{
		this.userId = id;
		this.username=username;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public User(ArrayList data)
	{
		this.userId= data.get(0).toString();
		this.username = data.get(1).toString();
		this.password = data.get(2).toString();
		this.hoDem = data.get(3).toString();
		this.ten = data.get(4).toString();
		this.ngaySinh = data.get(5).toString();
		this.gioiTinh = data.get(6).toString();
		this.email = data.get(7).toString();
		this.dienThoai = data.get(8).toString();
		this.diaChi = data.get(9).toString();
		this.ngayTao = data.get(10).toString();
		this.ngayVaoTruong = data.get(11).toString();
	}
	
	public boolean Login()
	{
		try
		{
			String data=html.SendRequest("",
					new String[] {"CVM","xTaiKhoan","xMatKhau"},
					new String[] {"DangNhap",username,password}
					);
			if(data.indexOf("TKTD")<0)
				return false;
			userId=data;
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	
	public void Logout()
	{
		userId = "";
	}
	
	public boolean Register()
	{
		try
		{
			String data=html.SendRequest("",
					new String[] {"CVM","xTaiKhoan","xMatKhau"},
					new String[] {"DangKyTaiKhoan",username,password}
					);
			if(data.indexOf("false") >= 0)
				return false;
			return true;
		}
		catch(Exception ex)		
		{
			return false;
		}
	}
	
	public boolean GetInfo()
	{
		ArrayList data = new ArrayList();		
		try
		{
			String s = html.SendRequest("",
					new String[] {Constants.Case,"xMaTaiKhoan"},
					new String[] {"ThongTinTaiKhoan", userId}
					);
			if(s.indexOf("false")>=0)
				return false;
			data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			this.username = data.get(0).toString();
			this.hoDem = data.get(1).toString();
			this.ten = data.get(2).toString();
			this.ngaySinh =data.get(3).toString();
			this.email = data.get(4).toString();
			this.gioiTinh = data.get(5).toString();
			this.dienThoai = data.get(6).toString();
			this.anhDaiDien = data.get(7).toString();
			this.diaChi = data.get(8).toString();
			this.ngayVaoTruong = data.get(9).toString();
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	
	public ArrayList GetNewTopics()
	{
		ArrayList data = new ArrayList();		
		try
		{
			String s = html.SendRequest("",
					new String[] {Constants.Case,"xMaTaiKhoan"},
					new String[] {"BaiVietMoi", userId}
					);
			if(s.indexOf("false")>=0)
				return null;
			data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			int len = data.size();
			int i=0;
			while(i<len)
			{
				TopicGroup t = new TopicGroup(data.get(i).toString(),data.get(i+1).toString(),
						data.get(i+2).toString(),data.get(i+3).toString(),
						data.get(i+4).toString(),data.get(i+5).toString(), data.get(i+6).toString());
				newtopics.add(t);
				i += 7;
			}
			return newtopics;
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	
	public boolean Update()
	{
		try
		{
			String data=html.SendRequest("",
					new String[] {"CVM","xMaTaiKhoan","xMatKhau","xHoDem","xTen","xNgaySinh","xGioiTinh",
					"xEmail","xDienThoai","xDiaChi","xNgayTao","xNgayVaoTruong"},
					new String[] {"CapNhatTaiKhoan",userId,password,hoDem,ten,ngaySinh,gioiTinh,email,dienThoai,
					diaChi,ngayTao,ngayVaoTruong}
					);
			if(data.indexOf("false") >= 0)
				return false;
			userId=data;
			return true;
		}
		catch(Exception ex)		{
			//return "";
			return false;
		}
	}
	
	public Group CreateGroup(String groupName)
	{
		group = new Group("", groupName);
		group.Create(userId);
		return group;
	}
	
	public boolean UpdateGroup(String groupId, String groupName, String groupDescription, String groupRule)
	{
		group = new Group(groupId, groupName, groupDescription, groupRule);
		return group.Update();
	}
	
	public boolean DeleteGroup(String groupId)
	{
		group = new Group(groupId);
		return group.Delete();
	}
	
	public Topic CreateTopic(String topicTitle, String topicContent)
	{
		topic = new Topic(topicTitle, topicContent);
		topic.Create(userId);
		return topic;
	}
	
	public boolean UpdateTopic(String topicId, String topicTitle, String topicContent)
	{
		topic = new Topic(topicId, topicTitle, topicContent);
		return topic.Update();
	}
	
	public boolean DeleteTopic(String topicId)
	{
		topic = new Topic(topicId);
		return topic.Delete();
	}
	
	public Comment CreateComment(String topicGroupId, String commentContent)
	{
		comment = new Comment("", userId, commentContent);
		comment.Create(topicGroupId);
		return comment;
	}
}
