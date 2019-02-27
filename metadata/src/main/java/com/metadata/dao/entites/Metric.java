// Copyright (c) 2019 Travelex Ltd

package com.metadata.dao.entites;

import com.common.models.dtos.EntityType;
import com.common.models.dtos.MetricType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Metric")
public class Metric extends EntityObject{

    @Id
    @SequenceGenerator(name = "metrics_seq", sequenceName = "metrics_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "metrics_seq")
    @Column(name = "pk_metric_id")
    private Integer id;

    @Column(name = "metric_type")
    private MetricType type;

    @Column(name = "entity_id")
    private Integer entityId;

    @Column(name = "entity_type")
    private EntityType entityType;

    @Column(name = "user_id")
    private Integer userId;

}
