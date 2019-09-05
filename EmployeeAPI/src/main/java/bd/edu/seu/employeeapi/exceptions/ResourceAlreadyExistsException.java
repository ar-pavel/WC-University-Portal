package bd.edu.seu.employeeapi.exceptions;

public class ResourceAlreadyExistsException extends Exception {
    public ResourceAlreadyExistsException(String resource) {
        super(resource + " already exists!");
    }
}