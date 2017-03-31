<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
	function agree() { //提现通过
	var Amount=$("#withdrawals_Amount").val();//获取提现金额
	var Balance=$("#withdrawals_Balance").val();//获取余额
		if(Amount>Balance){
		alert("余额不足");
		BJUI.dialog('closeCurrent');
		BJUI.navtab('reload');
			return;
		}
		BJUI.ajax('ajaxform', {
			url : 'withdrawals.do?type=agree',
			form : $('#j_withdrawalsEdit_form'),
			validate : true,
			loadingmask : true,
			okCallback : function(json, options) {
				console.info(options);
				BJUI.dialog('close', 'withdrawalsEdit'); //关闭
				BJUI.navtab('refresh', 'withdrawals_datagrid'); //刷新
			}
		})
		BJUI.dialog('closeCurrent');
	}
	function reject() { //驳回提现
		BJUI.ajax('ajaxform', {
			url : 'withdrawals.do?type=reject',
			form : $('#j_withdrawalsEdit_form'),
			validate : true,
			loadingmask : true,
			okCallback : function(json, options) {
				console.info(options);
				BJUI.dialog('close', 'withdrawalsEdit'); //关闭
				BJUI.navtab('refresh', 'withdrawals_datagrid'); //刷新
			}
		})
		BJUI.dialog('closeCurrent');
		BJUI.navtab('reload');
	}
</script>
<div class="bjui-pageContent">
	<div class="bs-example">
		<form id="j_withdrawalsEdit_form">

			<div class="bjui-row col-1">
				<label class="row-label "></label>
				<div class="row-input ">
					<input type="hidden" id="withdrawals_ID" name="id" value=""
						readonly="readonly">
				</div>
				<label class="row-label "></label>
				<div class="row-input ">
					<input type="hidden" id="withdrawals_merchantId" name="merchantId"
						value="" readonly="readonly">
				</div>

				<label class="row-label ">商户账号</label>
				<div class="row-input ">
					<input type="text" id="withdrawals_Account" name="account" value=""
						readonly="readonly">
				</div>
				<label class="row-label">提现金额</label>
				<div class="row-input ">
					<input type="text" id="withdrawals_Amount" name="amount" value=""
						readonly="readonly">
				</div>
				<label class="row-label">余额</label>
				<div class="row-input">
					<input type="text" id="withdrawals_Balance" name="balance" value=""
						readonly="readonly">
				</div>
			</div>

		</form>
	</div>

</div>
<div class="bjui-pageFooter">
	<ul>
		<li><button type="button" class="btn-default" data-icon="close"
				onclick="reject()">驳回</button></li>
		<li><button type="button" class="btn-default" data-icon="save"
				onclick="agree()">通过</button></li>
	</ul>
</div>