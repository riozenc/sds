<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">

$(function() {

	$('#merchant_datagrid').datagrid({
	    height: '100%',
	    tableWidth:'99.5%',
	    gridTitle : ' ',
	    local:'remote',
	    showToolbar: true,
	    toolbarItem: 'del',
	    dataUrl: 'merchant.do?type=findMerchantByWhere',
	    columns: [
	         {
	             name: 'account',
	             label: '帐号',
	             align: 'center',
	             width: 130
	         },
	        {
	            name: 'password',
	            label: '密码',
	            align: 'center',
	            width: 100
	        },
	        {
	            name: 'realName',
	            label: '真实姓名',
	            align: 'center',
	            width: 100
	        },
	        {
	            name: 'cmerSort',
	            label: '商户简称',
	            align: 'center',
	            width: 80
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
	            		return "通过";
	            	}else if(value == 2){
	            		return "禁用";
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
	            render:function(value){return value?value.substr(0,16):value}
	        },
	        {
	            name: 'id',
	            label: '操作',
	            align: 'center',
	            render: function(value) {
	            	return '<button type="button" class="btn-blue btn" data-icon="edit" onclick="dialog_merchant('+value+');">详情</button>';
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
			    id:'editMerchant',
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