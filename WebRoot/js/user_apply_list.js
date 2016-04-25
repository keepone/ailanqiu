
$(document).ready(function(){
  $(".click").click(function(){
  $(".tip").fadeIn(200);
  });
  
  $(".tiptop a").click(function(){
  $(".tip").fadeOut(200);
});

  $(".sure").click(function(){
  $(".tip").fadeOut(100);
});

  $(".cancel").click(function(){
  $(".tip").fadeOut(100);
});

});

	
  //首先需要初始化
  var   xmlDoc;   
  var   nodeIndex; 
  function   getxmlDoc()   
  {   
      xmlDoc=new   ActiveXObject("Microsoft.XMLDOM");   
          var   currNode;   
          xmlDoc.async=false;   
          xmlDoc.load("../js/Area.xml");   
          if(xmlDoc.parseError.errorCode!=0)   
          {   
                  var   myErr=xmlDoc.parseError;   
                  alert("出错！"+myErr.reason);   
          }           
  }
  function Init()
  {
    //打开xlmdocm文档
    getxmlDoc();
    var   dropElement1=document.getElementById("Select1"); 
    var   dropElement2=document.getElementById("Select2"); 
    var   dropElement3=document.getElementById("Select3");   
    RemoveDropDownList(dropElement1);
    RemoveDropDownList(dropElement2);
    RemoveDropDownList(dropElement3);
    var  TopnodeList=xmlDoc.selectSingleNode("address").childNodes;
    if(TopnodeList.length>0)
    {
        //省份列表
        var country;
        var province;
        var city;
        for(var   i=0; i<TopnodeList.length;   i++)
        {
              //添加列表项目
              country=TopnodeList[i];       
              var   eOption=document.createElement("option");   
              eOption.value=country.getAttribute("name");
              eOption.text=country.getAttribute("name");
              dropElement1.add(eOption);
        }
        if(TopnodeList[0].childNodes.length>0)
        {
            //城市列表
            for(var i=0;i<TopnodeList[0].childNodes.length;i++)
            {
               var   id=dropElement1.options[0].value;
               //默认为第一个省份的城市
               province=TopnodeList[0]; 
               var   eOption=document.createElement("option");  
               eOption.value=province.childNodes[i].getAttribute("name");   
               eOption.text=province.childNodes[i].getAttribute("name");   
               dropElement2.add(eOption);
            }
            if(TopnodeList[0].childNodes[0].childNodes.length>0)
            {
               //县列表
               for(var i=0;i<TopnodeList[0].childNodes[0].childNodes.length;i++)
               {
                  var   id=dropElement2.options[0].value;
                  //默认为第一个城市的第一个县列表
                  city=TopnodeList[0].childNodes[0];  
                  var   eOption=document.createElement("option");  
                  eOption.value=city.childNodes[i].getAttribute("name");   
                  eOption.text=city.childNodes[i].getAttribute("name");   
                  this.document.getElementById("Select3").add(eOption);
               }
            }
        }
    }
  }   
  function   selectCity()   
  {       var   dropElement1=document.getElementById("Select1"); 
          var   name=dropElement1.options[dropElement1.selectedIndex].value;
          //alert(id);
          var   countryNodes=xmlDoc.selectSingleNode('//address/province [@name="'+name+'"]');   
          //alert(countryNodes.childNodes.length); 
          var   province=document.getElementById("Select2");       
          var   city=document.getElementById("Select3");       
          RemoveDropDownList(province);   
          RemoveDropDownList(city);
          if(countryNodes.childNodes.length>0)
          {
               //填充城市          
               for(var   i=0;   i<countryNodes.childNodes.length;   i++)   
               {   
                  var   provinceNode=countryNodes.childNodes[i];     
                  var   eOption=document.createElement("option");   
                  eOption.value=provinceNode.getAttribute("name");   
                  eOption.text=provinceNode.getAttribute("name");   
                  province.add(eOption);   
               }
               if(countryNodes.childNodes[0].childNodes.length>0)
               {
                  //填充选择省份的第一个城市的县列表
                  for(var i=0;i<countryNodes.childNodes[0].childNodes.length;i++)
                  {
                      //alert("i="+i+"\r\n"+"length="+countryNodes.childNodes[0].childNodes.length+"\r\n");
                      var   dropElement2=document.getElementById("Select2"); 
                      var   dropElement3=document.getElementById("Select3"); 
                      //取当天省份下第一个城市列表
                      var cityNode=countryNodes.childNodes[0];
                      //alert(cityNode.childNodes.length); 
                      var   eOption=document.createElement("option");  
                      eOption.value=cityNode.childNodes[i].getAttribute("name");   
                      eOption.text=cityNode.childNodes[i].getAttribute("name");   
                      dropElement3.add(eOption);
                  }
               }
          }
  }   
  function   selectCountry()   
  {   
          var   dropElement2=document.getElementById("Select2");   
          var   name=dropElement2.options[dropElement2.selectedIndex].value;   
          var   provinceNode=xmlDoc.selectSingleNode('//address/province/city[@name="'+name+'"]');   
          var   city=document.getElementById("Select3");       
          RemoveDropDownList(city);   
          for(var   i=0;   i<provinceNode.childNodes.length;   i++)   
          {   
                  var   cityNode=provinceNode.childNodes[i];     
                  var   eOption=document.createElement("option");   
                  eOption.value=cityNode.getAttribute("name");   
                  eOption.text=cityNode.getAttribute("name");   
                  city.add(eOption);   
          }   
  }   
  function   RemoveDropDownList(obj)   
  {   
      if(obj)
      {
          var   len=obj.options.length;   
          if(len>0)
          {
            //alert(len);   
            for(var   i=len;i>=0;i--)   
            {   
                  obj.remove(i);   
            }
          }
       }
            
  }   
  
  
  
	 function loadXML(xmlpath) {
            var xmlDoc = null;
            if (window.ActiveXObject) {
                xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
            } else if (document.implementation && document.implementation.createDocument) {
                xmlDoc = document.implementation.createDocument("", "", null);
            } else {
                alert('Your browser cannot handle this script.');
            }
            xmlDoc.async = false;
            xmlDoc.load(xmlpath);
            return xmlDoc;
        }
        $(function () {
            var xmlDoc = null;
            xmlDoc = loadXML("js/Ctry/xml/Area.xml");
            var $s1 = $("#SelectProvince");
            var $s2 = $("#SelectCity");
            var $s3 = $("#SelectDistrict");
            var v1 = "省份";
            var v2 = "城市";
            var v3 = "区县";
            var root = $(xmlDoc).find("address")[0];
            $(root).children("province").each(function () {
                appendOptionTo($s1, $(this).attr("name"), $(this).attr("name"), v1);
            });
            $s1.change(function () {
                $s2.html("");
                var province_node = $(root).children("province")[this.selectedIndex];
                $(province_node).children("city").each(function () {
                    appendOptionTo($s2, $(this).attr("name"), $(this).attr("name"), v2);
                });
                $s2.change();
            }).change();
            $s2.change(function () {
                $s3.html("");
                var province_node = $(root).children("province")[$s1[0].selectedIndex];
                var city_node = $(province_node).children("city")[this.selectedIndex];
                $(city_node).children("country").each(function () {
                    appendOptionTo($s3, $(this).attr("name"), $(this).attr("name"), v3)
                });
            }).change();
            function appendOptionTo($o, k, v, d) {
                var $opt = $("<option>").text(k).val(v);
                if (v == d) { $opt.attr("selected", "selected") }
                $opt.appendTo($o);
            }
        });
	 


 function updateValue(userId,phone,selectIndex,paramName){
 	var selectName=paramName+selectIndex;
 var paramValue=document.getElementById(selectName).value;
 if(paramName=='budgetNum'){
 var reg = new RegExp("^[0-9]*$");
	 if (!reg.test(paramValue)){
		alert("请输入数字");
		return false;
	}
 }
 	 
 	var url = "user/UpdateUserApplyAction?time=+"+new Date().getTime()+"?";
 	var mark="1";
    		$.get(url, {mark: mark,userId: userId,phone: phone,paramName: paramName,paramValue: paramValue},function(data){
    			if (data=="ok"){
    				 alert("更新成功^^"); 
    			} else {	
    				 alert("更新失败"); 
    			}
    		});
 }
 

 function replaceSelect(userId,phone){
 
 				document.getElementById("hiddenUserId").value=userId;
 				document.getElementById("hiddenPhone").value=phone;
 				document.getElementById("hiddenAreaSelect").style.display="block";
 				window.scroll(0,0);
 };
 function changeSelectAreaMark(){
 	document.getElementById("SelectAreaMark2").style.display="none";
 	document.getElementById("SelectAreaMark3").style.display="block";
 }
 



 $(function(){
 		$("#submitValue").click(function(){
 		var userId=$("#hiddenUserId").val();
 		var phone=$("#hiddenPhone").val();
 		var province=$("#SelectProvince").val();
 		if(province=='省份'){
 			alert("你还没有选择！");
 			return false;
 		}
 		var city=$("#SelectCity").val();
 		var area=$("#SelectDistrict").val();
 		var mark="2";
 		var url = "user/UpdateUserApplyAction?time=+"+new Date().getTime()+"?";
    		$.get(url, {mark: mark,userId: userId,phone: phone,province: province,city: city,area: area},function(data){
    			if (data=="ok"){
    				 alert("更新成功^^"); 
    				 window.location.href="/user/GetUserApplyByConditionsAction";
    			} else {	
    				 alert("更新失败"); 
    			}
    		}); 	
 		});
 		
 		$("#cancelValue").click(function(){
 	 		document.getElementById("hiddenAreaSelect").style.display="none";
 		});
 });



  	 $(function(){
  	 	$("#toSearch").click(function(){
  	 			$("#clickType").val("查询");
  		 
  	 	});
      });

    function matchCompany(userId,userName,phone,homeType,homeStructure,budgetType,budgetNum,province,city,area,community){
    	 
    	$("#hiddenUid").val(userId);
    	$("#hiddenUserName").val(userName);
    	$("#hiddenPhone").val(phone);
    	$("#hiddenHomeType").val(homeType);
    	$("#hiddenHomeStructure").val(homeStructure);
    	$("#hiddenBudgetType").val(budgetType);
    	$("#hiddenBudgetNum").val(budgetNum);
    	$("#hiddenProvince").val(province);
    	$("#hiddenCity").val(city);
    	$("#hiddenArea").val(area);
    	$("#hiddenCommunity").val(community);
    	document.myFrm3.submit();
    }
 
  
$(function(){
	  	$("#applyTime").click( 
function () { 
	var rule=$("#timeSortRule").val();
	if(rule=="desc"){
		$("#timeSortRule").val("asc");
	}else{
		$("#timeSortRule").val("desc");
	}

	document.myFrm.submit();

} 
);
	
});
 