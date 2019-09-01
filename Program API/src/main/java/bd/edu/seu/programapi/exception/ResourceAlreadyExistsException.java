package bd.edu.seu.programapi.exception;

public class ResourceAlreadyExistsException extends Exception {
    public ResourceAlreadyExistsException(String resource) {
        super(resource + " already exists!");
    }
}