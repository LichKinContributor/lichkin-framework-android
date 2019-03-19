package org.apache.commons.logging;

public class LogFactory {

    public static Log getLog(Class<?> clazz) {
        return new Log() {
            @Override
            public boolean isDebugEnabled() {
                return true;
            }

            @Override
            public boolean isErrorEnabled() {
                return true;
            }

            @Override
            public boolean isFatalEnabled() {
                return true;
            }

            @Override
            public boolean isInfoEnabled() {
                return true;
            }

            @Override
            public boolean isTraceEnabled() {
                return true;
            }

            @Override
            public boolean isWarnEnabled() {
                return true;
            }

            @Override
            public void trace(Object message) {
                log("trace", message, null);
            }

            @Override
            public void trace(Object message, Throwable t) {
                log("trace", message, t);
            }

            @Override
            public void debug(Object message) {
                log("debug", message, null);
            }

            @Override
            public void debug(Object message, Throwable t) {
                log("debug", message, t);
            }

            @Override
            public void info(Object message) {
                log("info", message, null);
            }

            @Override
            public void info(Object message, Throwable t) {
                log("info", message, t);
            }

            @Override
            public void warn(Object message) {
                log("warn", message, null);
            }

            @Override
            public void warn(Object message, Throwable t) {
                log("warn", message, t);
            }

            @Override
            public void error(Object message) {
                log("error", message, null);
            }

            @Override
            public void error(Object message, Throwable t) {
                log("error", message, t);
            }

            @Override
            public void fatal(Object message) {
                log("fatal", message, null);
            }

            @Override
            public void fatal(Object message, Throwable t) {
                log("fatal", message, t);
            }

            private void log(String type, Object message, Throwable t) {
                System.out.println(type + " -> " + (message == null ? "" : message.toString()) + " -> " + (t == null ? "" : t.getMessage()));
            }
        };
    }

}