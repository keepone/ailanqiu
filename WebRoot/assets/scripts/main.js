window.onload=function(){

	function $(id){
		return document.getElementById(id)
	}
	
	var menu=$("topTags").getElementsByTagName("ul")[0];//�����˵�����
	var tags=menu.getElementsByTagName("li");//�����˵�
	var ck=$("leftMenu").getElementsByTagName("ul")[0].getElementsByTagName("li");//���˵�
	var contentDivs=jQuery(".content");
	var j;
//������˵������±�ǩ
	for(i=0;i<ck.length;i++){
		ck[i].onclick=function(){
			$("welcome").style.display="none"//��ӭ��������
			clearMenu();
			this.style.background='url(/assets/images/tabbg02.gif)'
			//ѭ��ȡ�õ�ǰ����
			for(j=0;j<contentDivs.length;j++){
				if(this==ck[j]){
					if($("p"+contentDivs[j].id)==null){
						openNew(contentDivs[j].id,this.innerHTML);//���ñ�ǩ��ʾ����
					 }
					clearStyle();
					$("p"+contentDivs[j].id).style.background='url(/assets/images/tabbg1.gif)';
					clearContent();
					$(""+contentDivs[j].id).style.display="block";
				}
			 }
			return false;
		  }
	 }
	//���ӻ�ɾ����ǩ
	function openNew(id,name){
		var tagMenu=document.createElement("li");
		tagMenu.id="p"+id;
		tagMenu.innerHTML=name+"&nbsp;&nbsp;"+"<img src='/assets/images/off.gif' style='vertical-align:middle'/>";
		var url = jQuery("#m_"+id).attr("url");
		jQuery.get(url,function(data){
			jQuery("#"+id).html(data);
  		});
		//��ǩ����¼�
		tagMenu.onclick=function(evt){
			clearMenu();
			$("m_"+id).style.background='url(/assets/images/tabbg02.gif)'
			clearStyle();
			tagMenu.style.background='url(/assets/images/tabbg1.gif)';
			clearContent();
			$(""+id).style.display="block";
		}
		//��ǩ�ڹر�ͼƬ����¼�
		tagMenu.getElementsByTagName("img")[0].onclick=function(evt){
			evt=(evt)?evt:((window.event)?window.event:null);
			if(evt.stopPropagation){evt.stopPropagation()} //ȡ��opera��Safarið����Ϊ;
				this.parentNode.parentNode.removeChild(tagMenu);//ɾ����ǰ��ǩ
				var color=tagMenu.style.backgroundColor;
				//��������ر�һ����ǩʱ�������һ����ǩ�õ�����
				if(color=="#ffff00"||color=="yellow"){//�������������ɫ����
					if(tags.length-1>=0){
						clearStyle();
						tags[tags.length-1].style.background='url(/assets/images/tabbg1.gif)';
						clearContent();
						var cc=tags[tags.length-1].id.split("p");
						$(""+cc[1]).style.display="block";
						clearMenu();
						ck[cc[1]].style.background='url(/assets/images/tabbg1.gif)';
					 }
				else{
					clearContent();
					clearMenu();
					$("welcome").style.display="block"
				}
			}
		}
		menu.appendChild(tagMenu);
	}
	//����˵���ʽ
	function clearMenu(){
		for(i=0;i<ck.length;i++){
			ck[i].style.background='url(/assets/images/tabbg01.gif)';
		 }
	}
	//�����ǩ��ʽ
	function clearStyle(){
		for(i=0;i<tags.length;i++){
			menu.getElementsByTagName("li")[i].style.background='url(/assets/images/tabbg2.gif)';
		}
	}
	//�������
	function clearContent(){
		for(i=0;i<contentDivs.length;i++){
			$(""+contentDivs[i].id).style.display="none";
		 }
	}
}