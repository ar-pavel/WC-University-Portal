package bd.edu.seu.employeeapi.exceptions;

public class ResourceDoesNotExistException extends Exception {
    public ResourceDoesNotExistException(String resource) {
        super(resource + " does not exist!");
    }
}