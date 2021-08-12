package com.example.demo.payload.request;

public class CommentRequest {
        public String messenger;
    public float start;

    public float getStart() {
        return start;
    }

    public void setStart(float start) {
        this.start = start;
    }

    public CommentRequest(String messenger, float start) {
        this.messenger = messenger;
        this.start = start;
    }

    public CommentRequest(String messenger) {
        this.messenger = messenger;
    }

    public String getMessenger() {
        return messenger;
    }

    public void setMessenger(String messenger) {
        this.messenger = messenger;
    }
}
