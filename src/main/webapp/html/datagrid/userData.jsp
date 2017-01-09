<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">

$(function() {
		
	$('#user_datagrid').datagrid({
	    height: '100%',
	    tableWidth:'99.5%',
	    gridTitle : ' ',
	    local: 'remote',
	    showToolbar: true,
	    toolbarItem: 'del',
	    dataUrl:"user.do?type=findUserByWhere",
	    columns: [
	         {
	             name: 'account',
	             label: '登录帐号',
	             align: 'center',
	             width: 130
	         },
	        {
	            name: 'fullName',
	            label: '企业名称',
	            align: 'center',
	            width: 200
	        },
	        {
	            name: 'agType',
	            label: '企业类型',
	            align: 'center',
	            width: 100
	        },
	        {
	            name: 'regName',
	            label: '法人姓名',
	            align: 'center',
	            width: 80
	        },
	        {
	            name: 'parentId',
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
	            width:105
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
	    
	    delUrl:'user.do?type=delete',
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
<table id="user_datagrid" class="table table-bordered">
</table>