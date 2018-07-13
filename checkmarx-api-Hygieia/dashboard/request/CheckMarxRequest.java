package com.capitalone.dashboard.request;

import com.capitalone.dashboard.model.CheckMarxType;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotNull;

public class CheckMarxRequest {
    @NotNull
    private ObjectId id;

    private CheckMarxType type;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public CheckMarxType getType() {
        return type;
    }

    public void setType(CheckMarxType type) {
        this.type = type;
    }

}
