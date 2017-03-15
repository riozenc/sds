<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
function save(){
	BJUI.ajax('ajaxform', {
	    url: 'merchant.do?type=updateRate',
	    form: $('#j_merchantEdit_form'),
	    validate: true,
	    loadingmask: true,
	    okCallback: function(json, options) {
	    	BJUI.dialog('close', 'merchantEdit');      //关闭
	    	BJUI.navtab('refresh', 'merchantData');  //刷新
	       
	    }
	})
}

</script>
<div class="bjui-pageContent">
    <div class="bs-example">
        <form  id="j_merchantEdit_form" >
        <h4>基 本 信 息</h4>
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
                <input type="text" id="createDate" value="" readonly="readonly" >
            </div>
            <label class="row-label " >真实姓名</label>
            <div class="row-input ">
                <input type="text"  id="realName" value="" readonly="readonly" >
            </div>
            <label class="row-label " >商户名称</label>
            <div class="row-input ">
                <input type="text"  id="cmer" value="" readonly="readonly" >
            </div>
            <label class="row-label " >商户简称</label>
            <div class="row-input ">
                <input type="text"  id="cmerSort" value="" readonly="readonly" >
            </div>
            <label class="row-label " >行业代码</label>
            <div class="row-input ">
                <input type="text"  id="businessId" value=""  readonly="readonly">
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
                <input type="text"  id="phone" value="" readonly="readonly" >
            </div>
            <label class="row-label required" >手机号</label>
            <div class="row-input ">
                <input type="text" id="mobile" value=""  readonly="readonly">
            </div>
            <label class="row-label " >开户城市</label>
            <div class="row-input ">
                <input type="text" id="location" value=""   readonly="readonly" >
            </div>
            <label class="row-label " >支付宝费率</label>
            <div class="row-input ">
                <input type="text" name="aliRate" id="aliRate" value=""  data-rule="required">
            </div>
            <label class="row-label " >微信费率</label>
            <div class="row-input ">
                <input type="text" name="wxRate" id="wxRate" value="" data-rule="required" >
            </div>
            <label class="row-label " >推广码</label>
            <div class="row-input ">
                <input type="text"  id="appCode" value=""  readonly="readonly">
            </div>
            
            <label class="row-label " >用户类型</label>
            <div class="row-input ">
                <select id="userType"  disabled="disabled">
            		<option value="2">企业用户</option>
            		<option value="1">普通用户</option>
            		
            	</select>
            </div>
            <div class="row r_pic">
            
             <div class="thumbnail ">
	            <img alt="" id="businessPic" class="m_pic" src="">
	            <label class="row-label ">工商执照</label>
	         </div>
	         <div class="thumbnail">
	            <img alt="" id="doorPic" class="m_pic"src="">
	            <label class="row-label ">门头执照</label>
	         </div>
	         <div class="thumbnail">
	            <img alt="" id="cardCorrect"class="m_pic" src="">
	            <label class="row-label ">银行卡照片</label>
	         </div>
	         <div class="thumbnail">
	            <img alt="" id="certMeet"class="m_pic" src="">
	            <label class="row-label ">手持身份证</label>
	         </div>
	         <div class="thumbnail">
	            <img alt="" id="certCorrect" class="m_pic"src="">
	            <label class="row-label ">身份证正面</label>
	         </div>
	         <div class="thumbnail">
	            <img alt="" id="certOpposite"class="m_pic" src="">
	            <label class="row-label ">身份证背面</label>
	         </div>
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