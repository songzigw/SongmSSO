
package cn.songm.sso.redis;

public interface Database {

    public static enum SsoTabs implements Tables {
        /** Session表 */
        SSO_SESSION,
        /** Attribute */
        SSO_ATTRIBUTE,
    }
    
    /**
     * Session表的字段
     * 
     * @author 张松
     * 
     */
    public static enum SessionF implements Fields {
        /** 会话唯一标示 */
        SES_ID,
        /** 用户ID */
        USER_ID,
        /** 会话创建时间 */
        CREATED,
        /** 会话访问时间 */
        ACCESS,
    }

}
