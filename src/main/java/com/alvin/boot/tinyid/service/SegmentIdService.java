package com.alvin.boot.tinyid.service;

import com.alvin.boot.tinyid.entity.SegmentId;

/**
 * @author alvin
 */
public interface SegmentIdService {

    /**
     * 根据bizType获取下一个SegmentId对象
     *
     * @param bizType
     * @return
     */
    SegmentId getNextSegmentId(String bizType);

}
