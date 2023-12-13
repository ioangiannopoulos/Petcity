package gr.aueb.cf.petcity.service.exceptions;

public class EntityNotFoundException extends Exception {
    private static final long serialVersionUID = 12345L;

    public EntityNotFoundException(Class<?> entityClass, Long id) {
        super("Entity " + entityClass.getSimpleName() + " with id: " + id + " does not exist");
    }

}
