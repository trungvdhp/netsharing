package model;

import java.util.*;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

public class Html {
	
	public String SendRequest(String url) throws Exception
	{
		HttpConnection connection = (HttpConnection) Connector.open(url);
		connection.setRequestMethod(HttpConnection.GET);
		connection.setRequestProperty("User-Agent", "Nokia6300/2.0 (04.20) Profile/MIDP-2.0 Configuration/CLDC-1.1 UNTRUSTED/1.0");
		
		Scanner scanner = new Scanner(connection.openInputStream());
		return scanner.useDelimiter("\\A").next();
	}
	public static String requestPost(String url, String[] args,String[] values)
	{
		return url;
	}
}
