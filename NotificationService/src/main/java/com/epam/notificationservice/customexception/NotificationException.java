package com.epam.notificationservice.customexception;


public class NotificationException extends RuntimeException{
    public NotificationException(){
        super("Exception has been raised.");
    }

    public NotificationException(String message){
        super(message);
    }
}
