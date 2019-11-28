package com.example.redisdemo.selocal.desensitized;

import com.example.redisdemo.annocation.DesensitizedAnnotation;
import com.example.redisdemo.enums.TypeEnum;
import lombok.*;

import java.io.Serializable;

/**
 * BaseInfo 过滤字段基类
 * @author wc
 * @date 2019/10/29 09:42
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BaseInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @DesensitizedAnnotation(type = TypeEnum.PERSON_NAME)
    private String custName;

    @DesensitizedAnnotation(type = TypeEnum.PERSON_CERT_NO)
    private String certNo;
}