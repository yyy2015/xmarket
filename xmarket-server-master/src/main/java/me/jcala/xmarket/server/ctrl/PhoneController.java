package me.jcala.xmarket.server.ctrl;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.jcala.xmarket.server.conf.DayuConfig;
import me.jcala.xmarket.server.entity.pojo.Result;
import me.jcala.xmarket.server.service.inter.UserService;
import me.jcala.xmarket.server.utils.RespFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Random;

/**
 * Phone validation
 * @author cuihao
 */
@Api("跟手机短信验证有关的API")
@RestController
@Slf4j
public class PhoneController  {

    @Resource
    private DayuConfig dayuConfig;

    @Resource
    private UserService userService;

    @PostMapping(value = "/api/v1/phone/send",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "发送验证短信",response = Result.class,produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> sendMessage(@RequestParam String phoneNum,
                                         HttpServletRequest request) throws ApiException {
        String url = "http://gw.api.taobao.com/router/rest";
        Random random = new Random();
        int code = random.nextInt(9999)%(9999-1000+1) + 1000;
        HttpSession session = request.getSession();
        session.setAttribute("code",code+"");
        session.setAttribute("phone",phoneNum);
        TaobaoClient client = new DefaultTaobaoClient(url, dayuConfig.getAppKey(), dayuConfig.getAppSecret());
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setSmsType("normal");
        req.setSmsFreeSignName("xmarke市场");
        req.setSmsParamString("{\"code\":\""+code+"\"}");
        req.setRecNum(phoneNum);
        req.setSmsTemplateCode(dayuConfig.getTemplateId());
        AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
        System.out.println(rsp.getBody());
        return RespFactory.INSTANCE().ok();
    }

    @PostMapping(value = "/api/v1/phone/verify",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "验证短信",response = Result.class,produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> verifyMessage(@RequestParam String code,
                                           @RequestParam String userId,
                                           HttpServletRequest request) {
        HttpSession session = request.getSession();
        String codeInSession = (String) session.getAttribute("code");
        if (codeInSession!=null && codeInSession.equals(code)) {
            return userService.updatePhone(userId, (String) session.getAttribute("phone"));
        }
        Result<String> errStr = new Result<String>().api(me.jcala.xmarket.server.entity.configuration.Api.USER_PHONE_ERR);
        return new ResponseEntity<Object>(errStr,HttpStatus.OK);
    }

}
