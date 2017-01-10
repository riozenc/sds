<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">

$(function() {
		
	$('#order_datagrid').datagrid({
	    height: '100%',
	    tableWidth:'99.5%',
	    gridTitle : ' ',
	    local: 'remote',
	    showToolbar: true,
	    toolbarItem: 'del',
	    dataUrl:"order.do?type=findOrder",
	    columns: [
	         {
	             name: 'orderId',
	             label: '订单号',
	             align: 'center',
	             width: 130
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
	        },
	       
	        {
	            name: 'id',
	            label: '操 作',
	            align: 'center',
	            render: function(value) {
	            	return '<button type="button" class="btn-blue btn" data-icon="edit" onclick="dialog_user('+value+');">详情</button>';
	            }
	        }
	    ],
	    
	    delUrl:'order.do?type=delete',
	    delPK:'id',
	    paging:{pageSize:5,selectPageSize:'5,10,20'},
	    linenumberAll: true
	})
});
function dialog_user(id) {
	var ajaxdata={id:id} ;
    var ajaxCallUrl="user.do?type=findUserByWhere";
	$.ajax({
		cache : false,
		type : "POST",
		url : ajaxCallUrl,
		data : ajaxdata,
		dataType : "json",
		error : function(request) {
			alert("Connection error");
			return false;
		},
		success : function(data) {
			BJUI.dialog({
			    id:'userEdit',
			    url:'html/form/userEdit.jsp',
			    title:'详情',
			    width:900,
			    height:500,
			    onLoad:function(){
			    	$.each(data, function(key, obj) {
			    		$("#"+key).val(obj);
					});
			    }
			});
			return false;	
		}
	});
    
}
</script>
<div class="bjui-pageHeader" style="background-color:#fefefe; border-bottom:none;">
<form data-toggle="ajaxsearch" data-options="{searchDatagrid:$.CurrentNavtab.find('#order_datagrid')}">
    <fieldset>
        <legend style="font-weight:normal;">页头搜索：</legend>
        <div style="margin:0; padding:1px 5px 5px;">
            <span>登录手机号：</span>
            <input type="text" name="account" class="form-control" size="15">
                
            <span>交易时间：</span>
            <input type="text" name="date" class="form-control" data-toggle="datepicker" placeholder="点击选择日期" >
                
            <div class="btn-group">
                <button type="submit" class="btn-green" data-icon="search">开始搜索！</button>
                <button type="reset" class="btn-orange" data-icon="times">重置</button>
            </div>
        </div>
    </fieldset>
</form>
</div>
<table id="order_datagrid" class="table table-bordered">
</table>