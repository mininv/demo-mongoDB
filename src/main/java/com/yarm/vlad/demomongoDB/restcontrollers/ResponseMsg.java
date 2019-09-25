package com.yarm.vlad.demomongoDB.restcontrollers;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class ResponseMsg {
    @NotNull
	@JsonProperty("status")
	private Status status;

	@JsonProperty("message")
	private String message;

	public ResponseMsg(@JsonProperty("status") Status status, @JsonProperty("message") String message) {
		this.status = status;
		this.message = message;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public enum Status {
		SUCCESS, FAIL
	}
	public enum Message{
	    SUCCESS("Телефон успешно добавлен"),FAIL("Ошибка добавления телефона. ");
        private String message;

        public String getMessage() {
            return message;
        }

        Message(String message) {
            this.message = message;
        }
    }
}
