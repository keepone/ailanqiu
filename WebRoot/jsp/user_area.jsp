<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>ext--报表</title>
	
    <link rel="stylesheet" href="resources/css/ext-all.css" type="text/css"></link>
      <script type="text/javascript" src="js/bootstrap.js"></script>
  	 <style type="text/css">
  	  div{height:30px;font-size: 30px;font-weight: bold;}
  	 </style>
  	<script type="text/javascript">
		function test(chartName){
				Ext.onReady(function(){
			  Ext.define('Person', {
        extend: 'Ext.data.Model',
        fields: ['province', 'proCount']
        
    });
					
			 var store = Ext.create('Ext.data.JsonStore', {
    model: 'Person',
     proxy: {
            type: 'ajax',
            url: 'chart/GetChart',
            reader: {
                type: 'json',
                root: 'data'
            }
        }
   
});
		/*
		 var store = Ext.create('Ext.data.JsonStore', {
   fields: ['province', 'proCount'],
     proxy: {
            type: 'ajax',
            url: 'chart/GetChart',
            reader: {
                type: 'json',
                root: 'data'
            }
        }
   
});*/
store.load();
Ext.create('Ext.chart.Chart', {
    renderTo: Ext.getBody(),
    width: 500,
    height: 300,
    animate: true,
    store: store,
    axes: [
        {
            type: 'Numeric',
            position: 'left',
            fields: ['proCount'],
            label: {
                renderer: Ext.util.Format.numberRenderer('0,0')
            },
            title: '用户数',
            grid: true,
            maximum:20,
            minimum: 0
        },
        {
            type: 'Category',
            position: 'bottom',
            fields: ['province'],
            title: '地区'
        }
    ],
    series: [
        {
            type: chartName,
            highlight: {
                size: 7,
                radius: 7
            },
            style: {
            colors: ['#FFFF00','#FFCC00','#FF9900','#FF6600','#FF3300','#FF0000','#660000','#663300']
         },
            tips: {  

                   trackMouse: true,  
				    
                   width: 140,  

                   height: 30,  

                    renderer: function(store, item) {  

                     this.setTitle(store.get('province') + '<br/>' + store.get('proCount'));  

                    }  

                  },
             label: {  

                    display: 'insideEnd',  

                    'text-anchor': 'middle',  

                      field: 'proCount',  

                     renderer: Ext.util.Format.numberRenderer('0'),  

                      orientation: 'horizontal',  

                     color: 'red'  

                  },  
            axis: 'left',
            xField: 'province',
            fill:true,        //填充阴影，默认为false
            yField: 'proCount',
            markerConfig: {
                type: 'cross', //设定数据点的样式，比如圆形，叉
                size: 4,
                radius: 4,
                'stroke-width': 0
            }
        }
    ]
});
		 
		});  	
			
		}
  	</script>
  	<script type="text/javascript">
		function pie(){
			Ext.onReady(function(){
			 	 var store = Ext.create('Ext.data.JsonStore', {
   fields: ['province', 'proCount'],
     proxy: {
            type: 'ajax',
            url: 'chart/GetChart',
            reader: {
                type: 'json',
                root: 'data'
            },
          
        }
   
});

Ext.create('Ext.chart.Chart', {
    renderTo: Ext.getBody(),
    insertPadding:30,
    width: 500,
    height: 350,
    
    animate: true,
    store: store,
    theme: 'Base:gradients',
    series: [{
        type: 'pie',
        angleField: 'proCount',
        showInLegend: true,
        style: {
             
         },
        tips: {
            trackMouse: true,
            width: 140,
            height: 28,
            renderer: function(storeItem, item) {
                // calculate and display percentage on hover
                var total = 0;
                store.each(function(rec) {
                    total += rec.get('proCount');
                });
                this.setTitle(storeItem.get('province') + ': ' + Math.round(storeItem.get('proCount') / total * 100) + '%');
            }
        },
        highlight: {
            segment: {
                margin: 20
            }
        },
        label: {
            field: 'province',
            display: 'rotate',
            contrast: true,
            font: '18px Arial'
            
        }
    }]
});
		});  	
		}
  	</script>
</head>
  
  <body>
    <div onclick="test('line')" style="float: left;width:250px;">【线形图】</div>
    <div onclick="test('column')">【柱状图】</div>
     
    
  </body>
</html>
