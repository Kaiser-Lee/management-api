package com.iwhalecloud.retail.oms.consts;

/**
 * @author mzl
 * @date 2018/10/17
 */
public class OmsConst {

    /**
     * 会员SESSION字符串定义
     */
    public static final String SESSION_MEMBER = "MEMBER";

    /**
     * 系统用户SESSION字符串定义
     */
    public static final String SESSION_USER = "USER";

    /**
     * 系统用户的店铺SESSION字符串定义
     */
    public static final String SESSION_SHOP = "SHOP";

    /**
     * 微信openId session字符串定义
     */
    public static final String SESSION_WX_OPEN_ID = "WX_OPEN_ID";
    
    /**
     * 微信昵称 session字符串定义
     */
    public static final String SESSION_WX_NICK_NAME = "WX_NICK_NAME";
    
    /**
     * 登录成功后的TOKEN 符串定义
     */
    public static final String SESSION_TOKEN = "TOKEN";

    public static final String LOGIN_STATUS = "loginStatus";

    public static final String AUDIT_STATE = "00A";

    public static final String MARKET_ENABLE = "1";

    public static final String MARKET_ENABLE_ZERO = "0";

    public static final int CONTENT_TEXT = 0;

    public static final int CONTENT_PIC = 1;

    public static final int CONTENT_ORDERPIC = 2;

    public static final int CONTENT_PICSET = 3;

    public static final int CONTENT_VIDEO = 4;

    public static final int CONTENT_VIDEO_REL = 5;

    public static final Integer CONTENT_MATERIAL_ONE_LEVEL = 1;

    public static final Integer CONTENT_MATERIAL_TWO_LEVEL = 2;

    //-------------------工号角色（沿用电商的）-------------------------
    /**电信员工*/
    public final static int CURR_FOUNDER0 = 0;//电信员工
    /**超级管理员*/
    public final static int CURR_FOUNDER1 = 1;//超级管理员
    /**代理商*/
    public final static int CURR_FOUNDER3 = 3;//代理商-店长
    /**代理商店员*/
    public final static int CURR_FOUNDER6 = 6 ;//店员
    /**供货商*/
    public final static int CURR_FOUNDER4 = 4;//供货商

    //-------------------------------------工号角色-------------------------
    public final static String USER_STATE_0 ="0";//用户状态（禁用）
    public final static int USER_STATE0 =0;//用户状态（禁用）
    public final static String USER_STATE_1 ="1";//用户状态（开启）
    public final static int USER_STATE1 =1;//用户状态（开启）
    public final static String OTHER_STATE_2="2";//其他状态
    public final static int OTHER_STATE2=2;//其他状态

    /**
     * 审核状态枚举
     */
    public enum LoginTypeEnum{
        /**
         * 密码登陆
         */
        PASSWORD("1","密码登陆"),
        /**
         * 验证码登陆
         */
        VERIFICATION_CODE("2","校验验证码");
        private String value;
        private String code;
        LoginTypeEnum(String code,String value){
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getCode() {
            return code;
        }
    }

    /**
     * 业务类型枚举
     */
    public enum BusinessTypeEnum{
        /**
         * 账号注册
         */
        REGISTER(1,"账号注册-手机随机码校验"),
        /**
         * 账号绑定手机号码修改
         */
        MODIFY_PHONE(2,"账号绑定手机号码修改"),
        /**
         * 系统登录
         */
        SYSTEM_LOGIN(3,"系统登录-手机验证码");
        private String value;
        private Integer code;
        BusinessTypeEnum(Integer code,String value){
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public Integer getCode() {
            return code;
        }
    }

    /**
     * 审核状态枚举
     */
    public enum loginStatusEnum{
        /**
         * 未登陆
         */
        NOT_LOGIN(0,"未登录"),
        /**
         * 已经登陆
         */
        HAVE_LOGIN(1,"已登陆");
        private String value;
        private Integer code;
        loginStatusEnum(Integer code,String value){
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public Integer getCode() {
            return code;
        }
    }

    /**
     * 审核状态枚举
     */
    public enum ValidStatusEnum{
        /**
         * 未登陆
         */
        NOT_VALID(0,"未校验"),
        /**
         * 已经登陆
         */
        HAVE_VALID(1,"已校验");
        private String value;
        private Integer code;
        ValidStatusEnum(Integer code,String value){
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public Integer getCode() {
            return code;
        }
    }

    /**
     * 审核状态枚举
     */
    public enum SendStatusEnum{
        /**
         * 未登陆
         */
        NOT_SEND(0,"未发送"),
        /**
         * 已经登陆
         */
        HAVE_SEND(1,"已发送");
        private String value;
        private Integer code;
        SendStatusEnum(Integer code,String value){
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public Integer getCode() {
            return code;
        }
    }

    /**
     * 内容信息类型
     */
    public enum ContentTypeEnum{
        /**
         * 0：软文
         * 1：单图
         * 2：轮播图
         * 3：推广图集
         * 4：纯视频
         * 5：关联视频
         */
        CONTENT_TEXT("0","软文"),
        CONTENT_PIC("1","单图"),
        CONTENT_ORDERPIC("2","轮播图"),
        CONTENT_PICSET("3","推广图集"),
        CONTENT_VIDEO("4","纯视频"),
        CONTENT_VIDEO_REL("5","关联视频");
        private String value;
        private String code;
        ContentTypeEnum(String code,String value){
            this.code = code;
            this.value = value;
        }
        public String getValue() {
            return value;
        }
        public String getCode() {
            return code;
        }
    }

    /**
     * 内容管理状态枚举
     */
    public enum ContentStatusEnum{
        /**
         * 未提交审核
         */
        NOT_AUDIT(0,"未提交审核"),
        /**
         * 已经提交审核，待审核通过
         */
        FOR_AUDIT(1,"待审核"),
        /**
         * 审核通过
         */
        PASS_AUDIT(2,"审核通过"),
        /**
         * 审核不通过
         */
        NOT_PASS_AUDIT(3,"审核不通过"),
        /**
         * 已发布
         */
        HAVE_PUBLISH(4,"已发布"),
        /**
         * 已上架
         */
        UP_SHELVE(5,"已上架"),
        /**
         * 已下架/失效
         */
        OFF_SHELVE(6,"已下架/失效"),
        /**
         * 已删除
         */
        HAVE_DELETE(7,"已删除"),
        /**
         * 已作废
         */
        HAVE_CANCELLATION(8,"已作废");

        private String value;
        private Integer code;
        ContentStatusEnum(Integer code,String value){
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public Integer getCode() {
            return code;
        }
    }

    /**
     * 内容关联对象类型
     */
    public enum ContentObjType{
        /**
         * 1：商品
         * 2：营销活动
         * 3：优惠券
         */
        GOODS (1,"商品"),
        CAMPAIGN(2,"营销活动"),
        COUPON(3,"优惠券");
        private String value;
        private Integer code;
        ContentObjType(Integer code,String value){
            this.code = code;
            this.value = value;
        }
        public String getValue() {
            return value;
        }
        public Integer getCode() {
            return code;
        }
    }
    /**
     * 内容管理视频素材是否有二级素材状态枚举
     */
    public enum ContentVedioStatusEnum{
        /**
         * 未提交审核
         */
        NOT_LV2(0,"无二级素材"),
        /**
         * 未提交审核
         */
        HAVE_LV2(1,"有二级素材");
        private String value;
        private Integer code;
        ContentVedioStatusEnum(Integer code,String value){
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public Integer getCode() {
            return code;
        }
    }

    /**
     * 是否被删除
     */
    public enum IsDeleted {
        /**
         * 未删除
         */
        NOT_DELETED(0,"未删除"),
        /**
         * 已删除
         */
        HAVE_DELETED(1,"已删除");
        private String value;
        private Integer code;
        IsDeleted(Integer code, String value){
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public Integer getCode() {
            return code;
        }
    }

    /**
     * 云货架状态
     */
    public enum CloudShelfStatus{
        /**
         * 10：未激活
         * 20：启用
         * 30：停用
         */
        NOT_ACTIVATED (10,"未激活"),
        USED(20,"启用"),
        NO_USED(30,"停用");
        private String value;
        private Integer code;
        CloudShelfStatus(Integer code,String value){
            this.code = code;
            this.value = value;
        }
        public String getValue() {
            return value;
        }
        public Integer getCode() {
            return code;
        }
    }

    /**
     * 云货架设备状态
     */
    public enum CloudDeviceStatus{
        /**
         * 10：未激活
         * 20：启用
         * 30：停用
         */
        NOT_ACTIVATED (10,"未激活"),
        USED(20,"启用"),
        NO_USED(30,"停用");
        private String value;
        private Integer code;
        CloudDeviceStatus(Integer code,String value){
            this.code = code;
            this.value = value;
        }
        public String getValue() {
            return value;
        }
        public Integer getCode() {
            return code;
        }
    }

    /**
     * 内容播放配置是否启用
     */
    public enum IsPlayback {
        /**
         * 未启用
         */
        NOT_PLAYBACK(0L,"未启用"),
        /**
         * 已启用
         */
        HAVE_PLAYBACK(1L,"已启用");
        private String value;
        private Long code;
        IsPlayback(Long code, String value){
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public Long getCode() {
            return code;
        }
    }


    /**
     * 云货架设备日志查询方
     */
    public enum QueryDeviceLogMethod{
        /**
         * 0：按日
         * 1：按周
         */
        DAY (0,"按日"),
        WEEK(1,"按周");
        private String value;
        private Integer code;
        QueryDeviceLogMethod(Integer code,String value){
            this.code = code;
            this.value = value;
        }
        public String getValue() {
            return value;
        }
        public Integer getCode() {
            return code;
        }
    }

    /**
     * 操作内容类型
     */
    public enum ActionTypeEnum{
        /**
         * add：添加内容
         * delete：删除内容
         */
        ADD("add","添加内容"),
        DELETE("delete","删除内容");
        private String value;
        private String code;
        ActionTypeEnum(String code,String value){
            this.code = code;
            this.value = value;
        }
        public String getValue() {
            return value;
        }
        public String getCode() {
            return code;
        }
    }
}
