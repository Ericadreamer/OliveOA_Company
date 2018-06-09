package com.oliveoa.dao;

import com.oliveoa.pojo.DutyInfo;

import java.util.ArrayList;
/**
* 数据访问接口
*/
public interface DutyDAO {

    /**
     * 插入职务信息
     */
    public void insertDuty(DutyInfo dutyInfo);

    /**
     * 删除职务
     */
    public void deleteDuty(String pcid);

    /**
     * 更新职务
     */
    public void updateDuty(DutyInfo dutyInfo);

    /**
     * 查询职务信息(同一部门对应多个职务)
     */
    public ArrayList<DutyInfo> getDutys(String dcid);


    /**\
     * 职务信息是否存在
     * @return
     */
    public boolean isExists(String name);

}