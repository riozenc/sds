<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">

$(function() {
	$("#profitCountBtn").bind("click",function(){  //分润统计
		$.ajax({
			cache : false,
			type : "POST",
			url : "profit.do?type=profitCount",
			data : $('#j_profitCount_form').serialize(),
			async: false,
			error : function(request) {
				alert("Connection error");
				return false;
			},
			success : function(data) {
				BJUI.alertmsg('ok', '统计成功'+data);
				return false;
			}
		});
		
	});
});

</script>
<div class="bjui-pageHeader" style="background-color:#fefefe; border-bottom:none;">
<form id="j_profitCount_form" >
    <fieldset>
        <legend style="font-weight:normal;">分润统计计算：</legend>
        <div style="margin:0; padding:1px 5px 5px;">
                 
            <span>交易时间：</span>
            <input type="text" name="orderDate" class="form-control" data-toggle="datepicker" placeholder="点击选择日期" >
                
            <div class="btn-group">
                <button id="profitCountBtn" type="button"class="btn btn-blue" data-icon="search">统计计算</button>
                <button type="reset" class="btn-orange" data-icon="times">重置</button>
                
            </div>
           
        </div>
    </fieldset>
</form>
</div>
<div class="bjui-pageContent">
	
</div>