package com.alvin.boot.tinyid.entity;

import com.alvin.boot.tinyid.common.Constants;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 构建 SegmentId 对象,初始化基本信息
 *
 * @ClassName SegmentIdBuild
 * @Description
 * @Author alvin
 * @Date 2020/7/31 17:33
 * @Version V1.0
 **/
public class SegmentIdBuild {

    private TinyIdInfo idInfo;

    public SegmentIdBuild(TinyIdInfo idInfo) {
        this.idInfo = idInfo;
    }

    public static SegmentIdBuild newBuilder(TinyIdInfo idInfo) {
        return new SegmentIdBuild(idInfo);
    }

    public SegmentId builder() {
        if (this.idInfo == null) {
            throw new IllegalArgumentException("TinyIdInfo is null.");
        }
        SegmentId segmentId = new SegmentId();
        segmentId.setCurrentId(new AtomicLong(idInfo.getMaxId() - idInfo.getStep()));
        segmentId.setMaxId(idInfo.getMaxId());
        segmentId.setRemainder(idInfo.getRemainder() == null ? 0 : idInfo.getRemainder());
        segmentId.setDelta(idInfo.getDelta() == null ? 1 : idInfo.getDelta());
        // 默认20%加载
        segmentId.setLoadingId(segmentId.getCurrentId().get() + idInfo.getStep() * Constants.LOADING_PERCENT / 100);
        segmentId.init();
        return segmentId;
    }
}
