package com.crud.tasks.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidTaskException extends RuntimeException {
    public enum InvalidTaskReason {
        ILLEGAL_TITLE("Null, empty or too long title"),
        NOT_EXIST("Task with given id not exist"),
        ILLEGAL_ID("Null, zero or negative task id"),
        FORCED_ID("Forced id for unsaved task");

        private String desc;

        InvalidTaskReason(final String desc) {
            this.desc = desc;
        }

        @Override
        public String toString() {
            return desc;
        }
    }

    public InvalidTaskException(InvalidTaskReason reason) {
        super(reason.toString());
    }
}