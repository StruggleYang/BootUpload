package org.struy.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "accessory")
public class Accessory {
    @Id
    String id;

    String type;

    String path;

    String contentType;

    String size;

    String url;
}
