package com.killer.ksport.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.killer.ksport.auth.service.ILoginService;
import com.killer.ksport.auth.util.VerifyCodeUtil;
import com.killer.ksport.auth.vo.LoginUserVo;
import com.killer.ksport.auth.vo.VcodeTime;
import com.killer.ksport.auth.vo.VerifyCodeVo;
import com.killer.ksport.common.core.exception.CommonException;
import com.killer.ksport.common.core.util.ImageUtil;
import com.killer.ksport.common.core.controller.BaseController;
import com.killer.ksport.redis.constant.RedisKeyConstant;
import com.killer.ksport.redis.service.IRedisService;
import com.killer.ksport.token.model.LoginUser;
import com.killer.ksport.token.util.JwtTokenHelper;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.killer.ksport.common.core.controller.ResponseBuilder;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * @author ：Killer
 * @date ：Created in 19-6-28 上午9:14
 * @description：${description}
 * @modified By：
 * @version: version
 */
@RestController
@RequestMapping("/")
public class LoginController extends BaseController {

    @Autowired
    private ILoginService loginService;
    @Autowired
    private IRedisService redisService;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    /**
     * 验证码过期时间
     */
    @Value("${auth.verifyCodeExpiredTime}")
    private long verifyCodeExpiredTime;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(@Valid LoginUserVo requestLoginUser, BindingResult bindingResult) {
        // 检查有没有输入用户名密码和格式对不对
        Map<String, String> fieldErrorMap = getErrors(bindingResult);
        if (fieldErrorMap.size() > 0) {
            return new ResponseBuilder().error().message(getErrorsString(bindingResult)).add("fields", fieldErrorMap).build();
        }
        //判断验证码是否正确
        if (!checkVerifyCode(requestLoginUser.getReqId(), requestLoginUser.getVerifyCode())) {
            throw new CommonException("验证码过期或不正确");
        }
        //调用用户服务获取登录用户
        LoginUser loginUser = loginService.getLoginUser(requestLoginUser.getAccount(), requestLoginUser.getPassword());
        //保存登录用户,以便需要作认证是获取
        saveLoginUser(loginUser);
        //生成token
        String token = jwtTokenHelper.generateToken(loginUser.getUserId());
        return new ResponseBuilder().success().data(loginUser).add("token", token).build();
    }

    private void saveLoginUser(LoginUser loginUser){
        //将用户信息缓存
        redisService.hSet(RedisKeyConstant.USER_DETAILS, loginUser.getUserId() + "", JSONObject.toJSONString(loginUser));
    }


    @ApiOperation(value = "获取登录验证码", httpMethod = "GET", notes = "获取登录验证码", response = VerifyCodeVo.class)
    @RequestMapping(value = "/verifyCode", method = RequestMethod.GET)
    public Object verifyCode() throws IOException {
        ResponseBuilder builder = new ResponseBuilder();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String verifyCode = VerifyCodeUtil.getVerifyCodeImage(outputStream);
        String retImage;
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray())) {
            retImage = ImageUtil.base64EncodeImage(inputStream, "JPEG");
        }
        String reqId = UUID.randomUUID().toString().replaceAll("-", "");
        logger.info("the verify code is : " + verifyCode + "  reqId is " + reqId);
        VerifyCodeVo vo = new VerifyCodeVo();
        vo.setReqId(reqId);
        vo.setCode(retImage);
        //保存进redis,以便验证
        saveVerifyCode(reqId, verifyCode);
        return builder.success().data(vo).build();
    }

    private boolean checkVerifyCode(String reqId, String code) {
        VcodeTime vcodeTime = getVcodeAndTime(reqId);
        if (vcodeTime == null) {
            return false;
        }
        //验证码只能用一次,要立马删除
        redisService.hDel(RedisKeyConstant.VERIFY_CODE, reqId);
        if (System.currentTimeMillis() - vcodeTime.getRequestTime() > verifyCodeExpiredTime) {
            return false;
        }
        if (!StringUtils.equalsIgnoreCase(code, vcodeTime.getVerifyCode())) {
            return false;
        }
        return true;
    }

    private void saveVerifyCode(String reqId, String code) {
        redisService.hSet(RedisKeyConstant.VERIFY_CODE, reqId, code + "-" + System.currentTimeMillis());
    }

    /**
     * 获取上次付款申请请求时间
     */
    private VcodeTime getVcodeAndTime(String reqId) {
        String cacheCode = redisService.hGet(RedisKeyConstant.VERIFY_CODE, reqId);
        if (StringUtils.isBlank(cacheCode))
            return null;
        String[] arr = cacheCode.split("-");
        VcodeTime vt = new VcodeTime(arr[0], Long.parseLong(arr[1]));
        return vt;
    }

}
