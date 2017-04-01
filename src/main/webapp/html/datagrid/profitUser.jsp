<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">

$(function() {
		
	$('#profitUser_datagrid').datagrid({
	    height: '100%',
	    tableWidth:'99%',
	    gridTitle : ' ',
	    local: 'remote',
	    showToolbar: false,
	    toolbarItem: 'del',
	    filterThead:false,
	    dataUrl:"profitUser.do?type=findProfitUserByWhere",
	    columns: [
              {
  	            name: 'id',
  	            label: '代理商名称',
  	            align: 'center',
  	            width:100
  	        },
	        {
	            name: 'account',
	            label: '登录手机号',
	            align: 'center',
	            width:100
	        },
	        {
	            name: 'totalAmount',
	            label: '交易总额',
	            align: 'center',
	            width: 100
	        },
	        {
	            name: 'totalProfit',
	            label: '分润金额',
	            align: 'center',
	            width: 100
	        },
	        {
	            name: 'date',
	            label: '分润时间',
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
	            	
	            		 return '<a href="javascript:;"   onclick="dialog_profit(\''+data.id+'\')">未提现</a>';
	            	}else if(value == 1){
	            		return "已提现";
	            	
	            	}else{
	            		return value;
	            	}
	                
	            }
	        }
	    ],
	    
	    paging:{pageSize:5,selectPageSize:'10,20,30'},
	    showLinenumber: false,
	    inlineEditMult: false
	});
	
});
function dialog_profit(Id){
	var ajaxdata={id:Id} ;
	$.ajax({
		cache : false,
		type : "POST",
		url : "profitUser.do?type=findProfitUserByWhere",
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
			    id:'profitUserEdit',
			    url:'html/form/profitUserEdit.jsp',
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
<form data-toggle="ajaxsearch" data-options="{searchDatagrid:$.CurrentNavtab.find('#profitUser_datagrid')}">
    <fieldset>
        <legend style="font-weight:normal;">搜索：</legend>
        <div style="margin:0; padding:1px 5px 5px;">
            <span>登录手机号：</span>
            <input type="text" name="account" class="form-control" size="15">
                
            <span>交易时间：</span>
            <input type="text" name="startDate" class="form-control" data-toggle="datepicker" placeholder="点击选择日期" >
            <input type="text" name="endDate" class="form-control" data-toggle="datepicker" placeholder="点击选择日期" >
                
            <div class="btn-group">
                <button type="submit" class="btn btn-blue" data-icon="search">开始搜索</button>
                <button type="reset" class="btn-orange" data-icon="times">重置</button>
            </div>
           
        </div>
    </fieldset>
</form>
</div>
<div class="bjui-pageContent">
	<table id="profitUser_datagrid" class="table table-bordered">
	</table>
</div>