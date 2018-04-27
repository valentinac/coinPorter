package com.qidiancamp.exceptions;

/**
 * Exception to provide the following to API:
 *
 * <ul>
 *   <li>Indication that the exchange does not support the requested function or data
 * </ul>
 */
public class NotAvailableFromExchangeException extends UnsupportedOperationException {

  /**
   * Constructor
   *
   * @param message Message
   */
  private NotAvailableFromExchangeException(String message) {

    super(message);
  }

  /** Constructor */
  public NotAvailableFromExchangeException() {

    this("Requested Information from Exchange is not available.");
  }
}
