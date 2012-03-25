package model;

import util.UtilString;

public class Comment {
	public String commentId;
	public String userId;
	public String userFullname;
	public String content;
	public String time;
	public User user=new User();
	public Comment()
	{
		this.commentId = "";
		this.userId = "";
		this.userFullname = "";
		this.content = "";
		this.time = "";
	}
	public Comment(String commendId,String userName,String content,String time)
	{
		this.commentId=commendId;
		this.user.username = userName;
		this.content = content;
		this.time=time;
	}
	public Comment(String id)
	{
		this.commentId = id;
		this.userId = "";
		this.userFullname = "";
		this.content = "";
		this.time = "";
	}
	
	public Comment(String userId, String content)
	{
		this.userId = userId;
		this.content = content;
	}
	public Comment(String id, String userId, String content)
	{
		this.commentId = id;
		this.userId = userId;
		this.content = content;
	}
	/*public Comment(String id, String userId, String username, String content)
	{
		this.commentId = id;
		this.userId = userId;
		this.userFullname = username;
		this.content = content;
	}*/
	
	public Comment(String id, String userId, String username, String content, String time)
	{
		this.commentId = id;
		this.userId = userId;
		this.userFullname = username;
		this.content = content;
		this.time = time;
	}
	
	public boolean Create(String topicGroupId)
	{
		try
		{
			String id=Html.SendRequest("",
					new String[] {"CVM","xMaTaiKhoan", "xMaBaiVietNhom", "xNoiDung"},
					new String[] {"TaoBinhLuan",userId,topicGroupId,content}
					);
			if(id.indexOf("false")>=0) return false;
			commentId= id;
			time = UtilString.GetTimeString();
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	
	public boolean Update()
	{
		try
		{
			String id=Html.SendRequest("",
					new String[] {"CVM", "xMaBinhLuan", "xNoiDung"},
					new String[] {"SuaBinhLuan", commentId, content}
					);
			if(id.indexOf("false")>=0) return false;
			time = UtilString.GetTimeString();
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	
	public boolean Delete()
	{
		try
		{
			String id=Html.SendRequest("",
					new String[] {"CVM", "xMaBinhLuan"},
					new String[] {"XoaBinhLuan", commentId}
					);
			if(id.indexOf("false")>=0) return false;
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}}
