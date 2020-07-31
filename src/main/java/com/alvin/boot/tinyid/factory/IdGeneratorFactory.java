package com.alvin.boot.tinyid.factory;

import com.alvin.boot.tinyid.generator.IdGenerator;
import com.alvin.boot.tinyid.generator.impl.CachedIdGenerator;
import com.alvin.boot.tinyid.service.SegmentIdService;
import com.alvin.boot.tinyid.util.SpringContextUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author du_imba
 */
public class IdGeneratorFactory {


    private static ConcurrentHashMap<String, IdGenerator> generators = new ConcurrentHashMap<>();

    public static IdGenerator getIdGenerator(String bizType) {
        if (generators.containsKey(bizType)) {
            return generators.get(bizType);
        }
        synchronized (IdGeneratorFactory.class) {
            if (generators.containsKey(bizType)) {
                return generators.get(bizType);
            }
            SegmentIdService service = SpringContextUtils.getBean(SegmentIdService.class);
            IdGenerator idGenerator = new CachedIdGenerator(bizType, service);
            generators.put(bizType, idGenerator);
            return idGenerator;
        }
    }
}
