package com.spring.common.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.spring.common.util.AppUtil;
import com.spring.log.model.LogEntity;
import com.spring.log.service.LogService;
import com.spring.log.service.PerLogging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Aspect
@Slf4j
public class PerfAspect {

	private final LogService logService;

	@AfterReturning(value =  "@annotation(PerLogging)", returning = "returnValue")
	public void annotationLogPerf(JoinPoint jp, Object returnValue) throws RuntimeException {
		log.info("AOP : {}", jp.getThis());
		MethodSignature methodSignature = (MethodSignature) jp.getSignature();
		PerLogging logging = methodSignature.getMethod().getAnnotation(PerLogging.class);
		
		logService.addLog(LogEntity.builder()
									.log(logging.value().getName())
									.registrant(AppUtil.getUser())
									.build());
	}
}
