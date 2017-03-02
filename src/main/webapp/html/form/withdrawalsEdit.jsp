<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
function agree(){
	BJUI.ajax('ajaxform', {
	    url: 'withdrawals.do?type=agree',
	    form: $('#j_withdrawalsEdit_form'),
	    validate: true,
	    loadingmask: true,
	    okCallback: function(json, options) {
	    	console.info(options);
	    	BJUI.dialog('close', 'withdrawalsEdit');      //关闭
	    }
	})
}
function reject(){
	BJUI.ajax('ajaxform', {
	    url: 'withdrawals.do?type=reject',
	    form: $('#j_withdrawalsEdit_form'),
	    validate: true,
	    loadingmask: true,
	    okCallback: function(json, options) {
	    	console.info(options);
	    	BJUI.dialog('close', 'withdrawalsEdit');      //关闭
	    }
	})
}
</script>
<div class="bjui-pageContent">
    <div class="bs-example">
        <form  id="j_withdrawalsEdit_form" >
       
        <div class="bjui-row col-1">
            <label class="row-label " >ID</label>
            <div class="row-input ">
                <input type="text" id="withdrawals_ID" name="id" value=""   readonly="readonly">
            </div>
            <label class="row-label " >商户账号</label>
            <div class="row-input ">
                <input type="text"   id="withdrawals_merchantId"  name="merchantId"  value="" readonly="readonly">
            </div>
            
            <label class="row-label " >提现金额</label>
            <div class="row-input ">
                <input type="text" id="withdrawals_Amount" value=""  name="amount"  readonly="readonly" >
            </div>
            
            
        </div>
        
        </form>
    </div>
    
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-default" data-icon="close" onclick="reject()">驳回</button></li>
        <li><button type="button" class="btn-default" data-icon="save" onclick="agree()">通过</button></li>
    </ul>
</div>