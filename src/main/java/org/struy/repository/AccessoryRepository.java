package org.struy.repository;

import org.springframework.data.repository.CrudRepository;
import org.struy.entity.Accessory;

import java.util.List;

public interface AccessoryRepository extends CrudRepository<Accessory, String> {
    /**
     * find accessory by type
     *
     * @param type
     * @return
     */
    List<Accessory> findByType(String type);

}
