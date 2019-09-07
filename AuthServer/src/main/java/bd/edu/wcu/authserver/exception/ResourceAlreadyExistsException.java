package bd.edu.wcu.authserver.exception;

public class ResourceAlreadyExistsException extends Exception {
    public ResourceAlreadyExistsException(String resource) {
        super(resource + " already exists!");
    }
}