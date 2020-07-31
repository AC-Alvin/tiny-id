package com.alvin.boot.tinyid.generator;

import java.util.List;

/**
 * @author alvin
 */
public interface IdGenerator {
    /**
     * get next id
     * @return
     */
    Long nextId();

    /**
     * get next id batch
     * @param batchSize
     * @return
     */
    List<Long> nextId(Integer batchSize);
}
