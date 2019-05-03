package pizza.nikiforov.exception;

public class ImaggaProcessException extends RuntimeException {
    public ImaggaProcessException(String message, Exception e) {
        super(message, e);
    }
}
