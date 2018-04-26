package com.iss.log;

import java.util.Arrays;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import com.iss.model.Employee;

@Aspect
@Configuration
public class LoggingHandler {
  Logger log = LoggerFactory.getLogger(this.getClass());

  @Pointcut("execution(* *.*(..))")
  protected void allMethod() {
    log.debug("Agya mein");
  }

  @Before("execution(* com.iss.service.impl.EmployeeServiceImpl.*(..))")
  public void beforeGetEmployeeById(JoinPoint joinPoint) {
    // Advice
    log.info(" Check for GetEmployeeById ");
    log.info("Arguments : " + Arrays.toString(joinPoint.getArgs()));
    log.info("Entering in Method : " + joinPoint.getSignature().getName());
    log.info("Target class : " + joinPoint.getTarget().getClass().getName());
  }


  @AfterReturning("execution(* com.iss.service.impl.EmployeeServiceImpl.*(..)) && args(..,result)")
  public void AfterGetEmployeeById(JoinPoint joinPoint, Object result) {
    String returnValue = this.getValue(result);
    log.info("Method Return value : " + returnValue);
  }

  // @Pointcut("within(com.iss.dao.impl.*)")
  // public void loggError() {}
  //
  // @AfterThrowing(pointcut = "loggError()", throwing = "e")
  // public void logErrorActionAdvice(final JoinPoint joinPoint, final Throwable e) {
  // String className = joinPoint.getTarget().getClass().getSimpleName();
  // log.info("Error in " + className);
  // }
  //
  // @Around("loggError()")
  // public void logAround(final JoinPoint joinPoint) throws Throwable {
  // String className = joinPoint.getTarget().getClass().getSimpleName();
  // log.error("Error in " + className);
  // }

  private String getValue(Object result) {
    String returnValue = null;
    if (null != result) {
      if (result.toString().endsWith("@" + Integer.toHexString(result.hashCode()))) {
        returnValue = ReflectionToStringBuilder.toString(result);
      } else {
        returnValue = result.toString();
      }
    }
    return returnValue;
  }

  // @Around("execution(* com.iss.service.impl.EmployeeServiceImpl.getEmployeeById(..))")
  // public void AroundGetEmployeeById(ProceedingJoinPoint joinPoint) {
  // Object retVal;
  // String returnValue;
  // try {
  // retVal = joinPoint.proceed();
  // returnValue = this.getValue(retVal);
  // log.info("Method Return value : " + returnValue);
  // } catch (Throwable e) {
  // e.printStackTrace();
  // }
  // }



  // @Pointcut("within(@org.springframework.stereotype.Controller *)")
  // public void controller() {
  // }
  //
  // @Pointcut("execution(* *.*(..))")
  // protected void allMethod() {
  // }
  //
  // @Pointcut("execution(public * *(..))")
  // protected void loggingPublicOperation() {
  // }
  //
  // @Pointcut("execution(* *.*(..))")
  // protected void loggingAllOperation() {
  // }
  //
  // @Pointcut("within(com.iss..*)")
  // private void logAnyFunctionWithinResource() {
  // }

  // before -> Any resource annotated with @Controller annotation
  // and all method and function taking HttpServletRequest as first parameter
  // @Before("controller() && allMethod() && args(..,request)")
  // public void logBefore(JoinPoint joinPoint, HttpServletRequest request) {
  //
  // log.debug("Entering in Method : " + joinPoint.getSignature().getName());
  // log.debug("Class Name : " + joinPoint.getSignature().getDeclaringTypeName());
  // log.debug("Arguments : " + Arrays.toString(joinPoint.getArgs()));
  // log.debug("Target class : " + joinPoint.getTarget().getClass().getName());
  //
  // if (null != request) {
  // log.debug("Start Header Section of request ");
  // log.debug("Method Type : " + request.getMethod());
  // Enumeration headerNames = request.getHeaderNames();
  // while (headerNames.hasMoreElements()) {
  // String headerName = (String) headerNames.nextElement();
  // String headerValue = request.getHeader(headerName);
  // log.debug("Header Name: " + headerName + " Header Value : " + headerValue);
  // }
  // log.debug("Request Path info :" + request.getServletPath());
  // log.debug("End Header Section of request ");
  // }
  // }

}
