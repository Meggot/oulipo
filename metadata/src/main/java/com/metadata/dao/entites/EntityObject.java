package com.metadata.dao.entites;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostUpdate;

@Data
@MappedSuperclass
public abstract class EntityObject implements Serializable {

    @Column(name="creation_date")
    @CreationTimestamp
    private LocalDateTime creationDate;
    @Column(name="modified_date")
    @UpdateTimestamp
    private LocalDateTime modifiedDate;
    @Column(name= "deleted")
    private boolean deleted;
    @Column(name="oca")
    private int oca;

    @PostUpdate
    public void incrementOca() {
        this.oca = oca++;
    }
}
