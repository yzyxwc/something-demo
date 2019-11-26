package com.example.redisdemo.util.covert;

import javax.persistence.AttributeConverter;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

/**
 * @author wc
 */
public abstract class BaseEnumValueConverter<E extends ValuableEnum> implements AttributeConverter<E, Integer> {
    private Class<E> clz;
    private Method method;
    @Override
    public Integer convertToDatabaseColumn(ValuableEnum attribute) {
        return attribute == null ? null : attribute.getValue();
    }
    @Override
    public E convertToEntityAttribute(Integer dbData) {
        return this.valueOf(dbData);
    }

    public BaseEnumValueConverter() {
        try {
            this.clz = (Class)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            this.method = this.clz.getMethod("values");
        } catch (Exception var2) {
            throw new RuntimeException("反射失败", var2);
        }
    }

    public E valueOf(Integer value) {
        if (value == null) {
            return null;
        } else {
            try {
                this.method = this.clz.getMethod("values");
                ValuableEnum[] var2 = (ValuableEnum[])((ValuableEnum[])this.method.invoke((Object)null));
                int var3 = var2.length;

                for(int var4 = 0; var4 < var3; ++var4) {
                    E e = (E)var2[var4];
                    if (e.getValue() == value) {
                        return e;
                    }
                }

                return null;
            } catch (Exception var6) {
                throw new RuntimeException("反射失败", var6);
            }
        }
    }
}
