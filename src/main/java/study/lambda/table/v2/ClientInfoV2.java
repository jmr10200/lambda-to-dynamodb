package study.lambda.table.v2;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class ClientInfoV2 {
	
	String fullName;
	String address;
	String emailAddress;
	
	public String getFullName() {
		return fullName;
	}
	public String getAddress() {
		return address;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	@Override
	public String toString() {
		return "ClientInfo [fullName=" + fullName + ", address=" + address + ", emailAddress=" + emailAddress + "]";
	}
	public ClientInfoV2(String fullName, String address, String emailAddress) {
		super();
		this.fullName = fullName;
		this.address = address;
		this.emailAddress = emailAddress;
	}
	
	
}
