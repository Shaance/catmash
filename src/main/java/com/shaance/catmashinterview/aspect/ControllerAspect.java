package com.shaance.catmashinterview.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class ControllerAspect {

	@Before("within(com.shaance.catmashinterview.controller..*)")
	private void before(JoinPoint joinPoint) {
		String caller = joinPoint.getSignature().toShortString();
		log.info("{} method called with {}", caller, joinPoint.getArgs());
	}

}
