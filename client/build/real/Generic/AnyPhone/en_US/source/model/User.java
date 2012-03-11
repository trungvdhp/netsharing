package model;

public class User {
	public String code;
	public String username;
	private String password;
	public User(String username,String password)
	{
		this.username=username;
		this.password=password;
	}
	public boolean login()
	{
		try
		{
			Html html=new Html();
			String data=html.SendRequest("http://hanghaicnt.net/VM_Server/SVM.php",
					new String[] {"CVM","xTaiKhoan","xMatKhau"},
					new String[] {"DangNhap",username,password}
					);
			if(data.equalsIgnoreCase("") || data.equalsIgnoreCase("false"))
				return false;
			code=data;
			return true;
		}
		catch(Exception ex)
		{
			//return "";
			return false;
		}
	}
}
