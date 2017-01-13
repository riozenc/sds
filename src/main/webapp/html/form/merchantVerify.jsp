<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
function save(){
	BJUI.ajax('ajaxform', {
	    url: 'merchant.do?type=update',
	    form: $('#j_merchant_form'),
	    validate: true,
	    loadingmask: true,
	    okCallback: function(json, options) {
	    	BJUI.dialog('close', 'merchantVerify');      //关闭
	    	BJUI.navtab('refresh', 'merchantData');  //刷新
	       
	    }
	})
}

</script>
<div class="bjui-pageContent">
    <div class="bs-example">
        <form  id="j_merchant_form" >
        
        <div class="bjui-row col-1">
            <label class="row-label " >ID</label>
            <div class="row-input required">
                <input type="text" name="id" id="id" value="" readonly="readonly">
            </div>
            <label class="row-label " >系统账号</label>
            <div class="row-input required">
                <input type="text" name="account"  id="account" value="" readonly="readonly">
            </div>
            
            <label class="row-label " >创建时间</label>
            <div class="row-input ">
                <input type="text"  id="createDate" value="" readonly="readonly" >
            </div>
            <label class="row-label " >真实姓名</label>
            <div class="row-input ">
                <input type="text" name="realName" id="realName" value=""  readonly="readonly">
            </div>
            <label class="row-label " >商户名称</label>
            <div class="row-input ">
                <input type="text" name="cmer" id="cmer" value=""  readonly="readonly">
            </div>
            <label class="row-label " >商户简称</label>
            <div class="row-input ">
                <input type="text" name="cmerSort" id="cmerSort" value=""  readonly="readonly">
            </div>
            <label class="row-label " >支付通道</label>
            <div class="row-input ">
                <input type="text" name="channelCode" id="channelCode" value=""  readonly="readonly">
            </div>
            <label class="row-label " >行业代码</label>
            <div class="row-input ">
                <input type="text" name="businessId" id="businessId" value=""  readonly="readonly">
            </div>
            <label class="row-label required" >银行卡号</label>
            <div class="row-input ">
                <input type="text" name="cardNo" id="cardNo" value=""  readonly="readonly">
            </div>
            <label class="row-label required" >证件号</label>
            <div class="row-input ">
                <input type="text" name="certNo" id="certNo" value=""  readonly="readonly">
            </div>
            <label class="row-label " >电话码</label>
            <div class="row-input ">
                <input type="text" name="phone" id="phone" value=""  readonly="readonly">
            </div>
            <label class="row-label required" >手机号</label>
            <div class="row-input ">
                <input type="text" name="mobile" id="mobile" value=""  readonly="readonly">
            </div>
            <label class="row-label " >开户城市</label>
            <div class="row-input ">
                <input type="text" name="location" id="location" value=""  readonly="readonly">
            </div>
            <label class="row-label " >支付宝费率</label>
            <div class="row-input ">
                <input type="text" name="aliRate" id="aliRate" value=""   disabled="disabled">
            </div>
            <label class="row-label " >微信费率</label>
            <div class="row-input ">
                <input type="text" name="wxRate" id="wxRate" value=""   disabled="disabled">
            </div>
            <label class="row-label " >推广码</label>
            <div class="row-input ">
                <input type="text" name="appCode" id="appCode" value=""  readonly="readonly">
            </div>
            
            <label class="row-label " >用户类型</label>
            <div class="row-input ">
            	<select id="userType"  disabled="disabled">
            		<option value="2">企业用户</option>
            		<option value="1">普通用户</option>
            		
            	</select>
            </div>
            <label class="row-label " >审核</label>
            <div class="row-input ">
            	<select id="status"  name="status">
            		<option value="3">审核成功</option>
            	
            		<option value="4">审核失败</option>
            	</select>
                
            </div>
            <label class="row-label " >审核原因</label>
            <div class="row-input ">
                <input type="text" name="other" id="other" value=""   >
            </div>
            
        </div>
        </form>
    </div>
    
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">关闭</button></li>
        <li><button type="button" class="btn-default" data-icon="save" onclick="save()">保存</button></li>
    </ul>
</div>