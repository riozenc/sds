
<script type="text/javascript">
function pic_upload_success(file, data) {
    var json = $.parseJSON(data)
    
    $(this).bjuiajax('ajaxDone', json)
    if (json[BJUI.keys.statusCode] == BJUI.statusCode.ok) {
        $('#pic_logo').val(json.filename).trigger('validate')
        $('#j_pic_logo').html('<img src="__PUBLIC__/uploadfile/'+ json.filename +'" style="width:400px;" />')
		
    }
	
}
function do_OK(json, $form) {
    console.log(json)
    console.log($form)
}
</script>
<div class="bjui-pageHeader" style="background:#FFF;">
    
</div>
<div class="bjui-pageContent">
    <div style="margin-top:5px; margin-right:300px; overflow:hidden;">
        <div class="row" style="padding: 0 8px;">
            <div class="col-md-12">
		    <form action="__APP__/Index/index_layout_do/" id="" data-toggle="validate" data-alertmsg="false">
				
				<table class="table table-condensed table-hover" width="100%">
					<tbody>
						<tr>
							<td>
								<label class="control-label x85">顶部图片：</label>
								<div style="display: inline-block; vertical-align: middle;">
									<div id="j_custom_pic_up" data-toggle="upload" data-uploader="__APP__/Public/xhupload/?sessionid=?" 
										data-file-size-limit="1024000000"
										data-file-type-exts="*.jpg;*.png;*.gif;*.mpg"
										data-multi="true"
										data-on-upload-success="pic_upload_success"
										data-icon="cloud-upload"></div>
									<input type="hidden" id="pic_logo" name="logo" value="{$list['logo']}" >
									<div id="j_pic_logo" style="padding:4px; border:1px #CCC solid; border-radius:3px; text-align:center;">
									<img src="__PUBLIC__/uploadfile/{$list['logo']}" style="width:400px;"></div>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<label for="con1" class="control-label x100">网站标题：</label>
								<input type="text" name="con1" id="con1" value="{$list['con1']}" size="30">
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<label for="ten" class="control-label x100">网站英文标题：</label>
								<input type="text" name="ten" id="ten" value="{$list['ten']}"  size="30">
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<label class="control-label x100" for="con2">关键词：</label>
								<input type="text" name="con2" id="con2" value="{$list['con2']}" data-rule="required" size="20">
								
							</td>
						</tr> 
						<tr>
							<td colspan="2">
								<label for="con3" class="control-label x100">网站描述：</label>
								<input type="text" name="con3" id="con3" value="{$list['con3']}" data-rule="required" size="30">
							</td>
						</tr>
						<tr>
						   <td colspan="2">
								<label for="company" class="control-label x100">公司名称：</label>
								<input type="text" name="company" id="company" value="{$list['company']}" data-rule="required" size="30">
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<label for="companyen" class="control-label x100">公司英文名称：</label>
								<input type="text" name="companyen" id="companyen" value="{$list['companyen']}" data-rule="required" size="30">
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<label for="mobile" class="control-label x100">手机号码：</label>
								<input type="text" name="mobile" id="mobile" value="{$list['mobile']}" size="30">
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<label for="j_custom_passportno" class="control-label x100">公司电话：</label>
								<input type="text" name="phone" id="j_custom_passportno" value="{$list['phone']}" size="30">
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<label for="email" class="control-label x100">电子邮箱：</label>
								<input type="text" name="email" id="email" value="{$list['email']}" data-rule="required" size="30">
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<label for="address" class="control-label x100">公司地址：</label>
								<input type="text" name="address" id="address" value="{$list['address']}" data-rule="required" size="30">
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<label for="addressen" class="control-label x100">公司英文地址：</label>
								<input type="text" name="addressen" id="addressen" value="{$list['addressen']}" data-rule="required" size="30">
							</td>
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