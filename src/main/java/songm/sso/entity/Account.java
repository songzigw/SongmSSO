package songm.sso.entity;

import java.util.Date;

import songm.sso.Sequence;

public class Account {

    private String serverKey;
    private String nonce;
    private long timestamp;
    private String signature;
    private Date created;

    /** 授权客户唯一标识 */
    private String clientId;
    /** 授权客户会话信息 */
    private String sessionId;

    public Account() {
        this.created = new Date();
        this.clientId = Sequence.getInstance().getSequence(12);
    }

    public String getServerKey() {
        return serverKey;
    }

    public void setServerKey(String serverKey) {
        this.serverKey = serverKey;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Date getCreated() {
        return created;
    }

    public String getClientId() {
        return clientId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

}
