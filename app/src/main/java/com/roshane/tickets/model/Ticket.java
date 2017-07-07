package com.roshane.tickets.model;

import java.io.Serializable;

/**
 * Created by roshanedesilva on 7/7/17.
 */

public class Ticket implements Serializable {
    private final String _id;
    private final String subject;
    private final String type;
    private final String priority;
    private final String status;

    private Ticket(Builder offerBuilder) {
        this._id = offerBuilder._id;
        this.subject = offerBuilder.subject;
        this.type = offerBuilder.type;
        this.priority = offerBuilder.priority;
        this.status = offerBuilder.status;
    }

    public String getId() {
        return _id;
    }

    public String getSubject() {
        return subject;
    }

    public String getType() {
        return type;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }


    public static class Builder {
        private String _id;
        private String subject;
        private String type;
        private String priority;
        private String status;

        public Builder setId(String _id) {
            this._id = _id;
            return this;
        }

        public Builder setSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setPriority(String priority) {
            this.priority = priority;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Ticket build() {
            return new Ticket(this);
        }
    }
}
