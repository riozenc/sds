<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">

$(function() {
		
	$('#order_datagrid').datagrid({
	    height: '95%',
	    tableWidth:'99.5%',
	    gridTitle : ' ',
	    local: 'remote',
	    toolbarItem: 'del',
	    showToolbar: false,
	    filterThead:false,
	    columnMenu:false,
	    fieldSortable:false,
	    dataUrl:"order.do?type=getOrderByUser",
	    columns: [
	         {
	             name: 'orderId',
	             label: '订单号',
	             align: 'center',
	             width: 50,
	             render: function(value) {
		            	return '<a href="javascript:;"   onclick="dialog_profit(\''+value+'\')">'+value+'</a>';
		            }
	         },
	         {
	        	name:'cmer',
	        	label:'公司名称',
	        	align:'center',
	        	width:120
	         },
	        {
	            name: 'account',
	            label: '商户手机号',
	            align: 'center',
	            width:35
	        },
	        {
	            name: 'channelCode',
	            label: '交易通道',
	            align: 'center',
	            width: 25,
	            render: function(value) {
	            	if(value == 1){
	            		return "微信";
	            	}else if(value == 2){
	            		return "支付宝";
	            	}else if(value == 3){
	            		return "银联";
	            	}else if(value.indexOf("xx")){
	            		return "柜台码";
	            		return value;
	            	}
	            }
	        },
	        {
	            name: 'amount',
	            label: '交易金额',
	            align: 'center',
	            width: 20
	        },
	        {
	            name: 'date',
	            label: '交易时间',
	            align: 'center',
	            width:30,
	            type:'date',
	            pattern:'yyyy-MM-dd',
	            render: function(value) {
                    return value ? value.substr(0, 10) : value
                }
	        },
	        {
	            name: 'status',
	            label: '状态',
	            align: 'center',
	            width: 25,
	            render: function(value) {
	            	if(value == 0){
	            		return "未支付";
	            	}else if(value == 1){
	            		return "支付成功";
	            	}else if(value == 2){
	            		return "支付失败";
	            	}else if(value == 3){
	            		return "分润完毕";
	            	}else{
	            		return value;
	            	}
	                
	            }
	        }
	    ],
	    
	    delUrl:'order.do?type=delete',
	    delPK:'id',
	    paging:{pageSize:20,selectPageSize:'20,30,40'},
	    showLinenumber: false,
	    inlineEditMult: false
	});
	
	$("#profit").bind("click",function(){
		$.ajax({
			cache : false,
			type : "POST",
			url : "profit.do?type=recalculation",
			error : function(request) {
				alert("刷新成功");
				
				return false;
			},
			success : function(data) {
				return false;
			}
		});
	});
	
});
function dialog_profit(orderId){
	var ajaxdata={orderId:orderId} ;
	$.ajax({
		cache : false,
		type : "POST",
		url : "profit.do?type=getProfitByUser",
		data : ajaxdata,
		dataType : "json",
		error : function(request) {
			console.info(request);
			alert("请先登录");
			return false;
		},
		success : function(data) {
			console.info(data);
			if(data.statusCode == 300){
				alert(data.message);
				return false;
			}
			if(data.totalRow>0){
				BJUI.dialog({
				    id:'dialogProfit',
				    url:'html/form/profitInfo.jsp',
				    title:'详情',
				    width:900,
				    height:450,
				    onLoad:function(){
				    	var html_text ='<table class="table table-bordered table-hover table-striped  " data-height="150">'+
						'<thead><th>代理商手机号</th><th>代理商名称</th><th>代理商所得分润</th><th>推荐人</th><th>推荐人所得分润</th></thead><tbody>';
					
				    	$.each(data.list, function(key, obj) {
				    		console.info(obj.orderId);
				    		if(obj.tjName==null){
				    			obj.tjName="无";
				    		}
				    		html_text = html_text +'<tr><td>'
				    			+obj.agentAccount+'</td><td>'+obj.agentName+'</td><td>'+obj.agentProfit+'</td><td>'+obj.tjName+'</td><td>'+obj.tjProfit+'</td></tr>';
				    		
						});
				    	html_text = html_text
				    	+'<tr></table><div class="bjui-row col-2"><label class="row-label">商户号：</label><div class="row-input">'
				    	+data.list[0].account+'</div><label class="row-label">订单号：</label><div class="row-input">'
				    	+data.list[0].orderId+'</div><label class="row-label">消费金额：</label><div class="row-input">'
				    	+data.list[0].amount+'</div><label class="row-label">交易时间：</label><div class="row-input">'
				    	+data.list[0].orderDate+'</div><label class="row-label">分润总额：</label><div class="row-input">'
				    	+data.list[0].totalProfit+'</div><label class="row-label">微信费率：</label><div class="row-input">'
				    	+data.list[0].wxRate+'</div><label class="row-label">支付宝费率：</label><div class="row-input">'
				    	+data.list[0].aliRate+'</div><label class="row-label">银联费率：</label><div class="row-input">'
				    	+data.list[0].unipayRate+'</div></div>';
				    	$("#table_profitInfo").html(html_text);
				    }
				});
			}else{
				alert("该订单不存在分润");
			}
			
			return false;	
		}
	});
}
</script>
<div class="bjui-pageHeader" style="background-color:#fefefe; border-bottom:none;">
<form data-toggle="ajaxsearch" data-options="{searchDatagrid:$.CurrentNavtab.find('#order_datagrid')}">
    <fieldset>
        <legend style="font-weight:normal;">搜索：</legend>
        <div style="margin:0; padding:1px 5px 5px;">
            <span>登录手机号：</span>
            <input type="text" name="account" class="form-control" size="15">
                
            <span>交易时间：</span>
            <input type="text" name="startDate" class="form-control" data-toggle="datepicker" placeholder="点击选择日期" >
            <input type="text" name="endDate" class="form-control" data-toggle="datepicker" placeholder="点击选择日期" >
                
            <div class="btn-group">
                <button type="submit" class="btn-blue" data-icon="search">开始搜索</button>
                <button type="reset" class="btn-orange" data-icon="times">重置</button>
                <button type="reset" class="btn-green" id="profit" data-icon="refresh">分润重算</button>
            </div>
           
        </div>
    </fieldset>
</form>
</div>
<div class="bjui-pageContent">
	<table id="order_datagrid" class="table table-bordered">
	</table>
</div>