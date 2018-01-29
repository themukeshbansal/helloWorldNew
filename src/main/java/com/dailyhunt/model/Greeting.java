package com.dailyhunt.model;

public class Greeting {
	 private final long Status;
	    private final String content;

	    public Greeting(long Status, String content) {
	        this.Status = Status;
	        this.content = content;
	    }

	    public long getStatus() {
	        return Status;
	    }

	    public String getContent() {
	        return content;
	    }
}
