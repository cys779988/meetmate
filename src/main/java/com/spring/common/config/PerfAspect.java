package com.spring.common.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class PerfAspect {

	/*
	@Around("execution(* com.spring..*.BoardService.*(..))")
	public Object logPerf(ProceedingJoinPoint pjp) throws Throwable {
		long begin = System.currentTimeMillis();
		Object retVal = pjp.proceed(); // 메서드 호출 자체를 감쌈
		long procTime = System.currentTimeMillis() - begin;
		System.out.println("실행시간 : " + procTime);
		return retVal;
	}
	*/
	
	@Around("@annotation(PerLogging)")
	public Object annotationLogPerf(ProceedingJoinPoint pjp) throws Throwable{
		log.info("AOP : {}", pjp.getThis());
		long begin = System.currentTimeMillis();
		Object retVal = pjp.proceed(); // 메서드 호출 자체를 감쌈
		long time = System.currentTimeMillis() - begin;
		System.out.println("aop = " + time);
		return retVal;
	}

		
	/*
	@Around("bean(boardService)")
	public Object beanLogPerf(ProceedingJoinPoint pjp) throws Throwable {
		long begin = System.currentTimeMillis();
		Object retVal = pjp.proceed(); // 메서드 호출 자체를 감쌈
		System.out.println(System.currentTimeMillis() - begin);
		return retVal;
	}
	*/
}
