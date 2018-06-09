package com.oliveoa.dao;

import com.oliveoa.pojo.EmployeeInfo;

import java.util.ArrayList;

/**
 * 数据访问接口
 */
public interface EmployeeDAO {

        /**
         * 插入员工信息
         */
        public void insertEmployee(EmployeeInfo employeeInfo);

        /**
         * 删除员工
         */
        public void deleteEmployee(String eid);

        /**
         * 更新员工
         */
        public void updateEmployee(EmployeeInfo employeeInfo);

        /**
         * 查询员工信息(同一部门对应多个员工)
         */
        public ArrayList<EmployeeInfo> getEmployees(String dcid);

        /**
         * 查询员工信息(同一职务对应多个员工)
         */
//        public ArrayList<EmployeeInfo> getEmployeesBypcid(String pcid);

        /*
         * 查询员工信息(一个id对应一个员工)
         */
        public EmployeeInfo getEmployee(String id);

    /**\
         * 员工信息是否存在
         * @return
         */
        public boolean isExists(String name);

}
