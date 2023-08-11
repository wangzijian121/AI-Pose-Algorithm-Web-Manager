package com.zlht.pose.management.api.service;

import com.zlht.pose.management.api.utils.PageInfo;
import com.zlht.pose.management.api.utils.Result;
import com.zlht.pose.management.dao.entity.DataSet;
import com.zlht.pose.management.dao.entity.User;

import java.util.Map;

public interface DataSetServicesI {

    /**
     * 查询数据集
     *
     * @param type
     * @param currentPage
     * @param pageSize
     * @return
     */
    Result<PageInfo<DataSet>> queryDataSetList(User loginUser, int type, int currentPage, int pageSize, String dataSetName);


    /**
     * 创建数据集
     *
     * @param dataSet
     * @return
     */

    Map<String, Object> createDataSet(User loginUser, DataSet dataSet);

    /**
     * 更新数据集
     *
     * @param id
     * @param dataSet
     * @return
     */
    Map<String, Object> updateDataSet(User loginUser, int id, DataSet dataSet);

    /**
     * 删除数据集
     *
     * @param id
     * @return
     */
    Map<String, Object> deleteDataSet(User loginUser, int id);

    /**
     * 通过ID判断是否存在（删除更新判断）
     *
     * @param id
     * @return
     */
    boolean checkDataSetExistById(int id);
}
