package com.thinkgem.jeesite.modules.zxy.front.auth;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.zxy.utils.AlipaySubmit;

@Controller
@RequestMapping("/alipay")
public class AliPayController extends BaseController
{
    private static final String NOTIFY_URL = "notify_url";
    
    private static final String RETURN_URL = "return_url";
    
    private static final String SHOW_URL = "show_url";
    
    private static final String PARTNER = "partner";
    
    private static final String SELLER_EMAIL = "seller_email";
    
    private static final String INPUT_CHARSET = "input_charset";
    
    @RequestMapping("/index/")
    public String index(HttpServletRequest request, Model model) throws Exception
    {
        return "/alipay/index";
    }
    
    @RequestMapping("/notify_url/")
    public String notify_url(HttpServletRequest request, Model model) throws Exception
    {
        return "/alipay/notify_url";
    }
    
    @RequestMapping("/return_url/")
    public String return_url(HttpServletRequest request, Model model) throws Exception
    {
        return "/alipay/return_url";
    }
    
    @RequestMapping("/alipayapi/")
    @ResponseBody
    public String alipayapi(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception
    {
        
        // 支付类型
        String payment_type = "1";
        // 必填，不能修改
        // 服务器异步通知页面路径
        // String notify_url = PropUtil.getInstance().get(NOTIFY_URL);
        String notify_url = new String(request.getParameter("notify_url").getBytes("ISO-8859-1"), "UTF-8");
        // 需http://格式的完整路径，不能加?id=123这类自定义参数
        
        // 页面跳转同步通知页面路径
        // String return_url = PropUtil.getInstance().get(RETURN_URL);
        String return_url = new String(request.getParameter("return_url").getBytes("ISO-8859-1"), "UTF-8");
        // 需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/
        
        // 商户订单号
        String out_trade_no = new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"), "UTF-8");
        // 商户网站订单系统中唯一订单号，必填
        
        // 订单名称
        // String subject = new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"), "UTF-8");
        String subject = request.getParameter("WIDsubject");
        // 必填
        
        // 付款金额
        String total_fee = new String(request.getParameter("WIDtotal_fee").getBytes("ISO-8859-1"), "UTF-8");
        // 必填
        
        // 订单描述
        String body = request.getParameter("WIDbody");
        
        Global.getInstance();
		// 商品展示地址
        // String show_url = new String(request.getParameter("WIDshow_url").getBytes("ISO-8859-1"), "UTF-8");
        String show_url = Global.getConfig(SHOW_URL);
        // 需以http://开头的完整路径，例如：http://www.商户网址.com/myorder.html
        
        // 防钓鱼时间戳
        String anti_phishing_key = "";
        // 若要使用请调用类文件submit中的query_timestamp函数
        
        // 客户端的IP地址
        String exter_invoke_ip = "";
        // 非局域网的外网IP地址，如：221.0.0.1
        
        // 把请求参数打包成数组
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("service", "create_direct_pay_by_user");
        sParaTemp.put("partner", Global.getConfig("partner"));
        sParaTemp.put("seller_email", Global.getConfig("seller_email"));
        sParaTemp.put("_input_charset", "UTF-8");
        sParaTemp.put("payment_type", payment_type);
        sParaTemp.put("notify_url", notify_url);
        sParaTemp.put("return_url", return_url);
        sParaTemp.put("out_trade_no", out_trade_no);
        sParaTemp.put("subject", subject);
        sParaTemp.put("total_fee", total_fee);
        sParaTemp.put("body", body);
        sParaTemp.put("show_url", show_url);
        sParaTemp.put("anti_phishing_key", anti_phishing_key);
        sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
        
        // 建立请求
        String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
        
        return JSONObject.fromObject(sHtmlText).toString();
    }
}
