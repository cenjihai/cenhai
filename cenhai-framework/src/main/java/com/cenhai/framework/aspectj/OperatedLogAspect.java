package com.cenhai.framework.aspectj;

import com.alibaba.fastjson.JSON;
import com.cenhai.common.constant.Constants;
import com.cenhai.common.utils.IpUtils;
import com.cenhai.common.utils.ServletUtils;
import com.cenhai.common.utils.StringUtils;
import com.cenhai.framework.annotation.OperatedLog;
import com.cenhai.framework.security.SecurityUtils;
import com.cenhai.framework.security.SystemUserDetails;
import com.cenhai.system.domain.SysOperlog;
import com.cenhai.system.service.SysOperlogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 操作日志aop
 */
@Aspect
@Component
public class OperatedLogAspect {

    private static final Logger logger = LoggerFactory.getLogger("sys-operlog");

    @Autowired
    private SysOperlogService operlogService;

    /**
     * 处理完请求后执行
     * @param joinPoint
     * @param operatedLog
     * @param obj
     */
    @AfterReturning(pointcut = "@annotation(operatedLog)", returning = "obj")
    public void doAfterReturning(JoinPoint joinPoint, OperatedLog operatedLog, Object obj){
        logHandler(joinPoint, operatedLog,null,obj);
    }

    /**
     * 拦截异常
     * @param joinPoint
     * @param operatedLog
     * @param e
     */
    @AfterThrowing(value = "@annotation(operatedLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, OperatedLog operatedLog, Exception e){
        logHandler(joinPoint, operatedLog, e,null);
    }

    private void logHandler(final JoinPoint joinPoint, OperatedLog operatedLog, final Exception e, Object obj){
        SysOperlog operlog = new SysOperlog();
        try {
            SystemUserDetails userDetails = SecurityUtils.getUserDetails();
            if (userDetails != null) {
                operlog.setUserId(userDetails.getUserId());
                operlog.setUserDetails(userDetails.getAccessToken()+ "-" + userDetails.getIpaddr());
            }
            operlog.setStatus(Constants.NORMAL);
            operlog.setOperIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
            operlog.setOperUrl(ServletUtils.getRequest().getRequestURI());
            if (e != null){
                operlog.setErrMsg(StringUtils.substring(e.getMessage(), 0, 2000));
                operlog.setStatus(Constants.DISABLE);
            }
            operlog.setAction(joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()");
            Map<String,String> params = getRequestParams(joinPoint);
            params.put("response", StringUtils.substring(JSON.toJSONString(obj), 0, 2000));
            operlog.setParams(JSON.toJSONString(params));
        }catch (Exception e1){
            logger.error("日志记录错误", e1);
            operlog.setErrMsg(StringUtils.substring(e1.getMessage(), 0, 2000));
            operlog.setStatus(Constants.DISABLE);
        }
        operlog.setTitle(operatedLog.title());
        operlog.setInfo(operatedLog.info());
        operlogService.save(operlog);
    }

    /**
     * 获取请求参数
     * @param joinPoint
     * @return
     */
    private Map<String, String> getRequestParams(JoinPoint joinPoint)
    {
        Map<String,String> map = new HashMap<>();
        String requestMethod = ServletUtils.getRequest().getMethod();
        map.put("method", requestMethod);
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod))
        {
            String params = argsArrayToString(joinPoint.getArgs());
            map.put("request",StringUtils.substring(params, 0, 2000));
        }
        else
        {
            Map<?, ?> paramsMap = (Map<?, ?>) ServletUtils.getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            map.put("request",StringUtils.substring(paramsMap.toString(), 0, 2000));
        }
        return map;
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray)
    {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0)
        {
            for (Object o : paramsArray)
            {
                if (StringUtils.isNotNull(o) && !isFilterObject(o))
                {
                    try
                    {
                        Object jsonObj = JSON.toJSON(o);
                        params += jsonObj.toString() + " ";
                    }
                    catch (Exception e)
                    {
                    }
                }
            }
        }
        return params.trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o)
    {
        Class<?> clazz = o.getClass();
        if (clazz.isArray())
        {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        }
        else if (Collection.class.isAssignableFrom(clazz))
        {
            Collection collection = (Collection) o;
            for (Object value : collection)
            {
                return value instanceof MultipartFile;
            }
        }
        else if (Map.class.isAssignableFrom(clazz))
        {
            Map map = (Map) o;
            for (Object value : map.entrySet())
            {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }
}
