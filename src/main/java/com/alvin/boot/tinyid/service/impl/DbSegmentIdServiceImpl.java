package com.alvin.boot.tinyid.service.impl;

import com.alvin.boot.tinyid.common.Constants;
import com.alvin.boot.tinyid.dao.TinyIdInfoDAO;
import com.alvin.boot.tinyid.entity.SegmentId;
import com.alvin.boot.tinyid.entity.SegmentIdBuild;
import com.alvin.boot.tinyid.entity.TinyIdInfo;
import com.alvin.boot.tinyid.exception.TinyIdSysException;
import com.alvin.boot.tinyid.service.SegmentIdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author alvin
 */
@Component
public class DbSegmentIdServiceImpl implements SegmentIdService {

    private static final Logger logger = LoggerFactory.getLogger(DbSegmentIdServiceImpl.class);

    @Autowired
    private TinyIdInfoDAO tinyIdInfoDAO;

    /**
     * Transactional标记保证query和update使用的是同一连接
     *
     * @param bizType
     * @return
     */
    @Override
    @Transactional
    public SegmentId getNextSegmentId(String bizType) {
        // 获取nextTinyId的时候，有可能存在version冲突，需要重试
        for (int i = 0; i < Constants.RETRY; i++) {
            TinyIdInfo tinyIdInfo = tinyIdInfoDAO.queryByBizType(bizType);
            if (tinyIdInfo == null) {
                throw new TinyIdSysException("can not find biztype:" + bizType);
            }
            Long newMaxId = tinyIdInfo.getMaxId() + tinyIdInfo.getStep();
            Long oldMaxId = tinyIdInfo.getMaxId();
            int row = tinyIdInfoDAO.updateMaxId(tinyIdInfo.getId(), newMaxId, oldMaxId, tinyIdInfo.getVersion(),
                    tinyIdInfo.getBizType());
            if (row == 1) {
                tinyIdInfo.setMaxId(newMaxId);
                SegmentId segmentId = SegmentIdBuild.newBuilder(tinyIdInfo).builder();
                logger.info("getNextSegmentId success tinyIdInfo:{} current:{}", tinyIdInfo, segmentId);
                return segmentId;
            } else {
                logger.info("getNextSegmentId conflict tinyIdInfo:{}", tinyIdInfo);
            }
        }
        throw new TinyIdSysException("get next segmentId conflict");
    }
}
