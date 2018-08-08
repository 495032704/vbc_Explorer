<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Block</title>


</head>
<body>
	<a href="index.jsp">get</a>
   <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="main" style="height:400px"></div>
    <!-- ECharts单文件引入 -->
    <script src="echarts/echarts.common.min.js"></script>
   <script>
   require(
		    [
		        'echarts',
		        'echarts/chart/pie'
		    ],
		    function (ec) {
			   // 基于准备好的dom，初始化echarts图表
			   var myChart = echarts.init(document.getElementById('main')); 
			   option = {
					    title : {
					        text: 'VBC区块',
					        subtext: '测试',
					        x:'center'
					    },
					    tooltip : {
					        trigger: 'item',
					        formatter: "{a} <br/>{b} : {c} ({d}%)"
					    },
					    legend: {
					        orient: 'vertical',
					        left: 'left',
					        data: ['总区块量','链接量']
					    },
					    series : [
					        {
					            name: '区块量',
					            type: 'pie',
					            radius : '55%',
					            center: ['50%', '60%'],
					            data:[
					                {value:10, name:'已链接量'},
					                {value:90, name:'总量'}
					            ],
					            itemStyle: {
					                emphasis: {
					                    shadowBlur: 10,
					                    shadowOffsetX: 0,
					                    shadowColor: 'rgba( 0, 0.5)'
					                }
					            }
					        }
					    ]
					};
				// 为echarts对象加载数据 
			   myChart.setOption(option); 
		}
   </script>
   
</body>
</html>