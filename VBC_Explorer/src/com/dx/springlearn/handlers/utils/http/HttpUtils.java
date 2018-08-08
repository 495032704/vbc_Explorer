package com.dx.springlearn.handlers.utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.dx.springlearn.handlers.paramsModel.RpcResultModel;
import com.dx.springlearn.handlers.utils.HashMapUtils;
import com.dx.springlearn.handlers.utils.JsonUtils;
import com.dx.springlearn.handlers.utils.PropertiesUtils;

public class HttpUtils {
	private static Logger log=Logger.getLogger(HttpUtils.class);
	static HttpsManager connManager=new HttpsManager();
	 /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return String 所代表远程资源的响应结果
     */
    public static String get(String url,String param)
    {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = null;

            if(param == null)
                urlNameString = url;
            else
                urlNameString = url + "?" + param;

            //System.out.println("curl http url : " + urlNameString);

            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection","close");
            connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            // 建立实际的连接
            connection.connect();

            /*
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet())
            {
                System.out.println(key + "--->" + map.get(key));
            }
            */

            // 定义 BufferedReader输入流来读取URL的响应
            
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;

            while ((line = in.readLine()) != null)
            {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result.equals("") ? null : result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return String 所代表远程资源的响应结果
     */
    public static String post(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3371.0 Safari/537.36");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    } 
    
    /**
     * 基于连接池，发起HTTPPost 请求
     * @param urlstr 请求地址
     * @param param  请求参数，json格式
     * @param name  用户名	
     * @param pass 密码
     * @return
     * @throws MalformedURLException
     */
    public static String curlPoolpost(String url, String param,String name,String pass) {
        HttpPost ps=new HttpPost(url);
        CloseableHttpResponse response=null;
        String res=null;
        try {
        	String authString = name + ":" + pass;
      	    byte[] authEncBytes = Base64.getEncoder().encode(authString.getBytes());
      	    String authStringEnc = new String(authEncBytes);
        	
        	CloseableHttpClient httpClient=connManager.getHttpClient();
        	ps.addHeader("Content-Type", "application/x-www-form-urlencoded");
        	ps.addHeader("Authorization", "Basic " + authStringEnc);
        	String ct="application/x-www-form-urlencoded";
        	Map<String,Object> params=new HashMap<>();
        	params.put("Content-Type", ct);
        	params.put("accept", "*/*");
        	params.put("connection", "Keep-Alive");
        	params.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3371.0 Safari/537.36");
        	HttpEntity  httpEntity= new StringEntity(JsonUtils.objectToJson(params),ct,"UTF-8");
            ps.setEntity(httpEntity);
            
            HttpEntity  he= new StringEntity(param,ct,"UTF-8");
            ps.setEntity(he);
            
            response= httpClient.execute(ps);
            HttpEntity entity = response.getEntity();
            
            res = EntityUtils.toString(entity);
            
        } catch (Exception e) {
        	log.error("http pool sent post error:"+e.getMessage());
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
            	if (response != null) {
            		response.close();
            	}
            	ps.releaseConnection();
            }
            catch(IOException ex){
                log.error("http pool releaseConnection error:"+ex.getMessage());
            }
        }
        return res;
    } 
    /**
     * 基于连接池，发起HTTPPost 请求
     * @param urlstr 请求地址
     * @param param  请求参数，json格式
     * @param name  用户名	
     * @param pass 密码
     * @return
     * @throws MalformedURLException
     */
    public static RpcResultModel curlPoolpost(String param) throws Exception{
        RpcResultModel res=null;
        	String url=PropertiesUtils.getProperties("rpc.url");
        	String name=PropertiesUtils.getProperties("rpc.name");
        	String pass=PropertiesUtils.getProperties("rpc.pass");
            String result = curlPoolpost(url, param, name, pass);
           res=JsonUtils.jsonToObject(result, RpcResultModel.class);
        return res;
    } 
    
    /**
     * 普通模拟CURL Post请求
     * @param urlstr 请求地址
     * @param param  请求参数，json格式
     * @param name  用户名	
     * @param pass 密码
     * @return
     * @throws MalformedURLException
     */
    public static String curlPost(String url, String param,String name,String pass) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
        	CloseableHttpClient httpClient=connManager.getHttpClient();
        	URL realUrl=new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
        	 String authString = name + ":" + pass;
     	    byte[] authEncBytes = Base64.getEncoder().encode(authString.getBytes());
     	    String authStringEnc = new String(authEncBytes);
     	    conn.setRequestProperty("Authorization", "Basic " + authStringEnc);
     	    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3371.0 Safari/537.36");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
        	log.error("http sent post error:"+e.getMessage());
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
            	log.error("http sent post error:"+ex.getMessage());

            }
        }
        return result;
    } 
    
    
    
    /**
     * 发起HTTP post 异步 请求
     * @param urlstr 请求地址
     * @param param  请求参数，json格式:{"id":xxx,"rpcParams":jsonStr,"proxyParams":str=id:CanonicalName:method:time]}
     * jsonStr为调用服务器API所需参数
     * id 为系统唯一ID，使用generateID类获取；CanonicalName 为类唯一名，使用类名.class.getCanonicalName()获取；time为系统当前时间（毫秒）
     * @param name  用户名	
     * @param pass 密码
     * @return
     * @throws MalformedURLException
     */
    public static void httpAsycpost(String url, Map<String,Object> param) {
    	CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
        httpclient.start();
        HttpPost ps=new HttpPost(url);
        String rpcParams=param.get("rpcParams").toString();
        //post 设置
//        String authString = PropertiesUtils.getProperties("rpc.name") + ":" + PropertiesUtils.getProperties("rpc.pass");
        String authString = "admin:zykjtestyy0" ;

        byte[] authEncBytes = Base64.getEncoder().encode(authString.getBytes());
  	    String authStringEnc = new String(authEncBytes);
        String ct="application/x-www-form-urlencoded";
        ps.addHeader("Content-Type", "application/x-www-form-urlencoded");
    	ps.addHeader("Authorization", "Basic " + authStringEnc);
    	try {
	    	HttpEntity  he= new StringEntity(rpcParams,ct,"UTF-8");
	        ps.setEntity(he);
	        final CountDownLatch latch = new CountDownLatch(1);
	        System.out.println(" caller thread id is : " + Thread.currentThread().getId());
	        ResponseCallback callback=new ResponseCallback();
	        callback.setLatch(latch);
	        //发送前，记录requestID
	        Long id=Long.parseLong(param.get("id").toString());
	        String pparams=param.get("proxyParams").toString();
	        HashMapUtils.addRequst(id, pparams);
	       // httpclient.execute(ps, callback);
        
            latch.await();
        } catch (InterruptedException e) {
        	log.error("http asyc sent post error:"+e.getMessage());

        } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
        	log.error("http asyc sent post error:"+e.getMessage());

        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
        	try {
           	 httpclient.close();
           } catch (IOException ignore) {
           	log.error("http asyc close error:"+ignore.getMessage());
           }
		}
        
    } 
   
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
	        String apiUrl = "http://120.78.200.228:39186/";
	        String name="admin";
	        String pass="zykjtestyy0";
	         String code="{\"method\":\"listtransactions\",\"params\":[\"\"],\"id\":1}";
            // Object[] obj=new Object[] {"getinfo",null,0};
	        // String code="{\"method\":\"getblock\",\"params\":[\"fd1a016e4632e7ec03a0643963b8195c925910cd474cda867496f7441416c603\"],\"id\":2}";
	        //String code="{\"method\":\"gettransaction\",\"params\":[\"c38895280a92b633d0760a188cbb665c677b99bd10de7c4dd4adeed96d48ae6e\"],\"id\":1}";
	         //String code="{\"method\":\"getinfo\",\"params\":[\"\"],\"id\":1}";
	       // String code="{\"method\":\"getrawmempool\",\"params\":[],\"id\":1}";
	        //String code="{\"method\":\"listtransactions \",\"params\":[],\"id\":1}";
            //String code="{\"method\":\"checkwallet\",\"params\":[],\"id\":3853313524303872}";
	        //最新区块高度
            //String code="{\"method\":\"getblockhash\",\"params\":[75488],\"id\":1}";
            //fd1a016e4632e7ec03a0643963b8195c925910cd474cda867496f7441416c603
	        try {
//	        	HttpUtils.curlPoolpost(apiUrl, code, name, pass);
//	        	
//	        	String proxyParam="1:com.dx.springlearn.handlers.services.block.BlockServiceImpl:handelFinish:"+System.currentTimeMillis();
//	        	Map<String, Object> params=new HashMap<String,Object>();
//	        	params.put("id", 1);
//	        	params.put("rpcParams", code);
//	        	params.put("proxyParams", proxyParam);
	        	String str=HttpUtils.curlPoolpost(apiUrl, code, name, pass);
	        	System.out.println(str);
//				HttpUtils.httpAsycpost(apiUrl, params);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}     //开始模拟浏览器请求
	        
                 
	}
      
}
