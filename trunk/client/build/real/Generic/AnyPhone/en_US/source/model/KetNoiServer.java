package model;

import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

/**
 *
 * @author HieuTT
 */
public class KetNoiServer {
    public static String _Url = "http://hanghaicnt.net/VM_Server/SVM.php";
    //http://cnt48dh.net/forum/VM/SVM.php
    //http://localhost/VimaruMobile/SVM.php
    //http://localhost/VM_Server/SVM.php
    public static String LayDuLieuGET(String vUrl) {
        System.gc();
        vUrl = _Url + vUrl;
        String DuLieu = "";
        HttpConnection httpConnection = null;
        InputStream inputStream = null;
        int chr;
        try {
            httpConnection = (HttpConnection) Connector.open(vUrl);
            httpConnection.setRequestMethod(HttpConnection.GET);
            httpConnection.setRequestProperty("IF-Mofified-Since", "10 Nov 2006 17:29:12 GMT");
            httpConnection.setRequestProperty("User-Agent", "Profile/MIDP-2.0 Configuration/CLDC-1.1");
            httpConnection.setRequestProperty("Content-Language", "en-US");
            
            inputStream = httpConnection.openInputStream();
            StringBuffer stringBuffer = new StringBuffer("");
            while((chr = inputStream.read()) != -1)
                stringBuffer.append((char) chr);
            DuLieu = stringBuffer.toString();
        } catch (Exception e) {
            System.out.println("Err LayDuLieuGET: " + e.getMessage());
        }finally {
            try {
                if(httpConnection != null)
                    httpConnection.close();
                if (inputStream != null)
                    inputStream.close();
            } catch (Exception e) {
                System.out.println("Err LayDuLieuGET: " + e.getMessage());
            }
        }
        return DuLieu;
    }
    
    public static String LayDuLieuPOST(String vUrl, String[] vThamSo, String[] vGiaTri){
        System.gc();
        vUrl = _Url + vUrl;
        HttpConnection httpConn = null;
        InputStream is = null;
        OutputStream os = null;
        String DuLieu = "";
        try {
            httpConn = (HttpConnection)Connector.open(vUrl);
            httpConn.setRequestMethod(HttpConnection.POST);
            httpConn.setRequestProperty("User-Agent","Profile/MIDP-1.0 Confirguration/CLDC-1.0");
            httpConn.setRequestProperty("Accept_Language","en-US");
            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //ThongTinKetNoi(httpConn);
            os = httpConn.openOutputStream();
            String params = "";
            for(int i = 0; i < vThamSo.length; i++)
                if (vThamSo[i] != null) {
                    params += vThamSo[i] + "=" + vGiaTri[i] + "&";
                }

            params.substring(0, vThamSo.length-2);
            os.write(params.getBytes());
            
            StringBuffer sb = new StringBuffer();
            is = httpConn.openDataInputStream();
            int chr;
            while ((chr = is.read()) != -1)
                sb.append((char) chr);
            DuLieu = sb.toString();
            
        }catch (Exception e) {
            System.out.println("Err LayDuLieuPOST: " + e.getMessage());
        }finally {
            try {
                if(is!= null)
                    is.close();
                if(os != null)
                    os.close();
                if(httpConn != null)
                    httpConn.close();
            } catch (Exception e) {
                System.out.println("Err LayDuLieuPOST: " + e.getMessage());
            }
        }
        return DuLieu;
    }
    
    public static void ThongTinKetNoi(HttpConnection httpConnection) {
        System.out.println("Phương thức: " + httpConnection.getRequestMethod());
        System.out.println("URL: " + httpConnection.getURL());
        System.out.println("Protocol: " + httpConnection.getProtocol()); // It better be HTTP:)
        System.out.println("Host: " + httpConnection.getHost() + " host");
        System.out.println("HTTP Port: " + httpConnection.getPort());
        System.out.println("Tham số của Request: " + httpConnection.getQuery());
    }

    
}
