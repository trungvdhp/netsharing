package model;

import util.UtilString;
import de.enough.polish.util.ArrayList;
import base.Constants;

public class Group {
	public String groupId;
	public String groupName;
	public String userId;
	public String userFullname;
	public String description;
	public String rule;
	public String createDate;
	public String topicsCount;
	public String membersCount;
	public String requestsCount;
	public ArrayList topics;
	public ArrayList members;
	public ArrayList requests;
	
	private Html html=new Html();
	
	public Group()
	{
		this.groupId = "";
		this.groupName = "";
		this.userId = "";
		this.userFullname = "";
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
	
	public Group( String userId, String groupname)
	{
		this.groupName = groupname;
		this.userId = userId;
	}
	
	public Group(String groupId, String groupName, String description, String rule)
	{
		this.groupId = groupId;
		this.groupName = groupName;
		this.description = description;
		this.rule = rule;
	}
	
	public Group(String groupId, String groupName, String userId, String userFullname, String createDate,
			String topicsCount, String membersCount)
	{
		this.groupId = groupId;
		this.userId = userId;
		this.groupName = groupName;
		this.userFullname = userFullname;
		this.createDate = createDate;
		this.topicsCount = topicsCount;
		this.membersCount = membersCount;
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
			this.groupName = data.get(0).toString();
			this.userId = data.get(1).toString();
			this.userFullname = data.get(2).toString();
			this.description = data.get(3).toString();
			this.rule = data.get(4).toString();
			this.createDate = data.get(5).toString();
			this.topicsCount = data.get(6).toString();
			this.membersCount = data.get(7).toString();
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	
	public ArrayList GetMembers()
	{
		ArrayList data = new ArrayList();		
		try
		{
			String s = html.SendRequest("",
					new String[] {Constants.Case,"xMaTaiKhoan"},
					new String[] {"DanhSachThanhVienNhom", userId}
					);
			if(s.indexOf("false")>=0)
				return null;
			data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			int len = data.size();
			int i=0;
			while(i<len)
			{
				User t = new User(data.get(i).toString(),data.get(i+1).toString(),
						data.get(i+2).toString(),data.get(i+3).toString());
				members.add(t);
				i += 4;
			}
			return members;
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	
	public ArrayList GetRequests()
	{
		ArrayList data = new ArrayList();		
		try
		{
			String s = html.SendRequest("",
					new String[] {Constants.Case,"xMaTaiKhoan"},
					new String[] {"DanhSachYeuCauThamGiaNhom", userId}
					);
			if(s.indexOf("false")>=0)
				return null;
			data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			int len = data.size();
			int i=0;
			while(i<len)
			{
				Request t = new Request(data.get(i).toString(),data.get(i+1).toString(),
						data.get(i+2).toString(),data.get(i+3).toString(),
						data.get(i+4).toString(),data.get(i+5).toString());
				requests.add(t);
				i += 6;
			}
			return requests;
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	
	public ArrayList GetTopics()
	{
		ArrayList data = new ArrayList();		
		try
		{
			String s = html.SendRequest("",
					new String[] {Constants.Case,"xMaTaiKhoan"},
					new String[] {"DanhSachBaiVietNhom", userId}
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
						data.get(i+4).toString(),data.get(i+5).toString(), 
						data.get(i+6).toString(),data.get(i+7).toString(),data.get(i+8).toString());
				topics.add(t);
				i += 9;
			}
			return topics;
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	public boolean Create()
	{
		try
		{
			String id=html.SendRequest("",
					new String[] {"CVM","xMaTaiKhoan","xTenNhom"},
					new String[] {"TaoNhom",userId, groupName}
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
					new String[] {"SuaNhom", groupId, groupName, description, rule}
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
