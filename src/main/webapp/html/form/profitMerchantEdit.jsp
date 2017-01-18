<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
function save(){
	BJUI.ajax('ajaxform', {
	    url: 'profitMerchant.do?type=update',
	    form: $('#j_profitMerchantEdit_form'),
	    validate: true,
	    loadingmask: true,
	    okCallback: function(json, options) {
	    	BJUI.dialog('close', 'profitMerchantEdit');      //关闭
	    	BJUI.navtab('refresh', 'profitMerchant');  //刷新
	       
	    }
	})
}

</script>
<div class="bjui-pageContent">
    <div class="bs-example">
        <form  id="j_profitMerchantEdit_form" >
        
        <div class="bjui-row col-1">
            <label class="row-label " >ID</label>
            <div class="row-input required">
                <input type="text" name="id" id="id" value="" readonly="readonly">
            </div>
            <label class="row-label " >账号</label>
            <div class="row-input required">
                <input type="text" name="account"  id="account" value="" readonly="readonly">
            </div>
            
            <label class="row-label " >分润时间</label>
            <div class="row-input ">
                <input type="text" id="date" value="" readonly="readonly" >
            </div>
            
            <label class="row-label " >交易总额</label>
            <div class="row-input ">
                <input type="text"  id="totalAmount" value="" readonly="readonly" >
            </div>
            <label class="row-label " >分润总额</label>
            <div class="row-input ">
                <input type="text"  id="totalProfit" value="" readonly="readonly" >
            </div>
            
            <label class="row-label " >提现状态</label>
            <div class="row-input ">
                <select id="status" name="status" >
            		<option value="0">未提现</option>
            		<option value="1">已提现</option>
            		
            	</select>
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