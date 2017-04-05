<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">

$(function() {
		
	$('#profitMerchant_datagrid').datagrid({
	    height: '100%',
	    tableWidth:'99%',
	    gridTitle : ' ',
	    local: 'remote',
	    showToolbar: false,
	    filterThead:false,
	    toolbarItem: 'del',
	    dataUrl:"balance.do?type=getBalanceLogByUser",
	    columns: [
	        {
	            name: 'account',
	            label: '商户手机号',
	            align: 'center',
	            width:70
	        },
	        {
	            name: 'realName',
	            label: '商户名称',
	            align: 'center',
	            width: 50
	        },
	        {
	            name: 'orderId',
	            label: '订单号',
	            align: 'center',
	            width: 125
	        },
	        {
	        	name:'amount',
	        	label:'金额',
	        	align:'center',
	        	width:50
	        	
	        },
	        {
	        	name:'balance',
	        	label:'返佣金额',
	        	align:'center',
	        	width:50
	        },
	        {
	        	name:'tjAccount',
	        	label:'推荐人手机号',
	        	align:'center',
	            render: function(value) {
	            	for(var i=0;i<value.length;i++){
	            		if(value[i].tjAccount == null){
	            			return '无推荐人';
	            		}
	            		return value[i].tjAccount;
	            	}
                },
	        	width:70
	        },
	        {
	        	name:'tjName',
	        	label:'推荐人',
	        	align:'center',
	            render: function(value) {
	            	for(var i=0;i<value.length;i++){
	            		if(value[i].tjAccount == null){
	            			return '无推荐人';
	            		}
	            		return value[i].tjAccount;
	            	}
                },
	        	width:50
	        },
	        {
	            name: 'createDate',
	            label: '返佣时间',
	            align: 'center',
	            width:125
	        },
	    ],
	    
	    paging:{pageSize:5,selectPageSize:'10,20,30'},
	    showLinenumber: false,
	    inlineEditMult: false
	});
	
});
function dialog_profitm(Id){
	var ajaxdata={id:Id} ;
	$.ajax({
		cache : false,
		type : "POST",
		url : "profitMerchant.do?type=findProfitMerchantByWhere",
		data : ajaxdata,
		dataType : "json",
		error : function(request) {
			console.info(request);
			alert("请先登录");
			return false;
		},
		success : function(data) {
			console.info(data);
			BJUI.dialog({
			    id:'profitMerchantEdit',
			    url:'html/form/profitMerchantEdit.jsp',
			    title:'详情',
			    width:900,
			    height:500,
			    onLoad:function(){
			    	
			    	$.each(data.list[0], function(key, obj) {
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
<form data-toggle="ajaxsearch" data-options="{searchDatagrid:$.CurrentNavtab.find('#profitMerchant_datagrid')}">
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
            </div>
        </div>
    </fieldset>
</form>
</div>
<div class="bjui-pageContent">
	<table id="profitMerchant_datagrid" class="table table-bordered">
	</table>
</div>