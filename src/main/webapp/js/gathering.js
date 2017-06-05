ca.init();
/*
 * 点击数字键盘输入信息
 * 
 * @param {Object} inner
 * 
 */
function keyWord(inner) {
	changeNumber(inner);
}

/*
 * 改变input内容的方法
 * 
 * @param {Object} inner 需要添加的内容
 */
function changeNumber(inner) {
	//获取当前input的内容
	var inputValue = mui('.gathering-input')[0].value;

	if(inputValue.length < 8) {
		//可以输入
		var dotLocation = inputValue.indexOf('.');
		var newFromptValue = parseInt(inputValue);
		if(inputValue.length == 1 && newFromptValue == 0) {
			return;
		}
		if(dotLocation == -1) {
			//没有小数点
			var intInputValue = parseFloat(inputValue);
			if(isNaN(intInputValue)) {
				//第一次输入
				mui('.gathering-input')[0].value = inner;
			} else {
				//不是第一次输入
				var newInputValue = inputValue + inner;
				var intInputValue = parseInt(newInputValue);
				if(intInputValue > 49999.99) {
					mui('.gathering-input')[0].value = 49999.99;
				} else {
					mui('.gathering-input')[0].value = newInputValue;
				}
			}
		} else {
			//有小数点
			if(dotLocation == inputValue.length - 1) { //小数点在最后一位
				var newInputValue = inputValue + inner;
				var intInputValue = parseFloat(newInputValue);
				if(intInputValue > 49999.99) {
					mui('.gathering-input')[0].value = 49999.99;
				} else {
					mui('.gathering-input')[0].value = newInputValue;
				}

			} else {
				//小数点不在最后一位
				var dotLatterLength = inputValue.length - dotLocation;
				if(dotLatterLength <= 2) {
					var newInputValue = inputValue + inner;
					var intInputValue = parseFloat(newInputValue);
					if(intInputValue > 49999.99) {
						mui('.gathering-input')[0].value = 49999.99;
					} else {
						mui('.gathering-input')[0].value = newInputValue;
					}
				}
			}
		}
	}
};

/*
 * 添加小数点的方法
 * 
 * @param {Object} inner
 */
function keyWordDot(inner) {
	var inputValue = mui('.gathering-input')[0].value;
	if(inputValue.indexOf('.') == -1 && inputValue.length != 0) { //不存在小数点
		var intInputValue = parseInt(inputValue);
		if(intInputValue < 49999.99) {
			var newInputValue = intInputValue + inner;
			mui('.gathering-input')[0].value = newInputValue;
		}
	}
}

/*
 * 支付选择窗口的弹出和关闭
 */
mui('.gathering-gathering')[0].addEventListener('tap', function() {
	
	mui('#popover').popover('toggle');
});

/*
 * 删除输入框字符的方法
 */
mui('.gathering-delete')[0].addEventListener('tap', function() {
	var inputValue = mui('.gathering-input')[0].value;
	if(inputValue.length > 0) {
		var newInputValue = inputValue.substr(0, inputValue.length - 1);
		mui('.gathering-input')[0].value = newInputValue;
	}
});

/*
 * 选择支付方式
 */
mui(".gathering-popover-bg").on('tap', '.gathering-popover-img', function() {
	var mId = this.getAttribute("id");
	mId = parseInt(mId);
	switch(mId) {
		case 1:
			var inputValue = mui('.gathering-input')[0].value;
			var newInputValue = parseFloat(inputValue);
			var info = '微信收款';
			validGathering(newInputValue, info, 1);
			break;
		case 2:
			var inputValue = mui('.gathering-input')[0].value;
			var newInputValue = parseFloat(inputValue);
			var info = '支付宝收款';
			validGathering(newInputValue, info, 2);
			break;
		case 3:
			mui.toast('微信扫码功能尚未开通');
			break;
		case 4:
			mui.toast('支付宝扫码功能尚未开通');
			break;
	}
});

/*
 * 
 * 
 * @param {Object} newInputValue 
 * @param {Object} info
 * @param {Object} code
 */
function validGathering(newInputValue, info, code) {
	if(newInputValue > 0) {
		pay(newInputValue, info, code);
	} else {
		mui.toast('请输入收款金额');
	}
}

/*
 * 获取支付二维码和订单号的方法
 * 
 * @param {Object} amount amount（金额）
 * @param {Object} info info（备注）
 * @param {Object} channelCode channelCode（渠道：微信1支付宝2）
 */
function pay(amount, info, channelCode) {
	alert(code);
	var mDate = getNowFormatDate();
	var mSuccessAmount = amount;
	var amount = amount * 100;
	ca.showWaiting('正在生成二维码');
	mui.ajax(pay_url, {
		data: {
			'amount': amount,
			'info': info,
			'channelCode': channelCode,
			'code':code
		},
		type: 'post',
		timeout: 10000,
		success: function(data) {
			console.log('pay' + data);
			ca.closeWaiting();
			var payData = JSON.parse(data);
			if(payData.statusCode == 200) {
				var payMessageData = payData.message;
				//去除Message中的转义字符
				payMessageData = payMessageData.replace("/", "")
				//JSON解析
				payMessageData = JSON.parse(payMessageData);
				
				//获取支付二维码和订单号
				var message = payMessageData.QRcodeURL;
				var orderId = payMessageData.orderId;
				
				if(message.indexOf('wxpay') > 0) { 
					//微信收款码
					console.log(mDate,mSuccessAmount);
					openWxQRCode(message, orderId,mDate,mSuccessAmount);
				} else {
					//支付宝收款码
					openALiQRCode(message, orderId,mDate,mSuccessAmount);
				}
			} else if(payData.statusCode == 202){
				mui.toast('当前登录已失效,请重新登录');
			}else {
				mui.toast('获取二维码失败,请重新发起交易');
			}
		},
		error: function(xhr, type, errorThrown) {
			ca.closeWaiting();
			mui.toast("请检查网络后再试");
		}
	});
};

/**
 * 打开微信扫码界面
 * 
 * @param {Object} message 微信支付二维码
 * @param {Object} orderId 订单ID
 */
function openWxQRCode(message, orderId,mDate,mSuccessAmount) {
	mui.openWindow({
		url: 'wx_qrcode.html',
		id: 'wx_qrcode.html',
		extras: {
			message: message,
			orderId: orderId,
			mDate: mDate,
			mSuccessAmount: mSuccessAmount
		},
		createNew: false,
		show: {
			autoShow: true,
			aniShow: 'slide-in-right',
			duration: 200
		},
		waiting: {
			autoShow: true,
			title: '正在加载',
		}
	})
}

/*
 * 打开支付宝扫码界面
 * 
 * @param {Object} message 支付宝二维码信息
 * @param {Object} orderId 订单ID
 */
function openALiQRCode(message, orderId,mDate,mSuccessAmount) {
	mui.openWindow({
		url: 'ali_qrcode.html',
		id: 'ali_qrcode.html',
		extras: {
			message: message,
			orderId: orderId,
			mDate: mDate,
			mSuccessAmount: mSuccessAmount
		},
		createNew: false,
		show: {
			autoShow: true,
			aniShow: 'slide-in-right',
			duration: 200
		},
		waiting: {
			autoShow: true,
			title: '正在加载',
		}
	})
}

/*
 * 获取当前时间的方法 
 */
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
    return currentdate;
}

