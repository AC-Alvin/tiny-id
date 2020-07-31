package com.alvin.boot.tinyid.dao;

import com.alvin.boot.tinyid.entity.TinyIdInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author alvin
 */
@Component
public interface TinyIdInfoDAO {
    /**
     * 根据bizType获取db中的tinyId对象
     *
     * @param bizType
     * @return
     */
//    @Select("select id, biz_type, begin_id, max_id, step, delta, remainder, create_time, update_time, version from tiny_id_info where biz_type = #{bizType}")
    TinyIdInfo queryByBizType(String bizType);

    /**
     * 根据id、oldMaxId、version、bizType更新最新的maxId
     *
     * @param id
     * @param newMaxId
     * @param oldMaxId
     * @param version
     * @param bizType
     * @return
     */
//    @Update("update tiny_id_info set max_id= ?,  update_time=now(), version=version+1 where id=? and max_id=? and version=? and biz_type=?")
    int updateMaxId(@Param("id") Long id, @Param("newMaxId") Long newMaxId, @Param("oldMaxId") Long oldMaxId, @Param("version") Long version, @Param("bizType") String bizType);
}
