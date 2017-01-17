<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">

//存储下拉数据
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
		var selectorData=data.list;
		var selector=$('<select  name="agentId" ></select>');  
		selector.append('<option value="0">管理员</option>');  

		for(var i=0;i< selectorData.length;i++){   
		  selector.append('<option value="'+selectorData[i].id+'">'+selectorData[i].fullName+'</option>');  
		}
		$("#parentSel").append(selector);
		alert("该功能需优化");
		
	}
});
function save(){

	BJUI.ajax('ajaxform', {
	    url: 'merchant.do?type=insert',
	    form: $('#j_custom_form'),
	    validate: true,
	    loadingmask: true,
	    okCallback: function(json, options) {
	    	BJUI.navtab('refresh', 'merchantAdd');  //刷新
	    }
	})
}
</script>
<div class="bjui-pageContent">
    <div class="bs-example">
        <form id="j_custom_form" >
        <h4>基 本 信 息</h4>
        <div class="bjui-row col-2">
        	<label class="row-label " >用户类型</label>
            <div class="row-input ">
                <select id="userType"  name="userType">
            		<option value="2" selected>企业用户</option>
            		<option value="1">普通用户</option>
            	</select>
            </div>
            <label class="row-label " >系统账号</label>
            <div class="row-input required">
                <input type="text" name="account" value="" data-rule="required">
            </div>
            <label class="row-label " >系统密码</label>
            <div class="row-input required">
                <input type="text" name="password" value="" data-rule="required">
            </div>
            <label class="row-label">所属上级代理商</label>
            <div class="row-input" id="parentSel">
                
            </div>
            <label class="row-label">真实姓名</label>
            <div class="row-input">
                <input type="text" value="" name="realName" data-rule="required">
            </div>
            <label class="row-label">商户名称</label>
            <div class="row-input">
                <input type="text" value="" name="cmer"  >
            </div>
            <label class="row-label">商户简称</label>
            <div class="row-input">
                <input type="text" value="" name="cmerSort"  >
            </div>
            
            <label class="row-label">身份证号</label>
            <div class="row-input">
                <input type="text" value="" name="certNo" data-rule="required">
            </div>
            
            <label class="row-label">微信费率</label>
            <div class="row-input">
                <input type="text" value="" name="wxRate" data-rule="required">
            </div>
            <label class="row-label">支付宝费率</label>
            <div class="row-input">
                <input type="text" value="" name="aliRate" data-rule="required">
            </div>
            
            <hr>
            <h4>结 算 账 户</h4>
            
            <label class="row-label">开户账号</label>
            <div class="row-input">
                <input type="text" value="" name="cardNo" data-rule="required">
            </div>
            <label class="row-label">开户手机号</label>
            <div class="row-input">
                <input type="text" value="" name="mobile" data-rule="required">
            </div>
            <label class="row-label">开户地</label>
            <div class="row-input">
                <input type="text" value="" name="location" data-rule="required">
            </div>
            
        </div>
        </form>
    </div>
    
    
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">关闭</button></li>
        <li><button onclick="save()"  class="btn-default" data-icon="save">保存</button></li>
    </ul>
</div>