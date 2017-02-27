<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
$.ajax({
	cache : false,
	type : "POST",
	url : "user.do?type=findMeInfo",
	data : null,
	dataType : "json",
	error : function(request) {
		alert("Connection error");
		return false;
	},
	success : function(data) {
		$("#account").html(data.account);
		$("#costArate").html(data.costArate);
		$("#costWrate").html(data.costWrate);
		$("#appCode").html(data.appCode);
		
	}
});

</script>
<div class="bjui-pageContent">
    <div class="bs-example">
    <table class="table table-bordered table-striped table-hover">
    <tbody>
    <tr>
    	<td>您的账号: <span id="account"></span></td>
    </tr>
    <tr>
    	<td>推荐码: <span id="appCode"></span></td>
    </tr>
    <tr>
    	<td>支付宝费率: <span id="costArate"></span></td>
    </tr>
    <tr>
    	<td>微信费率: <span id="costWrate"></span></td>
    </tr>
    </tbody>
    </table>
    </div>
      
    
</div>
