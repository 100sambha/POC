package com.loggers;

import org.slf4j.Logger;

public class TestApp
{
	
//	public static @InjectLogger Logger LOGGER;
	
	private static Logger logger = GlobalLogger.getLogger(TestApp.class);

	public static void main(String[] args)
	{
		Employee employee = new Employee();
		
		logger.info("Start Main");
		employee.emp();
		logger.info("Start End");
		
	}
}
