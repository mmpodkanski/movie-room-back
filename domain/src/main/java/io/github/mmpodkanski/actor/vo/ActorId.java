package io.github.mmpodkanski.actor.vo;

public class ActorId {
    private String id;

    protected ActorId() {
    }

    public ActorId(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
