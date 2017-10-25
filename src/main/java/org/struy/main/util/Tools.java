package org.struy.main.util;

import java.util.UUID;

public class Tools {
    /**
     * get a UUID
     * @return UUID
     */
    public static String uid(){
        return UUID.randomUUID().toString().replace("-","")+"_";
    }
}
