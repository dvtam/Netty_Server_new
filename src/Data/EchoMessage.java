package Data;

import cc.lison.pojo.EchoPojo;

public class EchoMessage extends EchoPojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3648851810575375470L;

	private static MessageType messageType;

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	/**
	 * 
	 * 
	 * @param message
	 * @return
	 */
	public static EchoMessage buildMessage(String message) {

		EchoMessage echo = new EchoMessage();

		byte[] bytes = message.getBytes();
		echo.setBytes(bytes);
		echo.setSumCountPackage(bytes.length);
		echo.setCountPackage(1);
		echo.setSend_time(System.currentTimeMillis());
		echo.setReceive_uid("0");

		return echo;
	}

	/**
	 * 
	 * 
	 * @param message
	 * @param messageType
	 * @return
	 */
	public static EchoMessage buildMessage(String message, MessageType messageType) {
		EchoMessage echo = null;

		switch (messageType) {
		case

		HEART_BEAT_SERVER:
			echo = buildMessage("");
			echo.setMessageType(messageType);
			break;

		case HEART_BEAT_CLIENT:
			echo = buildMessage("");
			echo.setMessageType(messageType);
			break;

		case BUSINESS2SERVER:
			echo = buildMessage(message);
			echo.setMessageType(messageType);
			break;

		case BUSINESS2CLIENT:
			echo = buildMessage(message);
			echo.setMessageType(messageType);
			break;

		case SERVER_ONLINE:
			echo = buildMessage("");
			echo.setMessageType(messageType);
			break;

		case SERVER_OFFLINE:
			echo = buildMessage("");
			echo.setMessageType(messageType);
			break;

		case CLIENT_ONLINE:
			echo = buildMessage("");
			echo.setMessageType(messageType);
			break;

		case CLIENT_OFFLINE:
			echo = buildMessage("");
			echo.setMessageType(messageType);
			break;
	
		case SERVER_ACCEPT:
			echo = buildMessage("");
			echo.setMessageType(messageType);
			break;

		case SERVER_REJECT:
			echo = buildMessage(message);
			echo.setMessageType(messageType);
			break;

		case SERVER_EXCEPTION:
			echo = buildMessage("");
			echo.setMessageType(messageType);
			break;

		case CLIENT_EXCEPTION:
			echo = buildMessage("");
			echo.setMessageType(messageType);
			break;
		}

		return echo;
	}


	public enum MessageType {

		HEART_BEAT_SERVER,

		HEART_BEAT_CLIENT,

		BUSINESS2SERVER,

		BUSINESS2CLIENT,

		SERVER_ONLINE,

		SERVER_OFFLINE,

		CLIENT_ONLINE,

		CLIENT_OFFLINE,

		SERVER_ACCEPT,

		SERVER_REJECT,

		SERVER_EXCEPTION,

		CLIENT_EXCEPTION

	}
}
