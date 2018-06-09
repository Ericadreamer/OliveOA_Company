package com.oliveoa.dao;


import com.oliveoa.pojo.Properties;

import java.util.ArrayList;

/**
 * 数据访问接口
 */
public interface PropertyDAO {
    /**
     * 插入物品信息
     */
    public void insertProperty(Properties properties);

    /**
     * 删除物品
     */
    public void deleteProperty(String gid);

    /**
     * 更新物品
     */
    public void updateProperty(Properties properties);

    /**
     * 查询物品信息
     */
    public ArrayList<Properties> getProperties();


    /**\
     * 物品信息是否存在
     * @return
     */
    public boolean isExists(String name);

}
