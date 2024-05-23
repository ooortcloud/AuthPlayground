package com.example.demo.common.aspect;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspectJ {

	public LogAspectJ() {
		
	}
	
	@Around("execution(* com.example.demo.service..*.*(..) )")
	public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
		
		System.out.println();
		System.out.println("[around before] target method ::" 
				+ joinPoint.getTarget().getClass().getSimpleName() + "." 
				+ joinPoint.getSignature().getName() );
		
		if(joinPoint.getArgs().length != 0) {
			System.out.println("[around before] target method�� ���޵Ǵ� argument �� = " + joinPoint.getArgs().length);
			System.out.println("[around before] target method�� ���޵Ǵ� argument :: " + Arrays.asList(joinPoint.getArgs()) );
		}
		
		System.out.println("method proceed...");
		Object result = joinPoint.proceed();
		
		System.out.println("[around after] ����� method�� return value :: " + result);
		System.out.println();
		
		return result;
	}
}
