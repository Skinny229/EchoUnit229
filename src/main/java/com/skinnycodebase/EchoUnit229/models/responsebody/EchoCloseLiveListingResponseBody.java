package com.skinnycodebase.EchoUnit229.models.responsebody;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat
public class EchoCloseLiveListingResponseBody {

    private long id;

    private String confirmation_code;

    private String guild_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getConfirmation_code() {
        return confirmation_code;
    }

    public void setConfirmation_code(String confirmation_code) {
        this.confirmation_code = confirmation_code;
    }

}
