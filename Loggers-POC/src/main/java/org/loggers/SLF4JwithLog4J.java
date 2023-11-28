package org.loggers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SLF4JwithLog4J {
	
	private static Logger log = LoggerFactory.getLogger(SLF4JwithLog4J.class);
	public static void main(String[] args)
	{
		log.trace("trace log");
		log.debug("debug log");
		log.info("info log");
		log.warn("warn log");
		log.error("error log");
	}

}
