package com.ht.lc.dcp.common.annotation;

import com.ht.lc.dcp.common.constants.CipherConst;
import java.lang.annotation.*;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-08-05 18:40
 * @Version 1.0
 **/

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Sensitive {

    String algorithm() default CipherConst.AES_GCM_256;

}
