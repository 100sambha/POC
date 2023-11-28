package org.loggers;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class Log4JApp
{
	private static final Logger log = LogManager.getLogger(Log4JApp.class);
    public static void main( String[] args )
    {
        System.out.println( "1Hello World!" );
        log.fatal("fatal log");
        log.info("info log");
        log.warn("warn log");
        log.debug("debug log");
        log.error("error log");
        log.trace("trace log");
    }
}
