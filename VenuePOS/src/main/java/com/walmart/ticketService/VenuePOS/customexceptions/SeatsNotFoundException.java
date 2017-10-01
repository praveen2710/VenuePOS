package com.walmart.ticketService.VenuePOS.customexceptions;

public class SeatsNotFoundException extends RuntimeException
{

    private static final long serialVersionUID = 1997753363232807009L;

		public SeatsNotFoundException()
		{
		}

		public SeatsNotFoundException(String message)
		{
			super(message);
		}

		public SeatsNotFoundException(Throwable cause)
		{
			super(cause);
		}

		public SeatsNotFoundException(String message, Throwable cause)
		{
			super(message, cause);
		}

		public SeatsNotFoundException(String message, Throwable cause, 
                                           boolean enableSuppression, boolean writableStackTrace)
		{
			super(message, cause, enableSuppression, writableStackTrace);
		}

}