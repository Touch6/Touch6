package com.touch6.core.exception;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * 异常信息自定义标签解析类
 *
 */
public class ErrorParser extends AbstractSimpleBeanDefinitionParser {
    /**
     * element 相当于对应的element元素<br>
     * parserContext 解析的上下文<br>
     * builder 用于该标签的实现<br>
     */
    protected void doParse(Element element, ParserContext parserContext,
                           BeanDefinitionBuilder builder) {
        // 从标签中取出对应的属性值
        String code = element.getAttribute("code");
        String info = element.getAttribute("info");
        String des = element.getAttribute("des");
        // 把创建完的实例对应的传到该标签类实现的相应属性中
        builder.addPropertyValue("code", code);
        builder.addPropertyValue("info", info);
        builder.addPropertyValue("des", des);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected Class getBeanClass(Element element) {
        // 返回该标签所定义的类实现,在这里是为了创建出Error对象
        return Error.class;
    }
}
