package com.vorspack.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class LogTool {
    public static Logger getLog() {
        return LogManager.getLogger(LogTool.class);
    }
}
