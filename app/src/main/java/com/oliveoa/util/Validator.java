package com.oliveoa.util;

import java.util.regex.Pattern;

/**
 *   校验器：利用正则表达式校验邮箱、手机号等
 *   @author Erica
 */
public class Validator {

        /**
         * 正则表达式：验证用户名
         */
        public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

        /**
         * 正则表达式：验证密码(长度在6~18之间，只能包含字母、数字)
         * 补充下划线组合：^[a-zA-Z]\w{5,17}$
         * 强密码（必须包含大小写字母和数字的组合，不能使用特殊字符，长度在8-10之间）：^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,10}$
         */
        public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,18}$";

        /**
         * 移动手机号码的正则表达式。
         */
        private static final String REGEX_CHINA_MOBILE ="1(3[4-9]|4[7]|5[012789]|8[278])\\d{8}";

        /**
         * 联通手机号码的正则表达式。
         */
        private static final String REGEX_CHINA_UNICOM = "1(3[0-2]|5[56]|8[56])\\d{8}";

        /**
         * 电信手机号码的正则表达式。
         */
        private static final String REGEX_CHINA_TELECOM = "(?!00|015|013)(0\\d{9,11})|(1(33|53|80|89)\\d{8})";

        /**
         * 正则表达式：验证手机号
         *      或者^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$
         */
        private static final String REGEX_PHONE_NUMBER = "^1[3578]\\d{9}$";

        /**
         *  正则表达式：验证电话
         */
        private static final String REGEX_FIXPHONE_NUMBER ="^0\\d{2,3}-?\\d{7,8}$";

        /**
         * 正则表达式：验证邮箱
         */
        public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

        /**
         * 正则表达式：验证汉字
         */
        public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

        /**
         * 正则表达式：验证身份证（15位或18位）
         */
        public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

        /**
         * 正则表达式：验证URL
         */
        public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

        /**
         * 正则表达式：验证IP地址
         */
        public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

        /**
         * 正则表达式：验证邮政编码（6位）
         */
        public static final String REGEX_ZIPCODE="^[1-9]\\d{5}$";

        /**
         * 正则表达式：验证字符串是否为数字
         */
        public static final String REGEX_NUMBERSTR="^[0-9]+.?[0-9]*$";

        /**
         * 校验数字字符串
         *
         * @param numstr
         * @return 校验通过返回true，否则返回false
         */
        public static boolean isNumstr(String numstr) {
                return Pattern.matches(REGEX_NUMBERSTR, numstr);
        }
        /**
         * 校验用户名
         *
         * @param username
         * @return 校验通过返回true，否则返回false
         */
        public static boolean isUsername(String username) {
            return Pattern.matches(REGEX_USERNAME, username);
        }

        /**
         * 校验密码
         *
         * @param password
         * @return 校验通过返回true，否则返回false
         */
        public static boolean isPassword(String password) {
            return Pattern.matches(REGEX_PASSWORD, password);
        }

        /**
         * 校验手机号
         *
         * @param mobile
         * @return 校验通过返回true，否则返回false
         */
        public static boolean isMobile(String mobile) {
            return Pattern.matches(REGEX_PHONE_NUMBER, mobile);
        }

        /**
         * 校验邮箱
         *
         * @param email
         * @return 校验通过返回true，否则返回false
         */
        public static boolean isEmail(String email) {
            return Pattern.matches(REGEX_EMAIL, email);
        }

        /**
         * 校验汉字
         *
         * @param chinese
         * @return 校验通过返回true，否则返回false
         */
        public static boolean isChinese(String chinese) {
            return Pattern.matches(REGEX_CHINESE, chinese);
        }

        /**
         * 校验身份证
         *
         * @param idCard
         * @return 校验通过返回true，否则返回false
         */
        public static boolean isIDCard(String idCard) {
            return Pattern.matches(REGEX_ID_CARD, idCard);
        }

        /**
         * 校验URL
         *
         * @param url
         * @return 校验通过返回true，否则返回false
         */
        public static boolean isUrl(String url) {
            return Pattern.matches(REGEX_URL, url);
        }

        /**
         * 校验IP地址
         *
         * @param ipAddr
         * @return
         */
        public static boolean isIPAddr(String ipAddr) {
            return Pattern.matches(REGEX_IP_ADDR, ipAddr);
        }

        /**
         * 校验邮政编码
         *
         * @param zipcode
         * @return
         */
        public static boolean isZipCode(String zipcode) {
                return Pattern.matches(REGEX_ZIPCODE, zipcode);
        }
        /**
         * 校验座机电话
         *
         * @param fixphone
         * @return
         */
        public static boolean isFixPhone(String fixphone) {
                return Pattern.matches(REGEX_FIXPHONE_NUMBER, fixphone);
        }




}
