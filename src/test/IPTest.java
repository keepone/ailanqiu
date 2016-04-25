package test;

import java.util.concurrent.TimeUnit;

import org.jsoup.nodes.Document;

public class IPTest {
	public final static String USERAGENT = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)";

	public static void main(String [] args){
		String host="119.40.36.79";
		String port="18186";
		try {
			Document doc=NetworkAccess.getHttpRequest("http://nba.sports.sina.com.cn/match_result.php", host,Integer.parseInt(port), USERAGENT);
			System.out.println(doc);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 

}
