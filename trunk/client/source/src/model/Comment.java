package model;

import util.UtilString;

public class Comment {
	public String commentId;
	public String content;
	public String time;
	public User user;
	public Comment(String commentId)
	{
		this.commentId=commentId;
	}
	public Comment(String commendId,User user,String content,String time)
	{
		this.commentId=commendId;
		this.user = user;
		this.content = content;
		this.time=time;		
	}
	
	
	public static Comment Create(GroupTopic t,User u,String content)
	{
		try
		{
			String id=Html.SendRequest("",
					new String[] {"CVM","xMaTaiKhoan", "xMaBaiVietNhom", "xNoiDung"},
					new String[] {"TaoBinhLuan",u.userId,t.topicGroupId,content}
					);
			if(id.indexOf("false")>=0) return null;
			
			return new Comment(id);
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
	
	public static boolean Delete(Comment c)
	{
		try
		{
			String id=Html.SendRequest("",
					new String[] {"CVM", "xMaBinhLuan"},
					new String[] {"XoaBinhLuan", c.commentId}
					);
			if(id.indexOf("false")>=0) return false;
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}}
