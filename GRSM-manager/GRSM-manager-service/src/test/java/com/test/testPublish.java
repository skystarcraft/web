package com.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class testPublish {

	@Test
	public void testPublish() throws Exception {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*");
		System.out.println("服务已启动...");
		System.in.read();
		System.out.println("服务已关闭...");
	}
}
