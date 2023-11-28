package org.loggers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommonsLoggingLog4j {
    private static Log log = LogFactory.getLog(CommonsLoggingLog4j.class);

    public static void main(String[] args) {
    	log.trace("trace log");
        log.debug("debug log");
        log.info("info log");
        log.warn("warn log");
        log.error("error log");
        log.fatal("fatal log");
    }
}