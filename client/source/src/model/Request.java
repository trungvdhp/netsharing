package model;

import util.UtilString;
import base.Constants;

public class Request {
	public String requestId;
	public String groupId;
	public String groupName;
	public String userId;
	public String userFullname;
	public String requestDate;
	public Request()
	{
		this.requestId = "";
		this.groupId = "";
		this.groupName = "";
		this.userId = "";
		this.userFullname = "";
		this.requestDate = "";
	}
	
	public Request(String requestId)
	{
		this.requestId = requestId;
	}
	
	public Request(String userId, String groupId)
	{
		this.userId = userId;
		this.groupId = groupId;
	}
	public Request(String requestId, String groupId, String groupName, String userId, String userFullname, String requestDate)
	{
		this.requestId = requestId;
		this.groupId = groupId;
		this.groupName = groupName;
		this.userId = userId;
		this.userFullname = userFullname;
		this.requestDate = requestDate;
	}
	
	public boolean Create()
	{
		try
		{
			String id=Html.SendRequest("",
					new String[] {"CVM","xMaTaiKhoan","xMaNhom"},
					new String[] {"TaoYeuCau",userId, groupId}
					);
			if(id.indexOf("false")>=0) return false;
			requestId = id;
			requestDate = UtilString.GetTimeString();
			return true;
		}
		catch(Exception ex)
		{
			return false;
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
