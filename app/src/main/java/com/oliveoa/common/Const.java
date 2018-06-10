package com.oliveoa.common;

import java.security.PublicKey;

/*
*    接口地址常量
*/
public class Const {
    public static final String HOSTPATH = "http://119.23.253.135:8080";

    public static  final  String COMPANY_LOGIN = HOSTPATH + "/oliveoa/manage/company/login.do";
    public static  final  String COMPANY_LOGOUT = HOSTPATH + "/oliveoa/manage/company/logout.do";
    public static  final  String COMPANY_INFO = HOSTPATH + "/oliveoa/manage/company/get_companyInfo.do";
    public static  final  String COMPANY_INFO_UPDATE =  HOSTPATH + "/oliveoa/manage/company/update_companyInfo.do";
    public static  final  String COMPANY_INFO_PASSWORD =  HOSTPATH + "/oliveoa/manage/company/update_password.do";

    public static  final  String GET_DEPARTMENT = HOSTPATH + "/oliveoa/manage/department/get_department.do";
    public static  final  String CHECK_DEPARTMENT_ID = HOSTPATH + "/oliveoa/manage/department/check_id.do";
    public static  final  String ADD_DEPARTMENT = HOSTPATH + "/oliveoa/manage/department/add_department.do";
    public static  final  String GET_DEPARTMENT_INFO = HOSTPATH + "/oliveoa/manage/department/get_departmentByDcid.do";
    public static  final  String CHECK_CHILDREN_PARALLEL_DEPARTMENT = HOSTPATH + "/oliveoa/manage/department/get_children_parallel_department.do";
    public static  final  String UPDATE_DEPARTMENT_INFO = HOSTPATH + "/oliveoa/manage/department/update_department.do";
    public static  final  String DEPARTMENT_DELETE = HOSTPATH +"/oliveoa/manage/department/delete_department.do";

    public static  final  String DUTY_ADD = HOSTPATH + "/oliveoa/manage/position/add_position.do";
    public static  final  String DUTY_SEARCH = HOSTPATH + "/oliveoa/manage/position/get_position.do";
    public static  final  String DUTY_UPDATE = HOSTPATH + "/oliveoa/manage/position/update_position.do";
    public static  final  String DUTY_DELETE = HOSTPATH +"/oliveoa/manage/position/delete_position.do";

    public static  final  String EMPLOYEE_INFO_ADD = HOSTPATH + "/oliveoa/manage/employees/add_employee.do";
    public static  final  String EMPLOYEE_INFO_GETBYPOSITON = HOSTPATH + "/oliveoa/manage/employees/get_employees_by_position.do";
    public static  final  String EMPLOYEE_INFO_GETBYDEPARTMENT = HOSTPATH + "/oliveoa/manage/employees/get_employees_by_department.do";
    public static  final  String EMPLOYEE_INFO_DELETE = HOSTPATH +"/oliveoa/manage/employees/delete_employee.do";
    public static  final  String EMPLOYEE_INFO_UPDATE = HOSTPATH +"/oliveoa/manage/employees/update_employee.do";


    public static  final  String GOODS_ADD = HOSTPATH + "/oliveoa/manage/goods/add_goods.do";
    public static  final  String GOODS_SEARCH = HOSTPATH +"/oliveoa/manage/goods/get_goods.do";
    public static  final  String GOODS_SEARCHBYID = HOSTPATH +"/oliveoa/manage/goods/get_goods_by_gid.do";
    public static  final  String GOODS_UPDATE = HOSTPATH +"/oliveoa/manage/goods/update_goods.do";
    public static  final  String GOODS_DELETE = HOSTPATH +"/oliveoa/manage/goods/delete_goods.do";
}
