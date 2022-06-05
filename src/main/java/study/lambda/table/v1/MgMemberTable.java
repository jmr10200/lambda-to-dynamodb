package study.lambda.table.v1;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import study.lambda.constants.Constants;

@JsonIgnoreProperties(ignoreUnknown = true)
@DynamoDBTable(tableName = Constants.MG_MEMBER)
public class MgMemberTable extends BaseTable{

	String id;
	String mgName;
	String birthDate;
	String emailAddress;
	
	@DynamoDBHashKey(attributeName = "id")
	public String getId() {
		return id;
	}
	
	@DynamoDBAttribute(attributeName = "mg_name")
	public String getMgName() {
		return mgName;
	}
	
	@DynamoDBAttribute(attributeName = "birth_date")
	public String getBirthDate() {
		return birthDate;
	}
	
	@DynamoDBAttribute(attributeName = "email_address")
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

	@Override
	public String toString() {
		return "MgMemberTable [id=" + id + ", mgName=" + mgName + ", birthDate=" + birthDate + ", emailAddress="
				+ emailAddress + ", createdAt=" + createdAt + ", insertUser=" + insertUser + ", updatedUser=" + updatedUser + 
				", updateAt=" + updatedAt + ", version=" + version + "]";
	}
	
	
}
