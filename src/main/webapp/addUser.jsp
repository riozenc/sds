<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>




<script type="text/javascript">
	function pic_upload_success(file, data) {
		var json = $.parseJSON(data)

		$(this).bjuiajax('ajaxDone', json)
		if (json[BJUI.keys.statusCode] == BJUI.statusCode.ok) {
			$('#pic_logo').val(json.filename).trigger('validate')
			$('#j_pic_logo')
					.html(
							'<img src="__PUBLIC__/uploadfile/'+ json.filename +'" style="width:400px;" />')

		}

	}
	function do_OK(json, $form) {
		console.log(json)
		console.log($form)
	}
</script>
<div class="bjui-pageHeader" style="background: #FFF;"></div>
<div class="bjui-pageContent">
	<div style="margin-top: 5px; margin-right: 300px; overflow: hidden;">
		<div class="row" style="padding: 0 8px;">
			<div class="col-md-12">
				<form action="user.do?type=insert" id="" data-toggle="validate"
					data-alertmsg="false">

					<table class="table table-condensed table-hover" width="100%">
						<tbody>
							<tr>
								<td><label class="control-label x85">顶部图片：</label>
									<div style="display: inline-block; vertical-align: middle;">
										<div id="j_custom_pic_up" data-toggle="upload"
											data-uploader="__APP__/Public/xhupload/?sessionid=?"
											data-file-size-limit="1024000000"
											data-file-type-exts="*.jpg;*.png;*.gif;*.mpg"
											data-multi="true" data-on-upload-success="pic_upload_success"
											data-icon="cloud-upload"></div>
										<input type="hidden" id="pic_logo" name="logo"
											value="{$list['logo']}">
										<div id="j_pic_logo"
											style="padding: 4px; border: 1px #CCC solid; border-radius: 3px; text-align: center;">
											<img src="__PUBLIC__/uploadfile/{$list['logo']}"
												style="width: 400px;">
										</div>
									</div></td>
							</tr>
							
							<tr>
								<td colspan="2"><label class="control-label x150"
									for="account">登录手机号:</label> <input type="text" name="account"
									id="account" value="13033445566" data-rule="required" size="30"></td>
							</tr>
							<tr>
								<td colspan="2"><label class="control-label x150"
									for="password">登录密码:</label> <input type="text" name="password"
									id="password" value="1234" data-rule="required" size="30"></td>
							</tr>
							
							<tr>
								<td colspan="2"><label class="control-label x150"
									for="parentId">上级代理商:</label> <input type="text" name="parentId"
									id="parentId" value="1" data-rule="required" size="30"></td>
							</tr>
							
							<tr>
								<td colspan="2"><label for="fullName"
									class="control-label x150">企业全称:</label> <input type="text"
									name="fullName" id="fullName" value="A" size="30"></td>
							</tr>
							<tr>
								<td colspan="2"><label for="simpleName"
									class="control-label x150">企业简称:</label> <input type="text"
									name="simpleName" id="simpleName" value="a" size="30"></td>
							</tr>
							<tr>
								<td colspan="2"><label class="control-label x150"
									for="agType">企业类型:</label> <input type="text" name="agType"
									id="agType" value="1" data-rule="required" size="30"></td>
							</tr>
							<tr>
								<td colspan="2"><label for="regMoney"
									class="control-label x150">注册资本:</label> <input type="text"
									name="regMoney" id="regMoney" value="2000" data-rule="required"
									size="30"></td>
							</tr>
							<tr>
								<td colspan="2"><label for="agAddress"
									class="control-label x150">公司地址:</label> <input type="text"
									name="agAddress" id="agAddress" value="南大街"
									data-rule="required" size="30"></td>
							</tr>
							<tr>
								<td colspan="2"><label for="regName"
									class="control-label x150">法人姓名:</label> <input type="text"
									name="regName" id="regName" value="张三" data-rule="required"
									size="30"></td>
							</tr>
							<tr>
								<td colspan="2"><label for="regAddress"
									class="control-label x150">身份证地址:</label> <input type="text"
									name="regAddress" id="regAddress" value="首都"
									data-rule="required" size="30"></td>
							</tr>
							<tr>
								<td colspan="2"><label for="regDate"
									class="control-label x150">成立日期:</label> <input type="text"
									name="regDate" id="regDate" value="2016-07-09"
									data-rule="required" size="30"></td>
							</tr>
							<tr>
								<td colspan="2"><label for="regExt"
									class="control-label x150">经营范围:</label> <input type="text"
									name="regExt" id="regExt" value="投资理财" data-rule="required"
									size="30"></td>
							</tr>
							<tr>
								<td colspan="2"><label for="regNo"
									class="control-label x150">营业执照编号:</label> <input type="text"
									name="regNo" id="regNo" value="1" data-rule="required"
									size="30"></td>
							</tr>
							<tr>
								<td colspan="2"><label for="busTerm"
									class="control-label x150">营业期限:</label> <input type="text"
									name="busTerm" id="busTerm" value="30" data-rule="required"
									size="30"></td>
							</tr>
							<tr>
								<td colspan="2"><label for="busAno"
									class="control-label x150">机构组织代码:</label> <input type="text"
									name="busAno" id="busAno" value="1" data-rule="required"
									size="30"></td>
							</tr>
							<tr>
								<td colspan="2"><label for="busSno"
									class="control-label x150">税务登记号:</label> <input type="text"
									name="busSno" id="busSno" value="1" data-rule="required"
									size="30"></td>
							</tr>

							<tr>
								<td colspan="2"><label for="costWrate"
									class="control-label x150">代理商微信成本费率:</label> <input
									type="text" name="costWrate" id="costWrate" value="0.005"
									data-rule="required" size="30"></td>
							</tr>
							<tr>
								<td colspan="2"><label for="userWrate"
									class="control-label x150">商户微信签约费率:</label> <input type="text"
									name="userWrate" id="userWrate" value="0.005"
									data-rule="required" size="30"></td>
							</tr>
							<tr>
								<td colspan="2"><label for="costArate"
									class="control-label x150">代理商支付宝成本费率:</label> <input
									type="text" name="costArate" id="costArate" value="0.005"
									data-rule="required" size="30"></td>
							</tr>
							<tr>
								<td colspan="2"><label for="userArate"
									class="control-label x150">商户支付宝签约费率:</label> <input
									type="text" name="userArate" id="userArate" value="0.005"
									data-rule="required" size="30"></td>
							</tr>
							<tr>
								<td colspan="2"><label for="costKrate"
									class="control-label x150">代理商快捷成本费率:</label> <input
									type="text" name="costKrate" id="costKrate" value="0.005"
									data-rule="required" size="30"></td>
							</tr>
							<tr>
								<td colspan="2"><label for="userKrate"
									class="control-label x150">商户快捷签约费率:</label> <input type="text"
									name="userKrate" id="userKrate" value="0.005"
									data-rule="required" size="30"></td>
							</tr>


						</tbody>

						<tbody>
							<tr>
								<td colspan="2"><label for="jsActype"
									class="control-label x150">账户类型:</label> <input type="text"
									name="jsActype" id="jsActype" value="2" data-rule="required"
									size="30"></td>
							</tr>

							<tr>
								<td colspan="2"><label for="jsCard"
									class="control-label x150">开户账号:</label> <input type="text"
									name="jsCard" id="jsCard" value="2" data-rule="required"
									size="30"></td>
							</tr>

							<tr>
								<td colspan="2"><label for="jsAddress"
									class="control-label x150">开户地:</label> <input type="text"
									name="jsAddress" id="jsAddress" value="上海" data-rule="required"
									size="30"></td>
							</tr>


							<tr>
								<td colspan="4"><label for="jsBank"
									class="control-label x150">开户行:</label> <input type="text"
									name="jsBank" id="jsBank" value="恒丰银行" data-rule="required"
									size="30"></td>
							</tr>

							<tr>
								<td colspan="2"><label for="jsName"
									class="control-label x150">开户名:</label> <input type="text"
									name="jsName" id="jsName" value="张三" data-rule="required"
									size="30"></td>
							</tr>
							<tr>
								<td colspan="2"><label for="jsLhno"
									class="control-label x150">联行号:</label> <input type="text"
									name="jsLhno" id="jsLhno" value="3" data-rule="required"
									size="30"></td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
</div>
<div class="bjui-pageFooter">
	<ul>
		<li><button type="submit" class="btn-default" data-icon="save">保存</button></li>
	</ul>
</div>
</html>