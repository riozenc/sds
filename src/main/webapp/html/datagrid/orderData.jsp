<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">

$(function() {
		
	$('#order_datagrid').datagrid({
	    height: '100%',
	    tableWidth:'99.5%',
	    gridTitle : ' ',
	    local: 'remote',
	    showToolbar: false,
	    toolbarItem: 'del',
	    dataUrl:"order.do?type=findOrder",
	    columns: [
	         {
	             name: 'orderId',
	             label: '订单号',
	             align: 'center',
	             width: 130,
	             render: function(value) {
		            	return '<a href="javascript:;"   onclick="dialog_profit(\''+value+'\')">'+value+'</a>';
		            }
	         },
	        {
	            name: 'account',
	            label: '登录手机号',
	            align: 'center',
	            width:100
	        },
	        {
	            name: 'channelCode',
	            label: '交易通道',
	            align: 'center',
	            width: 100
	        },
	        {
	            name: 'amount',
	            label: '交易金额',
	            align: 'center',
	            width: 50
	        },
	        {
	            name: 'date',
	            label: '交易时间',
	            align: 'center',
	            width:105
	        },
	        {
	            name: 'status',
	            label: '状态',
	            align: 'center',
	            width: 70,
	            render: function(value) {
	            	if(value == 0){
	            		return "未查询";
	            	}else if(value == 1){
	            		return "成功";
	            	}else if(value == 2){
	            		return "失败";
	            	}else{
	            		return value;
	            	}
	                
	            }
	        }
	    ],
	    
	    delUrl:'order.do?type=delete',
	    delPK:'id',
	    paging:{pageSize:5,selectPageSize:'10,20,30'},
	    showLinenumber: false,
	    inlineEditMult: false
	});
	
	$("#profit").bind("click",function(){
		$.ajax({
			cache : false,
			type : "POST",
			url : "profit.do?type=profit",
			error : function(request) {
				alert("刷新成功");
				
				return false;
			},
			success : function(data) {
				
			}
		});
	});
	
});
function dialog_profit(orderId){
	
	var ajaxdata={orderId:orderId} ;
	$.ajax({
		cache : false,
		type : "POST",
		url : "profit.do?type=getProfit",
		data : ajaxdata,
		dataType : "json",
		error : function(request) {
			alert("Connection error");
			return false;
		},
		success : function(data) {
			console.info(data);
			if(data.totalRow>0){
				BJUI.dialog({
				    id:'dialogProfit',
				    url:'html/form/profitInfo.jsp',
				    title:'详情',
				    width:900,
				    height:450,
				    onLoad:function(){
				    	$.each(data.list[0], function(key, obj) {
				    		$("#"+key).val(obj);
						});
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
                <button type="submit" class="btn-green" data-icon="search">开始搜索</button>
                <button type="reset" class="btn-orange" data-icon="times">重置</button>
                <button type="reset" class="btn btn-orange" id="profit" data-icon="refresh">分润计算</button>
            </div>
           
        </div>
    </fieldset>
</form>
</div>
<div class="bjui-pageContent">
	<table id="order_datagrid" class="table table-bordered">
	</table>
</div>