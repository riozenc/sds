<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
function onchangePass(){
	var password = $("#j_userinfo_changepass_newpass").val();
	var confirmpass = $("#confirmpass").val();
	
	if(password == confirmpass){
		$.ajax({
			cache : false,
			type : "POST",
			url : "<%=request.getContextPath()+"/" %>user.do?type=changepassword",
			dataType : "json",
			data:{password:password},
			error : function(request) {
				console.info(request);
				
				return false;
			},
			success : function(data) {
				console.info(data);
				
				BJUI.alertmsg('ok',data.message);
			}
		});
	}
	
	
}

</script>
<div class="bjui-pageContent">
    <form >
        <div class="bjui-row col-1">
            <label class="row-label">新密码:</label>
            <div class="row-input required">
                <input type="password" id="j_userinfo_changepass_newpass" name="password" value="" data-rule="新密码:required;length(6~)">
            </div> 
            <label class="row-label">确认密码:</label>
            <div class="row-input required">
                <input type="password" id="confirmpass" name="confirmpass" value="" data-rule="required;match(#j_userinfo_changepass_newpass)">
            </div>
        </div>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">关闭</button></li>
        <li><button type="button" class="btn-default" data-icon="check" onclick="onchangePass()">确认修改</button></li>
    </ul>
</div>