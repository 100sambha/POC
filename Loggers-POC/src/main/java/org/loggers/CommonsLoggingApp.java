package org.loggers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommonsLoggingApp
{
	private static Log log = LogFactory.getLog(CommonsLoggingApp.class);
	
	public static void main(String[] args)
	{
		log.trace("trace log");
        log.debug("debug log");
        log.info("info log");
        log.warn("warn log");
        log.error("error log");
        log.fatal("fatal log");
	}
}
