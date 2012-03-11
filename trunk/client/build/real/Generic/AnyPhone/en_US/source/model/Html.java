package model;

import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

public class Html {
	
	public String SendRequest(String url,String[] args,String[] values) throws Exception
	{
		HttpConnection connection = (HttpConnection) Connector.open(url);
		connection.setRequestMethod(HttpConnection.POST);
		//connection.setRequestProperty("User-Agent", "Nokia6300/2.0 (04.20) Profile/MIDP-2.0 Configuration/CLDC-1.1 UNTRUSTED/1.0");
		connection.setRequestProperty("User-Agent","Profile/MIDP-1.0 Confirguration/CLDC-1.0");
		connection.setRequestProperty("Accept_Language","en-US");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		
		String params="";
		for(int i=0;i<args.length;i++)
			params+=args[i]+"="+values[i]+(i==args.length-1?"":"&");
		if(args.length>0)
		{
			OutputStream os =  connection.openOutputStream();
			os.write(params.getBytes());
		}
		InputStream is = connection.openDataInputStream();
		byte[] data=new byte[is.available()];
		is.read(data);
		return new String(data);
	}
	public String SendRequest(String url) throws Exception
	{
		return SendRequest(url,new String[]{},new String[]{});
	}
}
