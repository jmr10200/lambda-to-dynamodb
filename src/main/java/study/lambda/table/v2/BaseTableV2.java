package study.lambda.table.v2;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@DynamoDbBean
public class BaseTableV2 {

	String createdAt;
	String updatedAt;
	String insertUser;
	String updatedUser;
	long version;
	
	@DynamoDbAttribute("created_at")
	public String getCreatedAt() {
		return createdAt;
	}
	
	@DynamoDbAttribute("updated_at")
	public String getUpdatedAt() {
		return updatedAt;
	}
	
	@DynamoDbAttribute("insert_user")
	public String getInsertUser() {
		return insertUser;
	}
	
	@DynamoDbAttribute("updated_user")
	public String getUpdatedUser() {
		return updatedUser;
	}
	
	@DynamoDbAttribute("version")
	public long getVersion() {
		return version;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public void setInsertUser(String insertUser) {
		this.insertUser = insertUser;
	}
	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	
	
}
