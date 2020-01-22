package com.skinnycodebase.EchoUnit229.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GuildDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String updateListingMentionRoleId;




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUpdateListingMentionRoleId() {
        return updateListingMentionRoleId;
    }

    public void setUpdateListingMentionRoleId(String updateListingMentionRoleId) {
        this.updateListingMentionRoleId = updateListingMentionRoleId;
    }
}
