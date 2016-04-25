package com.lb.crawl.thread.nba.news.service;
import com.lb.crawl.nba.GetNBACommentFromNBAChina;
import com.lb.crawl.nba.GetNBANewsFromSina;
import com.lb.crawl.nba.GetNBANewsFromZhiBoBa;
import com.lb.crawl.nba.GetNewsFromNBA98;
import com.lucene.crawl.GetPlayerVideoBySina;

public class NBACommentFromNBAChinaThread extends Thread {

	@Override
	public void run() {
			GetNBACommentFromNBAChina.getSpecial("http://china.nba.com/");
	}

}
