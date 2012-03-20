package model;

import util.UtilString;
import de.enough.polish.util.ArrayList;
import base.Constants;

public class Group {
	public String groupId;
	public String name;
	public String description;
	public String rule;
	public String createDate;
	public String topicsCount;
	public String membersCount;
	
	private Html html=new Html();
	
	public Group()
	{
		this.groupId = "";
		this.name = "";
		this.description ="";
		this.rule = "";
		this.createDate="";
		this.topicsCount = "0";
		this.membersCount = "0";
	}
	
	public Group(String groupId)
	{
		this.groupId = groupId;
	}
	
	public Group(String groupId, String name)
	{
		this.groupId = groupId;
		this.name = name;
	}
	
	public Group(String groupId, String name, String description, String rule)
	{
		this.groupId = groupId;
		this.name = name;
		this.description = description;
		this.rule = rule;
	}
	
	public Group(String groupId, String name, String description, String rule, String createDate)
	{
		this.groupId = groupId;
		this.name = name;
		this.description = description;
		this.rule = rule;
		this.createDate = createDate;
	}
	
	
	public boolean GetContents()
	{
		ArrayList data = new ArrayList();
		try
		{
			String s = html.SendRequest("",
					new String[] {Constants.Case,"xMaNhom"},
					new String[] {"MoTaNhom", groupId}
					);
			if(s.equalsIgnoreCase("false"))
				return false;
			data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			this.description = data.get(0).toString();
			this.rule = data.get(1).toString();
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	
	public boolean GetDetails()
	{
		ArrayList data = new ArrayList();
		try
		{
			String s = html.SendRequest("",
					new String[] {Constants.Case,"xMaNhom"},
					new String[] {"ChiTietNhom", groupId}
					);
			if(s.equalsIgnoreCase("false"))
				return false;
			data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			this.name = data.get(0).toString();
			this.description = data.get(1).toString();
			this.rule = data.get(2).toString();
			this.createDate = data.get(3).toString();
			this.topicsCount = data.get(4).toString();
			this.membersCount = data.get(5).toString();
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	
	public boolean Create(String userId)
	{
		try
		{
			String id=html.SendRequest("",
					new String[] {"CVM","xMaTaiKhoan","xTenNhom"},
					new String[] {"TaoNhom",userId, name}
					);
			if(id.indexOf("false")>=0) return false;
			groupId = id;
			createDate = UtilString.GetTimeString();
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
			String id=html.SendRequest("",
					new String[] {"CVM", "xMaNhom", "xTenNhom", "xMoTa","xLuat"},
					new String[] {"SuaNhom", groupId, name, description, rule}
					);
			if(id.indexOf("false")>=0) return false;
			createDate = UtilString.GetTimeString();
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
			String id=html.SendRequest("",
					new String[] {"CVM", "xMaNhom"},
					new String[] {"XoaNhom", groupId}
					);
			if(id.indexOf("false")>=0) return false;
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
}
