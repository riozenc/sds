<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">

$(function() {
		
	$('#withdrawals_datagrid').datagrid({
	    height: '100%',
	    tableWidth:'99%',
	    gridTitle : ' ',
	    local: 'remote',
	    showToolbar: false,
	    filterThead:false,
	    toolbarItem: 'del',
	    dataUrl:"withdrawals.do?type=findByWhere",
	    columns: [
	        {
	            name: 'account',
	            label: '商户号',
	            align: 'center',
	            width:100
	        },
	        {
	            name: 'amount',
	            label: '提现金额',
	            align: 'center',
	            width: 100
	        },
	       
	        {
	            name: 'date',
	            label: '提现时间',
	            align: 'center',
	            width:125
	        },
	        {
	            name: 'status',
	            label: '状态',
	            align: 'center',
	            width: 70,
	            render: function(value,data) {
	            	if(value == 0){
	            		 return '<a href="javascript:;"   onclick="dialog_withdrawals(\''+data.id+'\',\''+data.merchantId+'\',\''+data.amount+'\',\''+data.account+'\'  ,\''+data.balance+'\')">审核</a>';
	            	}else if(value == 1){
	            		return "已提现";
	            	}else if(value ==2){
	            		return "被驳回";
	            	}
	                
	            }
	        }
	    ],
	    
	    paging:{pageSize:5,selectPageSize:'10,20,30'},
	    showLinenumber: false,
	    inlineEditMult: false
	});
	
});
function dialog_withdrawals(Id,merchantId,Amount,Account,Balance){
	//var ajaxdata={id:Id,merchantId:merchantId,amount:Amount} ;
	BJUI.dialog({
	    id:'withdrawalsEdit',
	    url:'html/form/withdrawalsEdit.jsp',
	    title:'详情',
	    width:900,
	    height:500,
	    onLoad:function(){
	    	$("#withdrawals_ID").val(Id);
	    	$("#withdrawals_merchantId").val(merchantId);
	    	$("#withdrawals_Amount").val(Amount);
	    	$("#withdrawals_Account").val(Account);
	    	$("#withdrawals_Balance").val(Balance);
	    }
	});
	
}
</script>
<div class="bjui-pageHeader" style="background-color:#fefefe; border-bottom:none;">
<form data-toggle="ajaxsearch" data-options="{searchDatagrid:$.CurrentNavtab.find('#withdrawals_datagrid')}">
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
	<table id="withdrawals_datagrid" class="table table-bordered">
	</table>
</div>