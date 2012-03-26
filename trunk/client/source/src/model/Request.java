package model;

import util.UtilString;
import base.Constants;

public class Request {
	public String requestId;
	public Group group;
	public User user;
	public String requestDate;
	
	public Request(String requestId)
	{
		this.requestId = requestId;
	}
	
	public Request(User u,Group g)
	{
		this.user=u;
		this.group=g;
	}
	public Request(String requestId, Group g, User u, String requestDate)
	{
		this.requestId = requestId;
		group = g;
		user = u;
		this.requestDate = requestDate;
	}
	
	public static Request Create(User u,Group g)
	{
		try
		{
			String id=Html.SendRequest("",
					new String[] {"CVM","xMaTaiKhoan","xMaNhom"},
					new String[] {"TaoYeuCau",u.userId, g.groupId}
					);
			if(id.indexOf("false")>=0) return null;
			String requestDate = UtilString.GetTimeString();
			return new Request(id);
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	
	public boolean Confirm()
	{
		try
		{
			String s = Html.SendRequest("",
					new String[] {Constants.Case,"xMaYeuCau"},
					new String[] {"XacNhanYeuCauThamGia", requestId}
					);
			if(s.indexOf("false")>=0)
				return false;
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
			String s = Html.SendRequest("",
					new String[] {Constants.Case,"xMaYeuCau"},
					new String[] {"XoaYeuCauThamGia", requestId}
					);
			if(s.indexOf("false")>=0)
				return false;
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
}
