package ca.ulaval.glo4002.ui;

public class ServiceAlreadyRegisteredException extends RuntimeException {
	public <T> ServiceAlreadyRegisteredException(Class<T> contract) {
		super("A service implementation was already provided for " + contract.getSimpleName());
	}

	private static final long serialVersionUID = -7271305540010437237L;
}