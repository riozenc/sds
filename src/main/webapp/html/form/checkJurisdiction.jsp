<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">


function chk(){ 
	var rank=document.getElementsByName('rank'); //选择所有name="'rank'"的对象，返回数组 
	var userId = <%=request.getParameter("id") %>; //拿到上一个页面传过来的 id值
	//取到对象数组后，循环检测它是不是被选中 
	var s=''; 
	for(var i=0; i<rank.length; i++){
		if(!rank[i].checked){
			s+=0+",";
		}
		if(rank[i].checked){
			s+=rank[i].value+","; //如果选中，将value添加到变量s中 
		}
	} 
	//alert(s==''?'你还没有选择任何内容！':s); 
	$.ajax({
		cache : false,
		type : "POST",
		url :'role.do?type=changeUser',
		dataType : "json",
		data : {rankValue:s,userId:userId},
		error : function(request) {
			console.info(request);
			return false;
		},
		success : function(data) {
			console.info(data);
			BJUI.alertmsg('ok',data.message);
			BJUI.dialog('closeCurrent');
		}
	});
}
</script>

<form  id="rank_form" >
<div class="bjui-pageContent">
    <div id="status"></div>
</div>
</form>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">关闭</button></li>
        <li><button type="button" class="btn-default" data-icon="save" onclick="chk()">保存</button></li>
    </ul>
</div>