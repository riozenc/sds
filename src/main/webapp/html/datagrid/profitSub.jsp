<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">

$(function() {
	$("#download").bind("click",function(){
		var startDate = $("#startDate").val();
		var endDate = $("#endDate").val();

		var form = $("<form>");
		form.attr("style","display:none");
		form.attr("target","");
		form.attr("method","post");
		form.attr("action","profit.do?type=exportExcel");
		var input1 = $("<input>");
		input1.attr("type","hidden");
		input1.attr("name","startDate");
		input1.attr("value",startDate);
		var input2 = $("<input>");
		input2.attr("type","hidden");
		input2.attr("name","endDate");
		input2.attr("value",endDate);
		$("body").append(form);
		form.append(input1);
		form.append(input2);
		form.submit();
		form.remove();
	});
	
	var myDate = new Date();
	var dateNow = myDate.getFullYear()+"-"+ (myDate.getMonth()+1)+"-"+myDate.getDate();
	$('#profitSub_datagrid').datagrid({
	    height: '100%',
	    tableWidth:'99%',
	    gridTitle : ' ',
	    local: 'remote',
	    showToolbar: false,
	    toolbarItem: 'del',
	    filterThead:false,
	    dataUrl:'profitUser.do?type=subProfitUser',
	    postData:{startDate:dateNow,endDate:dateNow},
	    columns: [
              {
  	            name: 'agentId',
  	            label: 'ID',
  	            align: 'center',
  	            width:50
  	        },
			{
				name:'agentName',
				label:'代理商',
				align:'center',
				width:100
			},
			{
				name:'cmer',
				label:'代理人',
				align:'center',
				width:100
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
	            width: 60
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
	            	return '<a href="javascript:;"   onclick="dialog_profitSubInfo(\''+value+'\')">详情</a>';
	            }
	        }
	    ],
	    paging:{pageSize:5,selectPageSize:'10,20,30'},
	    showLinenumber: false,
	    inlineEditMult: false
	});
	
});
//详情
function dialog_profitSubInfo(account){
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
			    id:'profitSubInfo',
			    url:'html/form/profitSubInfo.jsp',
			    title:'详情',
			    width:900,
			    height:500,
			    onLoad:function(){
			    	$('#profitSubInfo_datagrid').datagrid({
			    	    height: '100%',
			    	    tableWidth:'99%',
			    	    gridTitle : ' ',
			    	    local: 'local',
			    	    showToolbar: false,
			    	    filterThead:false,
			    	    data:result,
			    	    columns: [
			               
			    			{
			      	        	name:'fullName',
			      	        	label:'公司',
			      	        	align:'center',
			      	        	width:100
			    			},
			    	        {
			    	            name: 'account',
			    	            label: '账号',
			    	            align: 'center',
			    	            width:100
			    	        },
			    	        {
			    	        	name:'date',
			    	        	label:'交易时间',
			    	        	align:'center',
			    	        	type:'date',
			    	            pattern:'yyyy-MM-dd',
			    	            render: function(value) {
			                        return value ? value.substr(0, 10) : value
			                    },
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
			    	            	return '<a href="javascript:;" onclick="dialog_profitSubInfos('+data.agentId+',\''+data.date+'\')">详情</a>';
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
function dialog_profitSubInfos(agentid,date){
	
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
			    id:'profitSubInfos',
			    url:'html/form/profitSubInfos.jsp',
			    title:'再详情',
			    width:900,
			    height:500,
			    onLoad:function(){
			    	$('#profitSubInfos_datagrid').datagrid({
			    	    height: '100%',
			    	    tableWidth:'99%',
			    	    gridTitle : ' ',
			    	    local: 'local',
			    	    showToolbar: false,
			    	    filterThead:false,
			    	    data:result,			    	    
			    	    columns: [
			                {
			      	            name: 'id',
			      	            label: 'ID',
			      	            align: 'center',
			      	            width:50
			      	        },
			      			{
			      	        	name:'agentName',
			      	        	label:'公司',
			      	        	align:'center',
			      	        	width:100
			    			},
			    	        {
			    	            name: 'account',
			    	            label: '账号',
			    	            align: 'center',
			    	            width:100
			    	        },
			    	       
			    	        {
			    	            name: 'amount',
			    	            label: '交易金额',
			    	            align: 'center',
			    	            width: 100
			    	        },
			    	        {
			    	            name: 'agentProfit',
			    	            label: '分润金额',
			    	            align: 'center',
			    	            width: 100
			    	        },
			    	        {
			    	            name: 'orderDate',
			    	            label: '交易时间',
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

	<form data-toggle="ajaxsearch" data-options="{searchDatagrid:$.CurrentNavtab.find('#profitSub_datagrid')}">
	    <fieldset>
	        <legend style="font-weight:normal;">搜索：</legend>
	        <div style="margin:0; padding:1px 5px 5px;">
	            
	            <span>交易时间：</span>
	            <input type="text" name="startDate" id="startDate" class="form-control" data-toggle="datepicker" placeholder="点击选择日期" >
	            <input type="text" name="endDate" id="endDate" class="form-control" data-toggle="datepicker" placeholder="点击选择日期" >
	                
	            <div class="btn-group">
	                <button type="submit" class="btn btn-blue" data-icon="search" >开始搜索</button>
	                <button type="button" class="btn btn-green" data-icon="download" id="download">导出列表</button>
	            </div>
	           
	        </div>
	    </fieldset>
	</form>
</div>
<div class="bjui-pageContent">
	<table id="profitSub_datagrid" class="table table-bordered">
	</table>
</div>