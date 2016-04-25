package com.lb.utils;
/**
 * 
 * 七牛基本图片处理
裁剪正中部分，等比缩小生成200x200缩略图：
http://developer.qiniu.com/resource/gogopher.jpg?imageView2/1/w/200/h/200

宽度固定为200px，高度等比缩小，生成200x133缩略图：
http://developer.qiniu.com/resource/gogopher.jpg?imageView2/2/w/200

高度固定为200px，宽度等比缩小，生成300x200缩略图：
http://developer.qiniu.com/resource/gogopher.jpg?imageView2/2/h/200
 * @author Allen
 *
 */
public class ImageUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
	public static String LIST_IMG_SIZE="?imageView2/2/w/376"; //列表图片尺寸
	
	
	public static String NEWS_DETAIL_SIZE="?imageView2/2/w/500";
	/**
	 * 通过七牛压缩列表（例如新闻列表，视频列表等）图片（只针对七牛的图片）
	 * @param imgUrl
	 * @return
	 */
	public static String compressListImgByQiNiu(String imgUrl){
		if(imgUrl.indexOf("sina")==-1){
			imgUrl=imgUrl+LIST_IMG_SIZE;
		}
		return imgUrl;
	}
}
