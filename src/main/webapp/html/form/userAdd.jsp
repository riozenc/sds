<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">

$(function() {
	//公司需要显示营业执照编号等信息，个体户则不需要显示。
	$("#agType_div").hide();
	$("#agType").change(function(){
		if($("#agType").val() == "个体户"){
			$("#agType_div").hide()
		}else{
			$("#agType_div").show()
		}
	});
	//提交
	$("#submitSave").bind("click",function(){

		BJUI.ajax('ajaxform', {
		    url: 'user.do?type=insert',
		    form: $('#j_userAdd_form'),
		    validate: true,
		    loadingmask: true,
		    okCallback: function(json, options) {
		        $("#submitSave").unbind("click");
		        BJUI.navtab('refresh', 'userAdd');  //刷新
		    }
		})
	});
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
		
			var list =data.list;
			var selector=$('<select data-toggle="selectpicker" name="parentId" ></select>');  
	
			  selector.append('<option value="'+data.list[0].id+'">'+data.list[0].fullName+'</option>');  
			
			$("#parentSelUserAdd").append(selector);
		}
	});
})

	//输入推广码功能
	function func(){
		var a=document.getElementsByName("account")[0].value;
		document.getElementsByName("appCode")[0].value="EA"+a;
		}
</script>
<div class="bjui-pageContent">
    <div class="bs-example">
        <form id="j_userAdd_form" >
        <h4>基 本 信 息</h4>
        <div class="bjui-row col-2">
            <label class="row-label " >系统账号</label>
            <div class="row-input required">
                <input type="text" id="rowid" onblur="func()" name="account" value="" data-rule="required" >
            </div>
            <label class="row-label">推广码</label>
            <div class="row-input">
                <input type="text" id="roid1" value="" name="appCode" data-rule="required">
            </div>
            <label class="row-label " >系统密码</label>
            <div class="row-input required">
                <input type="text" name="password" value="" data-rule="required">
            </div>
            <label class="row-label">所属上级代理商</label>
            <div class="row-input" id="parentSelUserAdd">
            </div>
            <label class="row-label">企业全称</label>
            <div class="row-input">
                <input type="text" value="" name="fullName" data-rule="required">
            </div>
            <label class="row-label">企业简称</label>
            <div class="row-input">
                <input type="text" value="" name="simpleName" data-rule="required">
            </div>
            <label class="row-label">企业类型</label>
            <div class="row-input">
            <select name="agType"  id="agType">
            	<option value="个体户" selected>个体户</option>
				<option value="有限责任公司">有限责任公司</option>
				
			</select>
            </div>
            
            
            <label class="row-label">公司地址</label>
            <div class="row-input">
                <input type="text" value="" name="agAddress" data-rule="novalidate">
            </div>
            <label class="row-label">法人姓名</label>
            <div class="row-input">
                <input type="text" value="" name="regName" data-rule="novalidate">
            </div>
            <label class="row-label">身份证号</label>
            <div class="row-input">
                <input type="text" value="" name="regCard" data-rule="novalidate">
            </div>
            <label class="row-label">身份证地址</label>
            <div class="row-input">
                <input type="text" value="" name="regAddress" data-rule="novalidate">
            </div>
            
            <label class="row-label">成立日期</label>
            <div class="row-input">
                 <input value="2016-10-01" name="regDate" data-toggle="datepicker" type="text">
            </div>
            <label class="row-label">经营范围</label>
            <div class="row-input">
                <input type="text" value="" name="regExt" data-rule="novalidate">
            </div>
            <div id="agType_div">
            <label class="row-label">营业执照编号</label>
            <div class="row-input">
                <input type="text" value="" name="regNo" id="regNo" data-rule="novalidate">
            </div>
            <label class="row-label">营业期限</label>
            <div class="row-input">
                <input type="text" value="" name="busTerm" id="busTerm" data-rule="novalidate">
            </div>
            <label class="row-label">注册资本</label>
            <div class="row-input">
                <input type="text" value="" name="regMoney" data-rule="novalidate">
            </div>
            <label class="row-label">机构组织代码</label>
            <div class="row-input">
                <input type="text" value="" name="busAno" id="busAno" data-rule="novalidate">
            </div>
            <label class="row-label">税务登记号</label>
            <div class="row-input">
                <input type="text" value="" name="busSno" id="busSno"  data-rule="novalidate">
            </div>
            </div>
            
            
            <hr>
            <label class="row-label">微信成本费率</label>
            <div class="row-input">
                <input type="text" value="" name="costWrate" data-rule="required">
            </div>
            <label class="row-label">支付宝成本费率</label>
            <div class="row-input">
                <input type="text" value="" name="costArate" data-rule="required">
            </div>
            
            <label class="row-label">商户微信费率</label>
            <div class="row-input">
                <input type="text" value="" name="userWrate" data-rule="required">
            </div>
            <label class="row-label">商户支付宝费率</label>
            <div class="row-input">
                <input type="text" value="" name="userArate" data-rule="required">
            </div>
           
            <hr>
            <h4>结 算 账 户</h4>
            <label class="row-label">账户类型</label>
            <div class="row-input">
            	<select data-toggle="selectpicker" name="jsActype" id="jsActype" >
                    <option value="0" selected>法人</option>
                    <option value="1">企业</option>
                </select>
            </div>
            <label class="row-label">开户账号</label>
            <div class="row-input">
                <input type="text" value="" name="jsCard" data-rule="required">
            </div>
            <label class="row-label">开户地</label>
            <div class="row-input">
                <input type="text" value="" name="jsAddress" data-rule="required">
            </div>
            <label class="row-label">开户行</label>
            <div class="row-input">
                <select name="jsBank" id="CACCBANK">
                                    
                    <option value="中国工商银行">中国工商银行</option>
                        
                    <option value="中国农业银行">中国农业银行</option>
                        
                    <option value="中国银行">中国银行</option>
                        
                    <option value="中国建设银行">中国建设银行</option>
                        
                    <option value="交通银行">交通银行</option>
                        
                    <option value="中信银行">中信银行</option>
                        
                    <option value="中国光大银行">中国光大银行</option>
                        
                    <option value="华夏银行">华夏银行</option>
                        
                    <option value="中国民生银行">中国民生银行</option>
                        
                    <option value="平安银行">平安银行</option>
                        
                    <option value="招商银行">招商银行</option>
                        
                    <option value="兴业银行">兴业银行</option>
                        
                    <option value="上海浦东发展银行">上海浦东发展银行</option>
                        
                    <option value="盛京银行">盛京银行</option>
                        
                    <option value="恒丰银行">恒丰银行</option>
                        
                    <option value="浙商银行">浙商银行</option>
                        
                    <option value="渤海银行">渤海银行</option>
                        
                    <option value="徽商银行">徽商银行</option>
                        
                    <option value="上海农村商业银行">上海农村商业银行</option>
                        
                    <option value="中国邮政储蓄银行">中国邮政储蓄银行</option>
                        
                </select>
                <input type="text" value="" name="jsBankadd" style="width:166px" data-rule="required">
            </div>
            <label class="row-label">开户名</label>
            <div class="row-input">
                <input type="text" value="" name="jsName" data-rule="required">
            </div>
            <label class="row-label">联行号</label>
            <div class="row-input">
                <input type="text" value="" name="jsLhno" data-rule="required">
            </div>
            <hr>
            <h4>推 荐 功 能</h4>
            <label class="row-label">开关</label>
            <div class="row-input">
	            <select data-toggle="selectpicker" name="tjStatus">
		            <option value="0" selected >关闭</option>
		            <option value="1">开启</option>
		        </select>
            
            </div>
            <label class="row-label">消费额度</label>
            <div class="row-input">
                <input type="text" value="0" name="tjLimit" data-rule="required">
            </div>
            <label class="row-label">分润比例</label>
            <div class="row-input">
                <input type="text" value="0.5" name="tjRate" data-rule="required">
            </div>
        </div>
        </form>
    </div>
    
    <div class="bs-callout bs-callout-info">
        <h4>联行号<code></code></h4>
        <p><a href="https://www.hebbank.com/corporbank/otherBankQueryWeb.do"  target="_blank">银行联行号在线查询</a></p>
    </div>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">关闭</button></li>
        <li><button id="submitSave" class="btn-default" data-icon="save" >保存</button></li>
    </ul>
</div>
