package com.oliveoa.dao;

import com.oliveoa.pojo.DepartmentInfo;

import java.util.ArrayList;

/**
 * 数据访问接口
 */
public interface DepartmentDAO {

    /**
     * 插入部门信息
     */
    public void insertDepartment(DepartmentInfo departmentInfo);

    /**
     * 删除部门
     */
    public void deleteDepartment(String pcid);

    /**
     * 更新部门
     */
    public void updateDepartment(DepartmentInfo departmentInfo);

    /**
     * 查询部门信息
     */
    public ArrayList<DepartmentInfo> getDepartments();


    /**\
     * 部门信息是否存在
     * @return
     */
    public boolean isExists(String name);

}