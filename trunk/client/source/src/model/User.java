package model;

import util.UtilString;
import base.Constants;

public class User {
	public String id;
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
	
	public User(String username,String password)
	{
		this.username=username;
		this.password=password;
	}

	public User(String id,String username,String firstName,String lastName)
	{
		this.id = id;
		this.username=username;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public User(String data[])
	{
		this.id= data[0];
		this.username = data[1];
		this.password = data[2];
		this.hoDem = data[3];
		this.ten = data[4];
		this.ngaySinh = data[5];
		this.gioiTinh = data[6];
		this.email = data[7];
		this.dienThoai = data[8];
		this.diaChi = data[9];
		this.ngayTao = data[10];
		this.ngayVaoTruong = data[11];
	}
	
	public boolean login()
	{
		try
		{
			String data=html.SendRequest("",
					new String[] {"CVM","xTaiKhoan","xMatKhau"},
					new String[] {"DangNhap",username,password}
					);
			if(data.indexOf("TKTD")<0)
				return false;
			id=data;
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	
	public boolean register()
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
	
	public boolean getInfo()
	{
		String data[] = new String[10];
		try
		{
			String s = html.SendRequest("",
					new String[] {Constants.Case,"xMaTaiKhoan"},
					new String[] {"ThongTinTaiKhoan", id}
					);
			if(s.indexOf("false")>=0)
				return false;
			data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			this.username = data[0];
			this.hoDem = data[1];
			this.ten = data[2];
			this.ngaySinh =data[3];
			this.email = data[4];
			this.gioiTinh = data[5];
			this.dienThoai = data[6];
			this.anhDaiDien = data[7];
			this.diaChi = data[8];
			this.ngayVaoTruong = data[9];
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	
	public boolean updateInfo()
	{
		try
		{
			String data=html.SendRequest("",
					new String[] {"CVM","xMaTaiKhoan","xMatKhau","xHoDem","xTen","xNgaySinh","xGioiTinh",
					"xEmail","xDienThoai","xDiaChi","xNgayTao","xNgayVaoTruong"},
					new String[] {"CapNhatTaiKhoan",id,password,hoDem,ten,ngaySinh,gioiTinh,email,dienThoai,
					diaChi,ngayTao,ngayVaoTruong}
					);
			if(data.indexOf("false") >= 0)
				return false;
			id=data;
			return true;
		}
		catch(Exception ex)		{
			//return "";
			return false;
		}
	}
	
	public Group createGroup(String groupName)
	{
		try
		{
			String groupId=html.SendRequest("",
					new String[] {"CVM","xMaTaiKhoan", "xTenNhom"},
					new String[] {"TaoNhom", id, groupName}
					);
			if(groupId.indexOf("false")>=0) return null;
			return new Group(groupId);
		}
		catch(Exception ex)
		{
			return null;
		}
		
	}
	
	public Topic createTopic(Group group,String topicTitle,String topicContent)
	{
		try
		{
			String topicId=html.SendRequest("",
					new String[] {"CVM","xMaTaiKhoan", "xTieuDe","xNoiDung"},
					new String[] {"TaoBaiViet", id, topicTitle,topicContent}
					);
			if(topicId.indexOf("false")>=0) return null;
			return new Topic(topicId);
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	
	public void writeComment(Topic topic,String comment)
	{
		
	}
}
