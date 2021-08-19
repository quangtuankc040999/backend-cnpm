package com.example.demo.payload.request;

public class CommentRequest {
        public String messenger;
    public float stars;

    public float getStart() {
        return stars;
    }

    public void setStart(float stars) {
        this.stars = stars;
    }

    public CommentRequest(String messenger, float start) {
        this.messenger = messenger;
        this.stars = stars;
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
