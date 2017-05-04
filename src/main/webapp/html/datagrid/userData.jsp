<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
var selectorData=null; //存储下拉数据
$.ajax({
	cache : false,
	type : "POST",
	url : "user.do?type=findUserByWhere",
	data : null,
	dataType : "json",
	error : function(request) {
		alert("Connection error");
		return false;
	},
	success : function(data) {
		selectorData=data.list;
	}
});
$(function() {
		
	$('#user_datagrid').datagrid({
		height: '95%',
	    tableWidth:'99.5%',
	    gridTitle : ' ',
	    local: 'remote',
	    showToolbar: false,
	    filterThead:false,
	    columnMenu:false,
	    fieldSortable:false,
	    toolbarItem: 'del',
	    dataUrl:"user.do?type=findUserByWhere",
	    delUrl:'user.do?type=delete',
	    delPK:'id',
	    columns: [
	         {
	             name: 'account',
	             label: '登录帐号',
	             align: 'center',
	             width: 55
	         },
	        {
	            name: 'fullName',
	            label: '企业名称',
	            align: 'center',
	            width: 160
	        },
	        {
	            name: 'agType',
	            label: '企业类型',
	            align: 'center',
	            width: 45
	        },
	        {
	            name: 'regName',
	            label: '法人姓名',
	            align: 'center',
	            width: 30
	        },
	        {
	            name: 'parentName',
	            label: '上级名称',
	            align: 'center',
	            width:160
	        },
	        {
	            name: 'createDate',
	            label: '注册时间',
	            align: 'center',
	            type:'date',
	            pattern:'yyyy-MM-dd',
	            render: function(value) {
                    return value ? value.substr(0, 10) : value
                },
	            width:50
	        },
	        {
	            name: 'id',
	            label: '操 作',
	            align: 'center',
	            width:35,
	            render: function(value) {
	            	return '<button type="button" class="btn-blue btn" data-icon="edit" onclick="dialog_user('+value+');">详情</button>';
	            }
	        }
	    ],
	    paging:{pageSize:20,selectPageSize:'20,30,40'},
	    showLinenumber: false,
	    inlineEditMult: false
	})
});

function dialog_user(id) {
    
	$.ajax({
		cache : false,
		type : "POST",
		url : "user.do?type=findUserByKey",
		data : {id:id} ,
		dataType : "json",
		error : function(request) {
			alert("Connection Error");
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
			    	var selector=$('<select id="parentId" name="parentId" ></select>');  
			    	//selector.append('<option value="0">管理员</option>');  
					
			    	for(var i=0;i< selectorData.length;i++){
					  selector.append('<option value="'+selectorData[i].id+'">'+selectorData[i].fullName+'</option>');  
					     };
					console.info(selector);
					$("#parentSel").append(selector);
			    	$.each(data, function(key, obj) {
			    		$("#"+key).val(obj);
					});
			    }
			});
		}
	});
    
}
</script>
<table id="user_datagrid" class="table table-bordered">
</table>