package com.lb.utils;
/**
 * 
 * ��ţ����ͼƬ����
�ü����в��֣��ȱ���С����200x200����ͼ��
http://developer.qiniu.com/resource/gogopher.jpg?imageView2/1/w/200/h/200

��ȹ̶�Ϊ200px���߶ȵȱ���С������200x133����ͼ��
http://developer.qiniu.com/resource/gogopher.jpg?imageView2/2/w/200

�߶ȹ̶�Ϊ200px����ȵȱ���С������300x200����ͼ��
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

	
	public static String LIST_IMG_SIZE="?imageView2/2/w/376"; //�б�ͼƬ�ߴ�
	
	
	public static String NEWS_DETAIL_SIZE="?imageView2/2/w/500";
	/**
	 * ͨ����ţѹ���б����������б���Ƶ�б�ȣ�ͼƬ��ֻ�����ţ��ͼƬ��
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
