<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">

$(function() {

	$('#merchant_datagrid').datagrid({
	    height: '100%',
	    tableWidth:'99.5%',
	    gridTitle : ' ',
	    local:'remote',
	    showToolbar: false,
	    toolbarItem: 'del',
	    dataUrl: 'merchant.do?type=findMerchantByWhere',
	    columns: [
			{
			    name: 'id',
			    label: 'ID',
			    align: 'center',
			    width: 50
			},
	         {
	             name: 'account',
	             label: '登录帐号',
	             align: 'center',
	             width: 130
	         },
	        {
	            name: 'realName',
	            label: '真实姓名',
	            align: 'center',
	            width: 100
	        },
	        {
	            name: 'mobile',
	            label: '绑卡手机号',
	            align: 'center',
	            width: 120
	        },
	        {
	            name: 'cmerSort',
	            label: '商户简称',
	            align: 'center',
	            width: 120
	        },
	        {
	            name: 'agentId',
	            label: '上级名称',
	            align: 'center'
	            
	        },
	        {
	            name: 'status',
	            label: '状态',
	            align: 'center',
	            width: 70,
	            render: function(value) {
	            	if(value == 0){
	            		return "未审核";
	            	}else if(value == 1){
	            		return "验卡通过";
	            	}else if(value == 2){
	            		return "禁用";
	            	}else if(value == 3){
	            		return "审核成功";
	            	}else{
	            		return value;
	            	}
	                
	            }
	        },
	        {
	            name: 'createDate',
	            label: '注册时间',
	            align: 'center',
	            width:105,
	            type:'date',
	            pattern:'yyyy-MM-dd',
	            render:function(value){return value?value.substr(0,20):value}
	        },
	        {
	            name: 'status',
	            label: '操作',
	            align: 'center',
	            width:120,
	            render: function(value,data) {
	            	if( value == 1 && data.wxRate>0 && data.aliRate>0){
	            		return '<button type="button" class="btn-red btn" data-icon="edit" onclick="dialog_verify('+data.id+');">审核</button>';
	            	}else{
	            		return '<button type="button" class="btn-blue btn" data-icon="edit" onclick="dialog_merchant('+data.id+');">修改费率</button>';
	            	}
	            	
	            	
	            }
	        }
	    ],
	    paging:{pageSize:5,selectPageSize:'10,20,30'},
	    delUrl:'merchant.do?type=delete',
	    delPK:'id',
	    showLinenumber: false,
	    inlineEditMult: false
	})
});
function dialog_verify(id) {
	var ajaxdata={id:id} ;
	$.ajax({
		cache : false,
		type : "POST",
		url : "merchant.do?type=findMerchantByWhere",
		data : ajaxdata,
		dataType : "json",
		error : function(request) {
			alert("Connection error");
			return false;
		},
		success : function(data) {
			
			BJUI.dialog({
			    id:'merchantVerify',
			    url:'html/form/merchantVerify.jsp',
			    title:'详情',
			    width:900,
			    height:450,
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
function dialog_merchant(id) {
	var ajaxdata={id:id} ;
	$.ajax({
		cache : false,
		type : "POST",
		url : "merchant.do?type=findMerchantByWhere",
		data : ajaxdata,
		dataType : "json",
		error : function(request) {
			alert("Connection error");
			return false;
		},
		success : function(data) {
			console.info(data);
			BJUI.dialog({
			    id:'merchantEdit',
			    url:'html/form/merchantEdit.jsp',
			    title:'详情',
			    width:1000,
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
<table id="merchant_datagrid" class="table table-bordered">
</table>