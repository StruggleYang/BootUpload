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

    Boolean isCloud; // is cloud

    String type; // accessory type

    String path; // accessory server save path

    String suffix; // suffix

    String contentType; // accessory contentType

    String size; // accessory size

    String url; //view url
}
