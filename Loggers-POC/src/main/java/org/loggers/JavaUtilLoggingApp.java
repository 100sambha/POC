package org.loggers;

import java.util.logging.Logger;

public class JavaUtilLoggingApp
{
	private static Logger LOGGER;
	
    static
    {
    	String path = JavaUtilLoggingApp.class.getClassLoader().getResource("logging.properties").getFile();
    	System.setProperty("java.util.logging.config.file", path);
        LOGGER = Logger.getLogger(JavaUtilLoggingApp.class.getName());
    }
	
	public static void main(String[] args)
	{
		LOGGER.info("an info msg");
        LOGGER.warning("a warning msg");
        LOGGER.severe("a severe msg");
	}
}