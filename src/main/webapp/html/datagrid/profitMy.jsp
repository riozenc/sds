<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">

$(function() {
	var myDate = new Date();
	var dateNow = myDate.getFullYear()+"-"+ (myDate.getMonth()+1)+"-"+myDate.getDate();
	$('#profitMy_datagrid').datagrid({
	    height: '100%',
	    tableWidth:'99%',
	    gridTitle : ' ',
	    local: 'remote',
	    showToolbar: false,
	    toolbarItem: 'del',
	    dataUrl:'profitUser.do?type=myProfitUser',
	    postData:{startDate:dateNow,endDate:dateNow},
	    columns: [
              {
  	            name: 'agentId',
  	            label: 'ID',
  	            align: 'center',
  	            width:50
  	        },
	        {
	            name: 'account',
	            label: '账号',
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
	            name: 'account',
	            label: '详情',
	            align: 'center',
	            width: 50,
	            render:function(value,data){
	            	return '<a href="javascript:;"   onclick="dialog_profitInfo(\''+value+'\')">详情</a>';
	            }
	        }
	    ],
	    paging:{pageSize:5,selectPageSize:'10,20,30'},
	    showLinenumber: false,
	    inlineEditMult: false
	});
	
});
//详情
function dialog_profitInfo(account){
	var startDate=$("#startDate").val();
	var endDate =$("#endDate").val();
	
	var ajaxdata={startDate:startDate,endDate:endDate,account:account};
	$.ajax({
		cache : false,
		type : "POST",
		url : "profitUser.do?type=findDateProfitUserByWhere",
		data : ajaxdata,
		dataType : "json",
		error : function(request) {
			console.info(request);
			alert("请重新登录");
			return false;
		},
		success : function(result) {
			BJUI.dialog({
			    id:'profitMyInfo',
			    url:'html/form/profitMyInfo.jsp',
			    title:'详情',
			    width:900,
			    height:500,
			    onLoad:function(){
			    	$('#profitMyInfo_datagrid').datagrid({
			    	    height: '100%',
			    	    tableWidth:'99%',
			    	    gridTitle : ' ',
			    	    local: 'local',
			    	    showToolbar: false,
			    	    data:result,			    	    
			    	    columns: [
			                {
			      	            name: 'id',
			      	            label: 'ID',
			      	            align: 'center',
			      	            width:50
			      	        },
			    	        {
			    	            name: 'account',
			    	            label: '账号',
			    	            align: 'center',
			    	            width:100
			    	        },
			    	        {
			    	            name: 'date',
			    	            label: '交易日期',
			    	            align: 'center',
			    	            width: 140
			    	        },
			    	        {
			    	            name: 'totalAmount',
			    	            label: '交易金额',
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
			    	            name: 'id',
			    	            label: '详情',
			    	            align: 'center',
			    	            width: 50,
			    	            render:function(value,data){
			    	            	return '<a href="javascript:;" onclick="dialog_profitInfos('+data.agentId+',\''+data.date+'\')">详情</a>';
			    	            }
			    	        }
			    	    ],
			    	    paging:{pageSize:10,selectPageSize:'20,30'},
			    	    showLinenumber: false,
			    	    inlineEditMult: false
			    	});
			    }
			});
			return false;	
		}
	});
}
//再详情
function dialog_profitInfos(agentid,date){
	
	var profitInfos_datas={agentId:agentid,orderDate:date};
	$.ajax({
		cache : false,
		type : "POST",
		url : "profit.do?type=getProfit",
		data : profitInfos_datas,
		dataType : "json",
		error : function(request) {
			console.info(request);
			alert("请重新登录");
			return false;
		},
		success : function(result) {
			console.info(result);
			BJUI.dialog({
			    id:'profitMyInfos',
			    url:'html/form/profitMyInfos.jsp',
			    title:'再详情',
			    width:900,
			    height:500,
			    onLoad:function(){
			    	$('#profitMyInfos_datagrid').datagrid({
			    	    height: '100%',
			    	    tableWidth:'99%',
			    	    gridTitle : ' ',
			    	    local: 'local',
			    	    showToolbar: false,
			    	    data:result,			    	    
			    	    columns: [
			                {
			      	            name: 'id',
			      	            label: 'ID',
			      	            align: 'center',
			      	            width:50
			      	        },
			    	        {
			    	            name: 'account',
			    	            label: '账号',
			    	            align: 'center',
			    	            width:100
			    	        },
			    	       
			    	        {
			    	            name: 'totalAmount',
			    	            label: '交易金额',
			    	            align: 'center',
			    	            width: 100
			    	        },
			    	        {
			    	            name: 'totalProfit',
			    	            label: '分润金额',
			    	            align: 'center',
			    	            width: 100
			    	        }
			    	    ],
			    	    paging:{pageSize:10,selectPageSize:'20,30'},
			    	    showLinenumber: false,
			    	    inlineEditMult: false
			    	});
			    }
			});
			return false;	
		}
	});
}
</script>
<div class="bjui-pageHeader" style="background-color:#fefefe; border-bottom:none;">

	<form data-toggle="ajaxsearch" data-options="{searchDatagrid:$.CurrentNavtab.find('#profitMy_datagrid')}">
	    <fieldset>
	        <legend style="font-weight:normal;">搜索：</legend>
	        <div style="margin:0; padding:1px 5px 5px;">
	            
	            <span>交易时间：</span>
	            <input type="text" name="startDate" id="startDate" class="form-control" data-toggle="datepicker" placeholder="点击选择日期" >
	            <input type="text" name="endDate" id="endDate" class="form-control" data-toggle="datepicker" placeholder="点击选择日期" >
	                
	            <div class="btn-group">
	                <button type="submit" class="btn btn-blue" data-icon="search" >开始搜索</button>
	            </div>
	           
	        </div>
	    </fieldset>
	</form>
</div>
<div class="bjui-pageContent">
	<table id="profitMy_datagrid" class="table table-bordered">
	</table>
</div>