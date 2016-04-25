package test;

import java.io.IOException;
import java.util.Scanner;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.lucene.crawl.GetProxyIpUtil;

public class GetJsoupDoc {
	
	private int   count=1;
	private int sum=1;
	 /**
     * heepclient 抓取页面
     * jroup 解析页面内容
     * @param args
	 * @throws Exception 
     */
    public static void main(String[] args) throws Exception {	
    	GetJsoupDoc http=new GetJsoupDoc();
    	JSONObject json=GetProxyIpUtil.readOneProxyIP();
		String ip2=json.getString("ip");
		int port2=json.getInt("port");
    	http.getDoc(ip2,port2);

    }
    
    public void getDoc(String ip,int port){
    	String strphone="15674979867";
        HttpClient client = new HttpClient();
        // 设置代理服务器地址(URL)和端口
        client.getHostConfiguration().setHost( "http://www.proxy360.cn/" , 80);
        client.getHostConfiguration().setProxy("211.141.130.56", 8118);
        HttpMethod method=getGetMethod();
        
        
      //  System.out.println(method.getStatusText());
        try {
        	  method.getParams().setSoTimeout(1000);
              method.getParams().setParameter(HttpMethodParams.HEAD_BODY_CHECK_TIMEOUT, 1000);


      		// 设置 get 请求超时�?60 �?
              method.getParams().setSoTimeout(200);
      		// 设置请求重试处理，用的是默认的重试处理：请求三次
              method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
      		/* 3 执行 HTTP GET 请求 */
      		
      			int statusCode = client.executeMethod(method);
      			if (statusCode != HttpStatus.SC_OK) {
      				throw new HttpException();
      			}
      			sum=0;
      			count=0;
      			System.out.println(method.getResponseBodyAsString());
      			
            client.executeMethod(method);
            
          
    			
            
            //15674975284
            //System.out.println(method.getStatusLine());   //打印结果页面
             String response=new String(method.getResponseBodyAsString());
 
//           System.out.println(response);
             //释放连接
             method.releaseConnection();

             //解析页面内容
             Document doc= Jsoup.parse(response);   //从字符串中加载
             //直接从URL 中加载页面信息。timeout设置连接超时时间 post提交方式 或者get()
//           Document document = (Document) Jsoup.connect("http://haoma.imobile.com.cn/index.php?mob=18710115102").timeout(3000).post();

            //Elements  是 Element 的集合类
             Elements element=doc.select("table");  //从加载的信息中查找table 标签

           //从查找到table属性的Elements集合中获取标签 tr 或者tr[class$=alt] 表示 tr标签内class属性=alt
//           Elements titleName=element.select("tr[class$=alt]");   
             Elements titleName=element.select("tr");
             for(Element name : titleName){
                System.out.println(name.text());
            }
        } catch (HttpException e) {
        	if(sum<=5){
        		if(count<=3){
            		System.out.println("http异常。。。。。网络故障，再次发起链接");
            		count++;
            		sum++;
            		getDoc(ip, port);
            		
            	}else{
            		//从本地读取ip
            		System.out.println("http异常。。。。。。。IP故障，读取最新ip");
            		count=0;
            		JSONObject json=GetProxyIpUtil.readOneProxyIP();
            		String ip2=json.getString("ip");
            		int port2=json.getInt("port");
            		sum++;
            		System.out.println("从本地读取ip:"+ip2);
            		getDoc(ip2,port2);
            		
            	}
        	
        	}else if(sum==7){
        		System.out.println("放弃本次操作");
        		return;
        	}else{
        		//重新抓取最新的代理ip
        		System.out.println("爬虫重新抓取最新ip");
        		GetProxyIpUtil.getProxyIps("http://www.xici.net.co/");
        		JSONObject json=GetProxyIpUtil.readOneProxyIP();
        		String ip2=json.getString("ip");
        		int port2=json.getInt("port");
        		sum--;
        		getDoc(ip2,port2);
        	}
        	
            e.printStackTrace();
        } catch (IOException e) {
        	//发生网络异常，例如代理ip地址失效等等。
        	if(sum<=5){
        		if(count<=3){
            		System.out.println("io异常。。。。。网络故障，再次发起链接");
            		count++;
            		sum++;
            		getDoc(ip, port);
            		
            	}else{
            		//从本地读取ip
            		System.out.println("io异常。。。。IP故障，读取最新ip");
            		count=0;
            		JSONObject json=GetProxyIpUtil.readOneProxyIP();
            		String ip2=json.getString("ip");
            		int port2=json.getInt("port");
            		sum++;
            		System.out.println("从本地读取ip:"+ip2);
            		getDoc(ip2,port2);
            		
            	}
        		 
        	}else if(sum==7){
        		System.out.println("放弃本次操作");
        		return;
        	}else{
        		//重新抓取最新的代理ip
        		System.out.println("爬虫重新抓取最新ip");
        		GetProxyIpUtil.getProxyIps("http://www.xici.net.co/");
        		JSONObject json=GetProxyIpUtil.readOneProxyIP();
        		String ip2=json.getString("ip");
        		int port2=json.getInt("port");
        		sum--;
        		getDoc(ip2,port2);
        	}
           // e.printStackTrace();
        }
    }
	private static HttpMethod getPostMethod(String phone){
        PostMethod post = new PostMethod( "/index.php" );
        //POST提交则需要通过NameValuePair类来设置参数名和对应的值
        NameValuePair simcard = new NameValuePair( "mob" ,phone);
        post.setRequestBody( new NameValuePair[] {simcard});
        return post; 
  } 
	
	/** 
     * 使用 GET 方式提交数据 
     *@return 
     */

    private static HttpMethod getGetMethod(){
       return new GetMethod("/default.aspx");
    }

}
