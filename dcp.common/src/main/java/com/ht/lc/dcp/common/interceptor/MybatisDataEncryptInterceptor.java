package com.ht.lc.dcp.common.interceptor;

import com.ht.lc.dcp.common.annotation.Sensitive;
import com.ht.lc.dcp.common.config.SystemConfig;
import com.ht.lc.dcp.common.crypto.CipherManager;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.Collections;
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
        @Signature(type = ParameterHandler.class, method = "setParameters", args = PreparedStatement.class)
})
@Component
public class MybatisDataEncryptInterceptor implements Interceptor {

    private static final Logger LOG = LoggerFactory.getLogger(MybatisDataEncryptInterceptor.class);

    @Autowired
    SystemConfig.KeyConfig keyConfig;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 处理参数
        ParameterHandler parameterHandler = (ParameterHandler) invocation.getTarget();
        Field parameterField = parameterHandler.getClass().getDeclaredField("parameterObject");
        parameterField.setAccessible(true);
        // 获取参数对象
        Object parameterObject = parameterField.get(parameterHandler);
        if (Objects.isNull(parameterObject)) {
            return invocation.proceed();
        }
        if (parameterObject instanceof MapperMethod.ParamMap) {
            List collectionObjects = (List) ((Map<String, Object>) parameterObject).getOrDefault("collection", Collections.emptyList());
            // 如果未获取到目标集合，直接退出
            if (!CollectionUtils.isEmpty(collectionObjects)) {
                for (Object collection : collectionObjects) {
                    encryptSensitiveField(collection);
                }
            }
        } else {
            encryptSensitiveField(parameterObject);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * 加密数据
     * @param paramObj
     */
    private void encryptSensitiveField(Object paramObj) {
        // 获取对象的类
        Class<?> objClass = paramObj.getClass();
        // 获取参数对象字段，循环判断是否有注解
        Field[] declaredFields = objClass.getDeclaredFields();
        for (Field f : declaredFields) {
            Sensitive sensitive = f.getAnnotation(Sensitive.class);
            // 指定加密算法且非空
            if (Objects.nonNull(sensitive) && StringUtils.hasText(sensitive.algorithm())) {
                try {
                    f.setAccessible(true);
                    Object obj = f.get(paramObj);
                    // 设置加密结果
                    if (obj instanceof String) {
                        String encryptStr = CipherManager.getInstance().encrypt(sensitive.algorithm(), (String)obj,
                                keyConfig.getAesKey(), keyConfig.getAesIv());
                        f.set(paramObj, encryptStr);
                    }
                } catch (Throwable t) {
                    LOG.error("encrypt sensitive data fiaied, msg: {} .", t.getMessage());
                }
            }
        }
    }
}
