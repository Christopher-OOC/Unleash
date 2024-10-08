package com.example.demo;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

public class CustomApplicationContext implements ApplicationContextAware {
	
	private static ApplicationContext CONTEXT;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		CONTEXT = applicationContext;
	}
	
	public static Object getServiceBean(String beanName) {
		return CONTEXT.getBean(beanName);
	}

}
