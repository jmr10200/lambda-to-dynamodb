package study.lambda.table.v2;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class MgMemberTableV2 extends BaseTableV2 {

	String id;
	String mgName;
	String birthDate;
	String emailAddress;

	@DynamoDbPartitionKey
	@DynamoDbAttribute("id")
	public String getId() {
		return id;
	}

	@DynamoDbAttribute("mg_name")
	public String getMgName() {
		return mgName;
	}

	@DynamoDbAttribute("birth_date")
	public String getBirthDate() {
		return birthDate;
	}

	@DynamoDbAttribute("email_address")
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setMgName(String mgName) {
		this.mgName = mgName;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public MgMemberTableV2() {}
	public MgMemberTableV2(String id, String mgName, String birthDate, String emailAddress) {
		this.id = id;
		this.mgName = mgName;
		this.birthDate = birthDate;
		this.emailAddress = emailAddress;
	}

	@Override
	public String toString() {
		return "MgMemberTable [id=" + id + ", mgName=" + mgName + ", birthDate=" + birthDate + ", emailAddress="
				+ emailAddress + "]";
	}

}
