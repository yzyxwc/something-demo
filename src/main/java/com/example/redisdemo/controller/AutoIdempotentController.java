package com.example.redisdemo.controller;

import com.example.redisdemo.annocation.AutoIdempotent;
import com.example.redisdemo.constant.Constant;
import com.example.redisdemo.service.idempotent.TokenService;
import com.example.redisdemo.util.JsonResult;
import com.example.redisdemo.util.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wc
 */
@RestController
@RequestMapping("local/idempotent")
@Api(description = "幂等-redis实现测试",tags = "幂等")
public class AutoIdempotentController {
    @Autowired
    TokenService tokenService;

    @PostMapping("/get/token")
    @ApiOperation(value = "测试redis获取token实现幂等",notes = "测试防刷",response = String.class)
    public String getToken(){
        String token = tokenService.createToken();
        if (StringUtils.isNotEmpty(token)) {
            JsonResult resultVo = new JsonResult<String>(Constant.CODE_SUCCESS,Constant.SUCCESS,token);
            return JsonUtils.obj2Json(resultVo);
        }

        return StringUtils.EMPTY;
    }

    @AutoIdempotent
    @GetMapping("/test/Idempotence")
    @ApiOperation(value = "测试redis获取token实现幂等",notes = "测试防刷",response = String.class)
    public String testIdempotence() {
        String businessResult = "true";

        if (StringUtils.isNotEmpty(businessResult)) {
            JsonResult resultVo = new JsonResult<String>(Constant.CODE_SUCCESS,businessResult);
            return JsonUtils.obj2Json(resultVo);
        }

        return StringUtils.EMPTY;
    }
}
