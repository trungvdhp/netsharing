package model;

import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

import control.MessageBox;
import de.enough.polish.ui.AlertType;

//import de.enough.polish.io.PostRedirectHttpConnection;
//import de.enough.polish.util.URL;

public class Html {
	private static String _sessionId = "";
	public static boolean isBusy = false;
	private static String path="http://vimaru.byethost7.com/SVM.php";
	//private static String path="http://localhost/VM_Server/SVM.php";
	public static void setSessionId(String sessionId)
	{
		_sessionId=sessionId;
	}
	public static String SendRequest(String url,String[] args,String[] values)
	{
		try{
		isBusy = true;
		url=path+url;
		HttpConnection connection = (HttpConnection) Connector.open(url);
		connection.setRequestMethod(HttpConnection.POST);
		//connection.setRequestProperty("User-Agent", "Nokia6300/2.0 (04.20) Profile/MIDP-2.0 Configuration/CLDC-1.1 UNTRUSTED/1.0");
		connection.setRequestProperty("User-Agent","Profile/MIDP-2.0 Confirguration/CLDC-1.1");
		//connection.setRequestProperty("User-Agent", "Mozilla/5.0 (SymbianOS/9.2; U; Series60/3.1 NokiaE71-1/100.07.57; Profile/MIDP-2.0 Configuration/CLDC-1.1 ) AppleWebKit/413 (KHTML, like Gecko) Safari/413");
		//connection.setRequestProperty("Accept_Language","en-US");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setRequestProperty("Accept", "text/txt" );
		
		String params="";
		for(int i=0;i<args.length;i++)
			params+=args[i]+"="+values[i]+"&";
		if(!_sessionId.equals("")){
			params+="sid="+_sessionId;
			//MessageBox.Show(_sessionId);
		}
		
		if(args.length>0)
		{
			OutputStream os =  connection.openOutputStream();
			os.write(params.getBytes());
		}
		InputStream is = connection.openDataInputStream();
		byte[] data=new byte[is.available()];
		is.read(data);
		isBusy = false;
		return new String(data);
		}
		catch(Exception ex)
		{
			isBusy=false;
			return "";
		}
	}
	/*public static String SendRequest(String url,String[] args,String[] values) throws Exception
	{
		url=path+url;
		PostRedirectHttpConnection connection=new PostRedirectHttpConnection(url);
		connection.setRequestProperty("User-Agent","Profile/MIDP-1.0 Confirguration/CLDC-1.0");
		connection.setRequestProperty("Accept_Language","en-US");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		
		for(int i=0;i<args.length;i++)
			connection.addPostParameter(args[i], values[i]);
		InputStream is = connection.openDataInputStream();
		byte[] data=new byte[is.available()];
		is.read(data);
		return new String(data);
	}*/
	/*public static String SendRequest(String url,String[] args,String[] values) throws Exception
	{
		url=path+url;
		URL u=new URL(url);
		for(int i=0;i<args.length;i++)
			u.addParameter(args[i], values[i]);
		
		HttpConnection connection = (HttpConnection) Connector.open(u.build());
		connection.setRequestMethod(HttpConnection.GET);
		//connection.setRequestProperty("User-Agent", "Nokia6300/2.0 (04.20) Profile/MIDP-2.0 Configuration/CLDC-1.1 UNTRUSTED/1.0");
		connection.setRequestProperty("User-Agent","Profile/MIDP-1.0 Confirguration/CLDC-1.0");
		connection.setRequestProperty("Accept_Language","en-US");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		
		InputStream is = connection.openDataInputStream();
		byte[] data=new byte[is.available()];
		is.read(data);
		return new String(data);
	}*/
	public static String SendRequest(String url) throws Exception
	{
		return SendRequest(url,new String[]{},new String[]{});
	}
}
