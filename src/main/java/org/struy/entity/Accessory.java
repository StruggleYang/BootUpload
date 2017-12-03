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

    String type; // accessory type

    String path; // accessory server save path

    String contentType; // accessory contentType

    String size; // accessory size

    String url; //view url
}
