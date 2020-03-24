package com.example.redisdemo.service.idempotent.impl;

import com.example.redisdemo.exception.BusinessSilentException;
import com.example.redisdemo.service.idempotent.TokenService;
import com.example.redisdemo.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {
    private final static String TOKEN_PREFIX = "TOKEN_";
    private final static String TOKEN_NAME = "token";
    @Autowired
    RedisUtil redisUtil;
    @Override
    public String createToken() {

        String str = UUID.randomUUID().toString();
        StrBuilder token = new StrBuilder();
        try {
            token.append(TOKEN_PREFIX).append(str);
            redisUtil.set(token.toString(), token.toString(), 10000L);

            boolean notEmpty = StringUtils.isNotEmpty(token.toString());

            if (notEmpty) {
                return token.toString();
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean checkToken(HttpServletRequest request) throws Exception {

        String token = request.getHeader(TOKEN_NAME);
        if (StringUtils.isBlank(token)) {
            // header中不存在token
            token = request.getParameter(TOKEN_NAME);
            if (StringUtils.isBlank(token)) {
                // parameter中也不存在token
                throw new BusinessSilentException(-1,"禁止重复提交");
            }
        }
        if (!redisUtil.hasKey(token)) {
            throw new BusinessSilentException(-1,"token不存在");
        }

        boolean remove = redisUtil.del(token);

        if
        (!remove) {

            throw new BusinessSilentException(500,"操作异常");
        }
        return true;
    }
}
