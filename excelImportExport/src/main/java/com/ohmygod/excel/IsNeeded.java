package com.ohmygod.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author : Dingm
 * Description :�Ƿ���Ҫ�ӽ���excel��ֵ
 * Create : 2020-04-11 ���� 5:22
 */

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
public @interface IsNeeded {

    /**
     * �Ƿ���Ҫ�ӽ���excel��ֵ
     * @return
     *         true:��Ҫ  false:����Ҫ
     * @see [�ࡢ��#��������#��Ա]
     */
    boolean isNeeded() default true;
}
