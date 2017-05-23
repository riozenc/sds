<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">

$(function() {
	$('#order_datagrid').datagrid({
	    height: '95%',
	    tableWidth:'99.5%',
	    gridTitle : ' ',
	    local: 'remote',
	    toolbarItem: 'del',
	    showToolbar: false,
	    filterThead:false,
	    columnMenu:false,
	    fieldSortable:false,
	    dataUrl:"user.do?type=getAllUser",
	    columns: [
	         {
	        	name:'fullName',
	        	label:'公司名称',
	        	align:'center',
	        	width:90
	         },
	        {
	            name: 'account',
	            label: '商户手机号',
	            align: 'center',
	            width:70
	        },
	       {
	        	name:'jurisdiction',
	        	label:'权限管理',
	        	align:'center',
	        	width:70,
	            render: function(value,data) {
	            		return '<button type="button" class="btn-blue btn" data-icon="edit" onclick="dialog_merchant('+data.id+');">修改权限</button>';
	            	}
	            }
	    ],
	    paging:{pageSize:20,selectPageSize:'20,30,40'},
	    showLinenumber: false,
	    inlineEditMult: false
	});
});
function dialog_merchant(id) {
	var userId = id;
	console.log(id);
	$.ajax({
		cache : false,
		type : "POST",
		url : "role.do?type=findRoleByWhere",
		data : {userId:userId},
		dataType : "json",
		error : function(request) {
			alert("Connection error");
			return false;
		},
		success : function(data) {
			BJUI.dialog({
			    id:'check',
			    url:'html/form/checkJurisdiction.jsp?userId='+userId,
			    title:'权限修改',
			    width:500,
			    height:400,
			    onLoad:function(){
					var rank ="";
					for(var i=0;i<data.length;i++){
						console.log(data)
								$("#status").append('<div class="row-input" ><input name="rank" id="'+data[i].roleId+'" type="checkbox" value="'+data[i].id+'"/>'+data[i].roleName+'</div>');
								console.log(data[i].roleName);
								if(data[i].isCheck>0){
									$("#"+data[i].roleId+"").attr("checked","true");//选中    
						}
					}
			    }
			});
			return false;	
		}
	});
}
</script>
<div class="bjui-pageHeader" style="background-color:#fefefe; border-bottom:none;">
<form data-toggle="ajaxsearch" data-options="{searchDatagrid:$.CurrentNavtab.find('#order_datagrid')}">
</form>
</div>
<div class="bjui-pageContent">
	<table id="order_datagrid" class="table table-bordered">
	</table>
</div>