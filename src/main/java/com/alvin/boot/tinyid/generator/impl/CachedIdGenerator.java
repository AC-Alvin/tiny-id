package com.alvin.boot.tinyid.generator.impl;

import com.alvin.boot.tinyid.entity.Result;
import com.alvin.boot.tinyid.entity.ResultCode;
import com.alvin.boot.tinyid.entity.SegmentId;
import com.alvin.boot.tinyid.exception.TinyIdSysException;
import com.alvin.boot.tinyid.factory.NamedThreadFactory;
import com.alvin.boot.tinyid.generator.IdGenerator;
import com.alvin.boot.tinyid.service.SegmentIdService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author alvin
 */
public class CachedIdGenerator implements IdGenerator {
    protected String bizType;
    protected SegmentIdService segmentIdService;
    protected volatile SegmentId current;
    protected volatile SegmentId next;
    private volatile boolean isLoadingNext;
    private Object lock = new Object();
    private ExecutorService executorService = Executors.newSingleThreadExecutor(new NamedThreadFactory("tinyid-generator"));

    public CachedIdGenerator(String bizType, SegmentIdService segmentIdService) {
        this.bizType = bizType;
        this.segmentIdService = segmentIdService;
        loadCurrent();
    }

    public synchronized void loadCurrent() {
        if (current == null || !current.useful()) {
            if (next == null) {
                SegmentId segmentId = querySegmentId();
                this.current = segmentId;
            } else {
                current = next;
                next = null;
            }
        }
    }

    private SegmentId querySegmentId() {
        String message = null;
        try {
            SegmentId segmentId = segmentIdService.getNextSegmentId(bizType);
            if (segmentId != null) {
                return segmentId;
            }
        } catch (Exception e) {
            message = e.getMessage();
        }
        throw new TinyIdSysException("error query segmentId: " + message);
    }

    public void loadNext() {
        if (next == null && !isLoadingNext) {
            synchronized (lock) {
                if (next == null && !isLoadingNext) {
                    isLoadingNext = true;
                    executorService.submit(new Runnable() {
                        public void run() {
                            SegmentId segmentId = querySegmentId();
                            next = segmentId;
                            isLoadingNext = false;
                        }
                    });
                }
            }
        }
    }

    @Override
    public Long nextId() {
        while (true) {
            if (current == null) {
                loadCurrent();
                continue;
            }
            Result result = current.nextId();
            if (result.getCode() == ResultCode.OVER) {
                loadCurrent();
            } else {
                if (result.getCode() == ResultCode.LOADING) {
                    loadNext();
                }
                return result.getId();
            }
        }
    }

    @Override
    public List<Long> nextId(Integer batchSize) {
        List<Long> ids = new ArrayList();
        for (int i = 0; i < batchSize; i++) {
            Long id = nextId();
            ids.add(id);
        }
        return ids;
    }

}
