package com.cxy.ansj;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class json {
	public static void main(String[] args) {
		boolean result=false;
        try {
        	String name = "国家";
            String json= getHttpResponse(name);
            System.out.println(json);
            System.out.println(jsontoString(json)[0]);
 
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	//根据请求得到json字符串
	public static String getHttpResponse(String name) throws Exception {
		String allConfigUrl = "http://shuyantech.com/api/cndbpedia/avpair?q=";
		allConfigUrl+= java.net.URLEncoder.encode(name,"utf-8");
        BufferedReader in = null;
        StringBuffer result = null;
        try {          
            URI uri = new URI(allConfigUrl);
            URL url = uri.toURL();
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Charset", "utf-8");
         
            connection.connect();
             
            result = new StringBuffer();
            //读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
             
            return result.toString();
             
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }        
        return null;         
    }
	//解析json字符串
	public static String[] jsontoString (String jsonstring) {
		    String cs = " ";
		    String ex = " ";
		    String tp = " ";
		    String output[] = new String[3];
		    JsonParser parse =new JsonParser();  //创建json解析器
            JsonObject json=(JsonObject) parse.parse(jsonstring);  //创建jsonObject对象           
            JsonArray result=json.get("ret").getAsJsonArray();
            
            for(int i=0;i<result.size();i++){
            	JsonArray resultchild = result.get(i).getAsJsonArray();
            	if(resultchild.get(0).getAsString().equals("CATEGORY_ZH")) {
            		cs+=resultchild.get(1).getAsString()+",";        		           		
            	}
            	if(resultchild.get(0).getAsString().equals("DESC")) {
            		ex+=resultchild.get(1).getAsString()+",";        		           		
            	}
            	if(resultchild.get(0).getAsString().equals("类型")) {
            		tp+=resultchild.get(1).getAsString()+",";        		           		
            	}
            }
            output[0] = cs;output[1]=ex;output[2]=tp;
            return output;
    }
	//解析json得到ca的list和desc的字符串
		public static Object[] jsontoclass (String jsonstring,String word) {
			    Object ob[] = new Object[2];
			    ArrayList<String> list = new ArrayList<>();
			    ob[1] = "";
			    JsonParser parse =new JsonParser();  //创建json解析器
	            JsonObject json=(JsonObject) parse.parse(jsonstring);  //创建jsonObject对象           
	            JsonArray result=json.get("ret").getAsJsonArray();
	            
	            for(int i=0;i<result.size();i++){
	            	JsonArray resultchild = result.get(i).getAsJsonArray();
	            	if(resultchild.get(0).getAsString().equals("CATEGORY_ZH")) {
	            		if(!resultchild.get(1).getAsString().equals(word))
	            		list.add(resultchild.get(1).getAsString());        		           		
	            	}
	            	if(resultchild.get(0).getAsString().equals("DESC")) {
	            		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
	            		Matcher m = p.matcher(resultchild.get(1).getAsString());            		
	            		ob[1]=m.replaceAll("");;       		           		
	            	}
	            }
	            ob[0] = list;
	            return ob;
	    }

}
