package com.skythees.bowEngine.core.exceptions;

/**
 * Created on 06.03.15.
 */
@SuppressWarnings("WeakerAccess")
public class BowEngineException extends Exception
{
	@SuppressWarnings("unused")
	public BowEngineException()
	{
		super();
	}

	@SuppressWarnings("unused")
	public BowEngineException(String message)
	{
		super(message);
	}

	@SuppressWarnings("unused")
	public BowEngineException(String message, Throwable cause)
	{
		super(message, cause);
	}

	@SuppressWarnings("unused")
	public BowEngineException(Throwable cause)
	{
		super(cause);
	}

	@SuppressWarnings("unused")
	protected BowEngineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
