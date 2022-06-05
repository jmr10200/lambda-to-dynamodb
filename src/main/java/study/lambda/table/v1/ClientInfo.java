package study.lambda.table.v1;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import study.lambda.constants.Constants;

@DynamoDBTable(tableName = "")
public class ClientInfo {

	String fullName;
	String address;
	String emailAddress;

	@DynamoDBAttribute(attributeName = Constants.FULL_NAME)
	public String getFullName() {
		return fullName;
	}

	@DynamoDBAttribute(attributeName = Constants.ADDRESS)
	public String getAddress() {
		return address;
	}

	@DynamoDBAttribute(attributeName = Constants.EMAIL_ADDRESS)
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

	public ClientInfo() {}
	
	public ClientInfo(String fullName, String address, String emailAddress) {
		this.fullName = fullName;
		this.address = address;
		this.emailAddress = emailAddress;
	}

}
