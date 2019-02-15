package es.ed0.tinyjson.test;

public class PendingMessage {

    public static final int STATUS_SENDING = 0, STATUS_SENT = 1, STATUS_RECEIVED = 2, STATUS_ACK_RECEIVED = 3;
    
	private String messageId, updateMessageId, senderUserId, targetUserId, text;
	private int targetStatus;
	private long sentTime;
	
	public PendingMessage(String messageId, String updateMessageId, String senderUserId,
			String targetUserId, String text, int targetStatus, long sentTime) {
		this.messageId = messageId;
		this.updateMessageId = updateMessageId;
		this.senderUserId = senderUserId;
		this.targetUserId = targetUserId;
		this.text = text;
		this.targetStatus = targetStatus;
		this.sentTime = sentTime;
	}
	
	/**
	 * Create an unsent message
	 * @param messageId
	 * @param senderUserId
	 * @param targetUserId
	 * @param text
	 */
	public PendingMessage(String messageId, String senderUserId, String targetUserId, String text, long sentTime) {
		this(messageId, null, senderUserId, targetUserId, text, STATUS_SENT, sentTime);
	}
	
	/**
	 * Create a 'waiting for delivered ack' message
	 * @param messageId
	 * @param senderUserId
	 */
	public PendingMessage(String messageId, String updateMessageId, String senderUserId) {
		this(messageId, updateMessageId, senderUserId, null, null, STATUS_RECEIVED, 0);
	}
	
	
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getUpdateMessageId() {
		return updateMessageId;
	}
	public void setUpdateMessageId(String updateMessageId) {
		this.updateMessageId = updateMessageId;
	}
	public String getSenderUserId() {
		return senderUserId;
	}
	public void setSenderUserId(String senderUserId) {
		this.senderUserId = senderUserId;
	}
	public String getTargetUserId() {
		return targetUserId;
	}
	public void setTargetUserId(String targetUserId) {
		this.targetUserId = targetUserId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getTargetStatus() {
		return targetStatus;
	}
	public void setTargetStatus(int targetStatus) {
		this.targetStatus = targetStatus;
	}
	public long getSentTime() {
		return sentTime;
	}
	public void setSentTime(long sentTime) {
		this.sentTime = sentTime;
	}
	
	
}
