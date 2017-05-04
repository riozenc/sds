<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">

$(function() {
	$('#order_datagrid').datagrid({
	    height: '95%',
	    tableWidth:'99.5%',
	    gridTitle : ' ',
	    local: 'remote',
	    showToolbar: false,
	    toolbarItem: 'del',
	    filterThead:false,
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
	var id = id;
	console.log(id);
	$.ajax({
		cache : false,
		type : "POST",
		url : "role.do?type=findRoleByWhere",
		data : {id:id},
		dataType : "json",
		error : function(request) {
			alert("Connection error");
			return false;
		},
		success : function(data) {
			BJUI.dialog({
			    id:'check',
			    url:'html/form/checkJurisdiction.jsp?id='+id,
			    title:'权限修改',
			    width:500,
			    height:400,
			    onLoad:function(){
					var rank ="";
					for(var i=0;i<data.length;i++){
								$("#status").append('<div class="row-input" ><input name="rank" id="'+data[i].roleId+'" type="checkbox" value="'+data[i].roleId+'"/>'+data[i].roleName+'</div>');
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
    <fieldset>
        <legend style="font-weight:normal;">搜索：</legend>
        <div style="margin:0; padding:1px 5px 5px;">
            <span>登录手机号：</span>
            <input type="text" name="account" class="form-control" size="15">
                
            <span>交易时间：</span>
            <input type="text" name="startDate" class="form-control" data-toggle="datepicker" placeholder="点击选择日期" >
            <input type="text" name="endDate" class="form-control" data-toggle="datepicker" placeholder="点击选择日期" >
                
            <div class="btn-group">
                <button type="submit" class="btn-blue" data-icon="search">开始搜索</button>
                <button type="reset" class="btn-orange" data-icon="times">重置</button>
            </div>
           
        </div>
    </fieldset>
</form>
</div>
<div class="bjui-pageContent">
	<table id="order_datagrid" class="table table-bordered">
	</table>
</div>