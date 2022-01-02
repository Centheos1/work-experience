package com.fitnessplayground.exception;


public class MindBodyException extends RuntimeException {

    private boolean replay = false;

    public MindBodyException(String message, boolean replay) {
        super(message);
        this.replay = replay;
    }

    public MindBodyException(String message) {
        super(message);
    }

    public MindBodyException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public MindBodyException(String message, boolean replay, Throwable throwable) {
        super(message, throwable);
        this.replay = replay;
    }

    public boolean isReplay() {
        return replay;
    }
}
