package songm.sso.exception;

public class UnauthorizedException extends SSOException {

    private static final long serialVersionUID = 9009246586428914948L;

    public UnauthorizedException(String message) {
        super(message);
    }
    
    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

}
