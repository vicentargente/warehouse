package com.example.demo.util;

public class Logger {
    private static Logger instance;

    public static Logger getInstance(){
        if(instance == null) instance = new Logger();
        return instance;
    }

    private String log;

    private Logger(){
        log = new String();
    }

    public void log(String message){
        this.log += message + "\\n";
    }

    public String getLog(){
        return this.log;
    }

    public void clear(){
        this.log = new String();
    }
}
