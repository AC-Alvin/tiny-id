package com.alvin.boot.tinyid.util;

import com.alvin.boot.tinyid.factory.IdGeneratorFactory;
import com.alvin.boot.tinyid.generator.IdGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author alvin
 */
public class TinyId {


    public static Long nextId(String bizType) {
        if (bizType == null) {
            throw new IllegalArgumentException("type is null");
        }
        IdGenerator idGenerator = IdGeneratorFactory.getIdGenerator(bizType);
        return idGenerator.nextId();
    }

    public static List<Long> nextId(String bizType, Integer batchSize) {
        if (batchSize == null) {
            Long id = nextId(bizType);
            List<Long> list = new ArrayList();
            list.add(id);
            return list;
        }
        IdGenerator idGenerator = IdGeneratorFactory.getIdGenerator(bizType);
        return idGenerator.nextId(batchSize);
    }

}
