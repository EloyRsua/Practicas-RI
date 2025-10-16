package uo.ri.cws.application.persistence;

import java.time.LocalDateTime;

public abstract class Record {
    public String id;
    public long version;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
    public String entityState;
}
