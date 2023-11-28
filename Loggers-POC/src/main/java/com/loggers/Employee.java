package com.loggers;

import org.slf4j.Logger;

public class Employee
{
	private static Logger logger = GlobalLogger.getLogger(TestApp.class);

	public void emp()
	{
		logger.info("TESTING...");
		logger.info("Jay");
		logger.info("Naik");
		logger.trace("Tracing");
		logger.debug("Debugging Start");
		logger.error("Yes Error Coming");
		logger.warn("Last Warning");
	}
}