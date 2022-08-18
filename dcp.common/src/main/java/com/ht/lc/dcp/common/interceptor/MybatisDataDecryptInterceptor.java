package com.ht.lc.dcp.common.interceptor;

import com.ht.lc.dcp.common.annotation.Sensitive;
import com.ht.lc.dcp.common.config.SystemConfig;
import com.ht.lc.dcp.common.crypto.CipherManager;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-08-09 08:59
 * @Version 1.0
 **/

@Intercepts({
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = Statement.class)
})
@Component
public class MybatisDataDecryptInterceptor implements Interceptor {

    private static final Logger LOG = LoggerFactory.getLogger(MybatisDataDecryptInterceptor.class);

    @Autowired
    SystemConfig.KeyConfig keyConfig;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //取出查询的结果
        Object resultObject = invocation.proceed();
        if (Objects.nonNull(resultObject)) {
            //基于selectList
            if (resultObject instanceof ArrayList) {
                ArrayList resultList = (ArrayList) resultObject;
                if (!CollectionUtils.isEmpty(resultList)){
                    for (Object result : resultList) {
                        //逐一解密
                        decryptSensitiveField(result);
                    }
                }
            } else {
                decryptSensitiveField(resultObject);
            }
        }
        return resultObject;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * 解密数据
     * @param result
     */
    private void decryptSensitiveField(Object result) {
        // 获取对象的类
        Class<?> resultClass = result.getClass();
        // 获取参数对象字段，循环判断是否有注解
        Field[] declaredFields = resultClass.getDeclaredFields();
        for (Field f : declaredFields) {
            Sensitive sensitive = f.getAnnotation(Sensitive.class);
            // 指定加密算法且非空
            if (Objects.nonNull(sensitive) && StringUtils.hasText(sensitive.algorithm())) {
                try {
                    f.setAccessible(true);
                    Object obj = f.get(result);
                    // 设置解密结果
                    if (obj instanceof String) {
                        String decryptStr = CipherManager.getInstance().decrypt(sensitive.algorithm(), (String)obj,
                                keyConfig.getAesKey(), keyConfig.getAesIv());
                        f.set(result, decryptStr);
                    }
                } catch (Throwable t) {
                    LOG.error("decrypt sensitive data fiaied, msg: {} .", t.getMessage());
                }
            }
        }
    }
}
