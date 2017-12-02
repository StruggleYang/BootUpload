package org.struy.repository;

import org.springframework.data.repository.CrudRepository;
import org.struy.entity.Accessory;

import java.util.List;

public interface AccessoryRepository extends CrudRepository<Accessory,String> {
    /**
     * 根据类型查找文件列表
     * @param type
     * @return
     */
    List<Accessory> findByType(String type);

}
