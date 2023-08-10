package com.zlht.pose.management.api.service;

import com.zlht.pose.management.api.utils.Result;
import com.zlht.pose.management.dao.entity.Sport;
import com.zlht.pose.management.dao.entity.User;

import java.util.Map;

public interface SportServicesI {

    /**
     * 查询体育
     *
     * @param type
     * @param pageNum
     * @param pageSize
     * @return
     */
    Result<Sport> querySportList(User loginUser, int type, int pageNum, int pageSize, String sportName);

    /**
     * 查询已添加的体育
     *
     * @return
     */
    Result<Sport> querySportMap(User loginUser);

    /**
     * 创建体育
     *
     * @param sport
     * @return
     */

    Map<String, Object> createSport(User loginUser, Sport sport);

    /**
     * 更新体育
     *
     * @param id
     * @param sport
     * @return
     */
    Map<String, Object> updateSport(User loginUser, int id, Sport sport);

    /**
     * 删除体育
     *
     * @param id
     * @return
     */
    Map<String, Object> deleteSport(User loginUser, int id);

    /**
     * 通过ID判断是否存在（删除更新判断）
     *
     * @param id
     * @return
     */
    boolean checkSportExistById(int id);


}
