package com.kodlamaio.rentACar.core.utilities.aspects;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

@Aspect
@Component
public class LoggingAspect {
//	Logger log = LoggerFactory.getLogger(LoggingAspect.class);
	@Pointcut("execution(* com.kodlamaio.rentACar.business.concretes.*.*(..))")
	public void myPointcut() {

	}

	@Around("myPointcut()")
	public void applicationLogger(ProceedingJoinPoint pjp) throws Throwable {
		
		ObjectMapper mapper = new ObjectMapper();
		String methodName = pjp.getSignature().getName();
		String className = pjp.getTarget().getClass().getSimpleName().toString();
		Object[] array = pjp.getArgs();
//		log.info("date:" + LocalDate.now().getYear() + "\n" + "className " + className + "\n" + " : " + methodName
//				+ "()" + "arguments : " + mapper.writeValueAsString(array));
		Object object = pjp.proceed();
//		log.info(className + " : " + methodName + "()" + "Response : " + mapper.writeValueAsString(object));
		File file = new File("C:\\logs\\operations.json");
		try (BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(file, true))) {
			bufferWriter.write("\"date\":" + LocalDate.now().getYear());
			bufferWriter.newLine();
			bufferWriter.write("\"className\":" + className);
			bufferWriter.newLine();
			bufferWriter.write("\"methodName\":" + methodName);
			bufferWriter.newLine();
			bufferWriter.write("\"parameters\":" + mapper.writeValueAsString(object));
			bufferWriter.newLine();
		} catch (IOException e) { 
			System.out.println("Unable to read file " + file.toString());
		}

	}
}
