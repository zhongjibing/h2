package com.icezhg.h2.config;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.util.StringUtils;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;

/**
 * JsonArgumentResolver:
 *
 * @author zhongjibing 2017-10-09
 * @version 1.0
 */
public class JsonArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String parameterName = parameter.getParameterName();
        return mavContainer.containsAttribute(parameterName) ?
                mavContainer.getModel().get(parameterName) : createAttribute(parameterName, parameter, binderFactory, webRequest);
    }

    /**
     * 根据参数attributeName获取请求的值
     *
     * @param attributeName 请求参数
     * @param parameter     method 参数对象
     * @param binderFactory 数据绑定工厂
     * @param request       请求对象
     *
     * @return
     *
     * @throws Exception
     */
    protected Object createAttribute(String attributeName, MethodParameter parameter,
                                     WebDataBinderFactory binderFactory, NativeWebRequest request) throws Exception {
        /**
         * 获取attributeName的值
         */
        String value = getRequestValueForAttribute(attributeName, request);

        /**
         * 如果存在值
         */
        if (value != null) {
            /**
             * 进行类型转换
             * 检查请求的类型与目标参数类型是否可以进行转换
             */
            Object attribute = convertAttributeToParameterValue(value, attributeName, parameter, binderFactory, request);
            /**
             * 如果存在转换后的值，则返回
             */
            if (attribute != null) {
                return attribute;
            }
        }
        /**
         * 检查request parameterMap 内是否存在以attributeName作为前缀的数据
         * 如果存在则根据字段的类型来进行设置值、集合、数组等
         */
        else {
            Object attribute = putParameters(parameter, request);
            if (attribute != null) {
                return attribute;
            }
        }
        /**
         * 如果以上两种条件不符合，直接返回初始化参数类型的空对象
         */
        return BeanUtils.instantiateClass(parameter.getParameterType());
    }

    /**
     * 将attribute的值转换为parameter参数值类型
     *
     * @param sourceValue   源请求值
     * @param attributeName 参数名
     * @param parameter     目标参数对象
     * @param binderFactory 数据绑定工厂
     * @param request       请求对象
     *
     * @return
     *
     * @throws Exception
     */
    protected Object convertAttributeToParameterValue(String sourceValue,
                                                      String attributeName,
                                                      MethodParameter parameter,
                                                      WebDataBinderFactory binderFactory,
                                                      NativeWebRequest request) throws Exception {
        /**
         * 获取类型转换业务逻辑实现类
         */
        DataBinder binder = binderFactory.createBinder(request, null, attributeName);
        ConversionService conversionService = binder.getConversionService();
        if (conversionService != null) {
            /**
             * 源类型描述
             */
            TypeDescriptor source = TypeDescriptor.valueOf(String.class);
            /**
             * 根据目标参数对象获取目标参数类型描述
             */
            TypeDescriptor target = new TypeDescriptor(parameter);
            /**
             * 验证是否可以进行转换
             */
            if (conversionService.canConvert(source, target)) {
                /**
                 * 返回转换后的值
                 */
                return binder.convertIfNecessary(sourceValue, parameter.getParameterType(), parameter);
            }
        }
        return null;
    }

    /**
     * 从request parameterMap集合内获取attributeName的值
     *
     * @param attributeName 参数名称
     * @param request       请求对象
     *
     * @return
     */
    protected String getRequestValueForAttribute(String attributeName, NativeWebRequest request) {
        /**
         * 获取PathVariables参数集合
         */
        Map<String, String> variables = getUriTemplateVariables(request);
        /**
         * 如果PathVariables参数集合内存在该attributeName
         * 直接返回相对应的值
         */
        if (StringUtils.hasText(variables.get(attributeName))) {
            return variables.get(attributeName);
        }
        /**
         * 如果request parameterMap内存在该attributeName
         * 直接返回相对应的值
         */
        else if (StringUtils.hasText(request.getParameter(attributeName))) {
            return request.getParameter(attributeName);
        }
        //不存在时返回null
        else {
            return null;
        }
    }

    /**
     * 获取指定前缀的参数：包括uri varaibles 和 parameters
     *
     * @param namePrefix
     * @param request
     *
     * @return
     *
     * @subPrefix 是否截取掉namePrefix的前缀
     */
    protected Map<String, String[]> getPrefixParameterMap(String namePrefix, NativeWebRequest request, boolean subPrefix) {
        Map<String, String[]> result = new HashMap();
        /**
         * 从PathVariables内获取该前缀的参数列表
         */
        Map<String, String> variables = getUriTemplateVariables(request);

        int namePrefixLength = namePrefix.length();
        for (String name : variables.keySet()) {
            if (name.startsWith(namePrefix)) {

                //page.pn  则截取 pn
                if (subPrefix) {
                    char ch = name.charAt(namePrefix.length());
                    //如果下一个字符不是 数字 . _  则不可能是查询 只是前缀类似
                    if (illegalChar(ch)) {
                        continue;
                    }
                    result.put(name.substring(namePrefixLength + 1), new String[]{variables.get(name)});
                } else {
                    result.put(name, new String[]{variables.get(name)});
                }
            }
        }

        /**
         * 从request parameterMap集合内获取该前缀的参数列表
         */
        Iterator<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasNext()) {
            String name = parameterNames.next();
            if (name.startsWith(namePrefix)) {
                //page.pn  则截取 pn
                if (subPrefix) {
                    char ch = name.charAt(namePrefix.length());
                    //如果下一个字符不是 数字 . _  则不可能是查询 只是前缀类似
                    if (illegalChar(ch)) {
                        continue;
                    }
                    result.put(name.substring(namePrefixLength + 1), request.getParameterValues(name));
                } else {
                    result.put(name, request.getParameterValues(name));
                }
            }
        }

        return result;
    }

    /**
     * 验证参数前缀是否合法
     *
     * @param ch
     *
     * @return
     */
    private boolean illegalChar(char ch) {
        return ch != '.' && ch != '_' && !(ch >= '0' && ch <= '9');
    }

    /**
     * 获取PathVariables集合
     *
     * @param request 请求对象
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    protected final Map<String, String> getUriTemplateVariables(NativeWebRequest request) {
        Map<String, String> variables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
        return (variables != null) ? variables : Collections.emptyMap();
    }

    /**
     * 从request内获取parameter前缀的所有参数
     * 并根据parameter的类型将对应字段的值设置到parmaeter对象内并返回
     *
     * @param parameter
     * @param request
     *
     * @return
     */
    protected Object putParameters(MethodParameter parameter, NativeWebRequest request) {
        /**
         * 根据请求参数类型初始化空对象
         */
        Object object = BeanUtils.instantiateClass(parameter.getParameterType());
        /**
         * 获取指定前缀的请求参数集合
         */
        Map<String, String[]> parameters = getPrefixParameterMap(parameter.getParameterName(), request, true);
        Iterator<String> iterator = parameters.keySet().iterator();
        while (iterator.hasNext()) {
            //字段名称
            String fieldName = iterator.next();
            //请求参数值
            String[] parameterValue = parameters.get(fieldName);
            try {
                Field field = object.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);

                //字段的类型
                Class<?> fieldTargetType = field.getType();

                /**
                 * List（ArrayList、LinkedList）类型
                 * 将数组类型的值转换为List集合对象
                 */
                if (List.class.isAssignableFrom(fieldTargetType)) {
                    field.set(object, Arrays.asList(parameterValue));
                }
                /**
                 *Object数组类型，直接将数组值设置为目标字段的值
                 */
                else if (Object[].class.isAssignableFrom(fieldTargetType)) {
                    field.set(object, parameterValue);
                }
                /**
                 * 单值时获取数组索引为0的值
                 */
                else {
                    field.set(object, parameterValue[0]);
                }
            } catch (Exception e) {
                continue;
            }
        }
        return object;
    }

}
