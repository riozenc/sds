/**
 * Title:DefaultAction.java
 * Author:riozenc
 * Datetime:2017年6月5日 上午9:32:16
**/
package sds.webapp.sys.action;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.riozenc.quicktool.common.util.date.DateUtil;
import com.riozenc.quicktool.springmvc.context.SpringContextHolder;

import sds.common.remote.util.NameUtil;
import sds.webapp.acc.domain.MerchantDomain;
import sds.webapp.acc.service.MerchantService;
import sds.webapp.ord.domain.OrderDomain;
import sds.webapp.ord.service.OrderService;
import sds.webapp.stm.action.ProfitAction;
import sds.webapp.sys.domain.ConfDomain;

/**
 * 操作一些特殊请求
 * 
 * @author riozenc
 *
 */
@ControllerAdvice
@RequestMapping("default")
public class DefaultAction {
	private static long orderId = 100118078259274L;

	@Autowired
	@Qualifier("merchantServiceImpl")
	private MerchantService merchantService;

	@Autowired
	@Qualifier("orderServiceImpl")
	private OrderService orderService;

	// 注册商户--步骤a1
	// 变成审核完成商户--跳过
	// 商户跟虚拟账户关联--跳过
	// 随机生成订单--步骤b2
	// 分润--步骤c3

	/**
	 * 生成虚假商户
	 * 
	 * @param limit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=a1")
	public String a1(int limit) {
		try {
			for (int i = 0; i < limit; i++) {
				MerchantDomain merchantDomain = createMerchant();
				merchantService.register(merchantDomain);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成订单
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=b2")
	public String b2(int limit, String date) {

		MerchantDomain merchantDomain = new MerchantDomain();
		merchantDomain.setPageSize(999);
		List<MerchantDomain> list = merchantService.findByWhere(merchantDomain);

		for (int i = 0; i < limit; i++) {
			orderId += 1;
			int l = (int) Math.round(Math.random() * list.size());
			OrderDomain orderDomain = createOrder(list.get(l).getAccount(), date);
			orderService.insert(orderDomain);
		}

		return null;
	}

	/**
	 * 生成分润
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(params = "type=c3")
	public String c3() throws Exception {

		ProfitAction profitAction = SpringContextHolder.getBean("profitAction");

		return profitAction.profit();
	}

	private static OrderDomain createOrder(String account, String date) {

		// D0+100115016109844
		OrderDomain orderDomain = new OrderDomain();
		orderDomain.setOrderId("D0" + orderId);
		orderDomain.setChannelCode(Math.random() > 0.5 ? 1 : 2);
		orderDomain.setRespCode("000000");
		orderDomain.setRespInfo("交易成功");

		orderDomain.setAmount((double) Math.round(Math.random() * 100000) / 100);
		orderDomain.setAccount(account);
		orderDomain.setRemark(orderDomain.getChannelCode() == 1 ? "微信收款" : "支付宝收款");
		orderDomain.setStatus(1);

		// 2017-05-18 10:17:52
		long s = Math.round(Math.random() * 24);
		long f = Math.round(Math.random() * 60);
		long m = Math.round(Math.random() * 60);
		date = date + " " + (s > 10 ? s : ("0" + s)) + ":" + (f > 10 ? f : ("0" + f)) + ":" + (m > 10 ? m : ("0" + m));
		// 伪造时间
		orderDomain.setDate(DateUtil.parseDateTime(date));

		return orderDomain;
	}

	// 创建虚假商户
	private static MerchantDomain createMerchant() throws Exception {
		MerchantDomain merchantDomain = new MerchantDomain();
		merchantDomain.setAccount(getTel());
		merchantDomain.setPassword("123456");
		merchantDomain.setCreateDate(DateUtil.parseDateTime("2017-03-23 14:51:57"));
		merchantDomain.setRealName(getChineseName());
		merchantDomain.setPhone(merchantDomain.getAccount());
		merchantDomain.setMobile(merchantDomain.getAccount());
		merchantDomain.setLocation("北京");
		merchantDomain.setAppCode("EA18660509556");
		merchantDomain.setUserType(2);
		merchantDomain.setAgentId(7);
		merchantDomain.setStatus(3);
		merchantDomain.setWxRate(0.0045);
		merchantDomain.setAliRate(0.0045);
		merchantDomain.setUnipayRate(0.0045);

		//
		Map<String, ConfDomain> map = ConfAction.getConfig("MCC");
		ConfDomain[] domains = new ConfDomain[map.size()];
		map.values().toArray(domains);
		Random random = new Random();
		String randomName = domains[random.nextInt(domains.length)].getName();
		String cmerName = NameUtil.randomName() + randomName.split("/")[random.nextInt(randomName.split("/").length)];;
		merchantDomain.setBusinessId(domains[random.nextInt(domains.length)].getValue());
		merchantDomain.setCmer(cmerName);
		merchantDomain.setCmerSort(cmerName);

		return merchantDomain;
	}

	private static String[] telFirst = "134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153,186,185,188"
			.split(",");

	private static String getTel() {
		int index = getNum(0, telFirst.length - 1);
		String first = telFirst[index];
		String second = String.valueOf(getNum(1, 888) + 10000).substring(1);
		String third = String.valueOf(getNum(1, 9100) + 10000).substring(1);
		return first + second + third;
	}

	public static int getNum(int start, int end) {
		return (int) (Math.random() * (end - start + 1) + start);
	}

	/**
	 * 返回中文姓名
	 */
	private static String firstName = "赵钱孙李周吴郑王冯陈褚卫蒋沈韩杨朱秦尤许何吕施张孔曹严华金魏陶姜戚谢邹喻柏水窦章云苏潘葛奚范彭郎鲁韦昌马苗凤花方俞任袁柳酆鲍史唐费廉岑薛雷贺倪汤滕殷罗毕郝邬安常乐于时傅皮卞齐康伍余元卜顾孟平黄和穆萧尹姚邵湛汪祁毛禹狄米贝明臧计伏成戴谈宋茅庞熊纪舒屈项祝董梁杜阮蓝闵席季麻强贾路娄危江童颜郭梅盛林刁钟徐邱骆高夏蔡田樊胡凌霍虞万支柯咎管卢莫经房裘缪干解应宗宣丁贲邓郁单杭洪包诸左石崔吉钮龚程嵇邢滑裴陆荣翁荀羊於惠甄魏加封芮羿储靳汲邴糜松井段富巫乌焦巴弓牧隗山谷车侯宓蓬全郗班仰秋仲伊宫宁仇栾暴甘钭厉戎祖武符刘姜詹束龙叶幸司韶郜黎蓟薄印宿白怀蒲台从鄂索咸籍赖卓蔺屠蒙池乔阴郁胥能苍双闻莘党翟谭贡劳逄姬申扶堵冉宰郦雍却璩桑桂濮牛寿通边扈燕冀郏浦尚农温别庄晏柴瞿阎充慕连茹习宦艾鱼容向古易慎戈廖庚终暨居衡步都耿满弘匡国文寇广禄阙东殴殳沃利蔚越夔隆师巩厍聂晁勾敖融冷訾辛阚那简饶空曾毋沙乜养鞠须丰巢关蒯相查后江红游竺权逯盖益桓公万俟司马上官欧阳夏侯诸葛闻人东方赫连皇甫尉迟公羊澹台公冶宗政濮阳淳于仲孙太叔申屠公孙乐正轩辕令狐钟离闾丘长孙慕容鲜于宇文司徒司空亓官司寇仉督子车颛孙端木巫马公西漆雕乐正壤驷公良拓拔夹谷宰父谷粱晋楚阎法汝鄢涂钦段干百里东郭南门呼延归海羊舌微生岳帅缑亢况后有琴梁丘左丘东门西门商牟佘佴伯赏南宫墨哈谯笪年爱阳佟第五言福百家姓续";
	private static String girl = "秀娟英华慧巧美娜静淑惠珠翠雅芝玉萍红娥玲芬芳燕彩春菊兰凤洁梅琳素云莲真环雪荣爱妹霞香月莺媛艳瑞凡佳嘉琼勤珍贞莉桂娣叶璧璐娅琦晶妍茜秋珊莎锦黛青倩婷姣婉娴瑾颖露瑶怡婵雁蓓纨仪荷丹蓉眉君琴蕊薇菁梦岚苑婕馨瑗琰韵融园艺咏卿聪澜纯毓悦昭冰爽琬茗羽希宁欣飘育滢馥筠柔竹霭凝晓欢霄枫芸菲寒伊亚宜可姬舒影荔枝思丽 ";
	private static String boy = "伟刚勇毅俊峰强军平保东文辉力明永健世广志义兴良海山仁波宁贵福生龙元全国胜学祥才发武新利清飞彬富顺信子杰涛昌成康星光天达安岩中茂进林有坚和彪博诚先敬震振壮会思群豪心邦承乐绍功松善厚庆磊民友裕河哲江超浩亮政谦亨奇固之轮翰朗伯宏言若鸣朋斌梁栋维启克伦翔旭鹏泽晨辰士以建家致树炎德行时泰盛雄琛钧冠策腾楠榕风航弘";

	private static String getChineseName() {
		int index = getNum(0, firstName.length() - 1);
		String first = firstName.substring(index, index + 1);
		int sex = getNum(0, 1);
		String str = boy;
		int length = boy.length();
		if (sex == 0) {
			str = girl;
			length = girl.length();
		} else {
		}
		index = getNum(0, length - 1);
		String second = str.substring(index, index + 1);
		int hasThird = getNum(0, 1);
		String third = "";
		if (hasThird == 1) {
			index = getNum(0, length - 1);
			third = str.substring(index, index + 1);
		}
		return first + second + third;
	}

}
