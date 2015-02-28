package osky.core.exception;

import osky.Constants;

import java.io.PrintStream;
import java.io.PrintWriter;

public class UncheckedException extends RuntimeException {
    /**
     * Divider between causes printouts.
     */
    protected static final String CAUSE_DIV = "---[cause]------------------------------------------------------------------------";
    private static final long serialVersionUID = 1L;
    protected final Throwable cause;
    /**
     * If set to <code>true</code> stack trace will be enhanced with cause's
     * stack traces.
     */
    protected final boolean showCauseDetails;

    // ----------------------------------------------------------------

    // constructors
    protected int code = Constants.ERROR_CODE.FAILURE;


    public UncheckedException() {
        super();
        cause = null;
        this.showCauseDetails = false;
    }

    public UncheckedException(String message) {
        super(message);
        cause = null;
        this.showCauseDetails = false;
    }

    public UncheckedException(int code) {
        super();
        cause = null;
        this.code = code;
        this.showCauseDetails = false;
    }

    public UncheckedException(int code, String message) {
        super(message);
        cause = null;
        this.code = code;
        this.showCauseDetails = false;
    }


    public UncheckedException(Throwable t) {
        super(t);
        cause = t;
        this.showCauseDetails = true;
    }

    public UncheckedException(String message, Throwable t) {
        super(message, t);
        cause = t;
        this.showCauseDetails = true;
    }


    public UncheckedException(int code, Throwable t) {
        super(t);
        cause = t;
        this.code = code;
        this.showCauseDetails = true;
    }


    // ---------------------------------------------------------------- txt

    /**
     * Wraps checked exceptions in a <code>UncheckedException</code>. Unchecked
     * exceptions are not wrapped.
     */
    public static RuntimeException wrapChecked(Throwable t) {
        if (t instanceof RuntimeException) {
            return (RuntimeException) t;
        }
        return new UncheckedException(t);
    }

    // ---------------------------------------------------------------- wrap

    /**
     * Wraps all exceptions in a <code>UncheckedException</code>
     */
    public static RuntimeException wrap(Throwable t) {
        return new UncheckedException(t);
    }

    /**
     * Wraps all exceptions in a <code>UncheckedException</code>
     */
    public static RuntimeException wrap(Throwable t, String message) {
        return new UncheckedException(message, t);
    }

    @Override
    public void printStackTrace() {
        printStackTrace(System.err);
    }

    // ---------------------------------------------------------------- cause

    @Override
    public void printStackTrace(PrintStream ps) {
        synchronized (ps) {
            super.printStackTrace(ps);
            if ((cause != null) && showCauseDetails) {
                Throwable rootCause = ExceptionKit.getRootCause(cause);
                ps.println(CAUSE_DIV);
                rootCause.printStackTrace(ps);
            }
        }
    }

    @Override
    public void printStackTrace(PrintWriter pw) {
        synchronized (pw) {
            super.printStackTrace(pw);
            if ((cause != null) && showCauseDetails) {
                Throwable rootCause = ExceptionKit.getRootCause(cause);
                pw.println(CAUSE_DIV);
                rootCause.printStackTrace(pw);
            }
        }
    }

    /**
     * Returns the detail message, including the message from the nested
     * exception if there is one.
     */
    @Override
    public String getMessage() {
        return ExceptionKit.buildMessage(super.getMessage(), cause);
    }

    /**
     * Re-throws cause if exists.
     */
    public void rethrow() throws Throwable {
        if (cause == null) {
            return;
        }
        throw cause;
    }

    /**
     * Returns exception cause.
     */
    @Override
    public Throwable getCause() {
        return cause;
    }


    public int getCode() {
        return code;
    }
}
