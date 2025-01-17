package com.server.gymServerApplication.exception;

public class TokenException extends RuntimeException{
   public TokenException(String message){
       super(message);
   }
}
