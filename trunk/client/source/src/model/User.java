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
	public Group createGroup(String groupName)
	{
		try
		{
			String groupId=html.SendRequest("",
					new String[] {"CVM","xMaTaiKhoan", "xTenNhom"},
					new String[] {"TaoNhom", userId, groupName}
					);
			if(groupId.equalsIgnoreCase("false")) return null;
			return new Group(groupId);
		}
		catch(Exception ex)
		{
			return null;
		}
		
	}
	public Topic createTopic(String topicTitle,String topicContent)
	{
		try
		{
			String topicId=html.SendRequest("",
					new String[] {"CVM","xMaTaiKhoan", "xTieuDe","xNoiDung"},
					new String[] {"TaoBaiViet", userId, topicTitle,topicContent}
					);
			if(topicId.equalsIgnoreCase("false")) return null;
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
