package model;

public class User {
	public String userId;
	public String username;
	private String password;
	Html html=new Html();
	public User(String username,String password)
	{
		this.username=username;
		this.password=password;
	}
	public boolean login()
	{
		try
		{
			String data=html.SendRequest("",
					new String[] {"CVM","xTaiKhoan","xMatKhau"},
					new String[] {"DangNhap",username,password}
					);
			userId=data;
			if(data.indexOf("TKTD")<0)
				return false;
			return true;
		}
		catch(Exception ex)
		{
			//return "";
			return false;
		}
	}
	public boolean createGroup(String groupName)
	{
		try
		{
			String groupId=html.SendRequest("",
					new String[] {"CVM","xMaTaiKhoan", "xTenNhom"},
					new String[] {"TaoNhom", userId, groupName}
					);
			if(groupId.equalsIgnoreCase("false")) return false;
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
		
	}
	public void createTopic()
	{
		
	}
	public void writeComment()
	{
		
	}
}
