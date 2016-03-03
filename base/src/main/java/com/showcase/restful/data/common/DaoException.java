package com.showcase.restful.data.common;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * @author: lei hang
 * @date: 2016年03月01日
 * @description:
 */


public class DaoException extends RuntimeException {

    private Throwable cause;

    public DaoException(String message) {
        super(message);
    }

    public DaoException(Throwable cause) {
        this.cause = cause;
    }

    public DaoException(String message, Throwable cause) {
        super(message);
        this.cause = cause;
    }

    public String getMessage() {
        StringBuffer message = new StringBuffer(super.getMessage());
        if (this.cause != null) {
            message.append("; DAO Exception is ").append(this.cause);
        }
        return message.toString();
    }

    public Throwable getCause() {
        return null == this.cause ? null : this.cause;
    }

    public void printStackTrace(PrintStream printStream) {
        if (null == getCause()) {
            super.printStackTrace(printStream);
        } else {
            printStream.println(this);
            getCause().printStackTrace(printStream);
        }
    }

    public void printStackTrace(PrintWriter printWriter) {
        if (null == getCause()) {
            super.printStackTrace(printWriter);
        } else {
            printWriter.println(this);
            getCause().printStackTrace(printWriter);
        }
    }
}
