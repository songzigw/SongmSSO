package songm.sso.entity;

import java.util.Date;

import songm.sso.Sequence;

public class Backstage {

    /** 后台唯一标识 */
    private String backId;
    private Date created;

    private String serverKey;
    private String nonce;
    private long timestamp;
    private String signature;

    public Backstage() {
        this.created = new Date();
        this.backId = Sequence.getInstance().getSequence(12);
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

    public String getBackId() {
        return backId;
    }

}
