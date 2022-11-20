package com.enfint.CreditConveyer.exception;

public class ApiException extends RuntimeException
{
    public ApiException(String message)
    {
        super(message);
    }
}
