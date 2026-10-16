package tn.esprit.autoloc.exception;

public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException() {
    super("Ressource introuvable");
  }

  public ResourceNotFoundException(String message) {
    super(message);
  }

  public ResourceNotFoundException(String resourceName, Long resourceId) {
    super(resourceName + " introuvable avec l'identifiant " + resourceId);
  }
}
