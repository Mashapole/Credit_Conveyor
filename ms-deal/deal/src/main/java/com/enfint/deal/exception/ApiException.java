package com.enfint.deal.exception;

public class ApiException extends RuntimeException
{
    public ApiException(String message)
    {
        super(message);
    }
}
