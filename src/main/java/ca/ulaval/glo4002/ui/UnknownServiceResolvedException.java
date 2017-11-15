package ca.ulaval.glo4002.ui;

public class UnknownServiceResolvedException extends RuntimeException {
	public <T> UnknownServiceResolvedException(Class<T> contract) {
		super("No implementation was registered for contract " + contract.getSimpleName());
	}

	private static final long serialVersionUID = 7717757493487590732L;
}