package study.lambda.table.v2;

import java.util.Map;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class MgClientTableV2 extends BaseTableV2 {

	String id;
	Map<String, String> clientInfo;
	// FIXME 아래와 같이 CientInfo 객체를 이용하는 방식이 동작하지 않는다..
//	ClientInfoV2 clientInfo;

	@DynamoDbPartitionKey
	@DynamoDbAttribute("id")
	public String getId() {
		return id;
	}

	@DynamoDbAttribute("client_info")
	public Map<String, String> getClientInfo() {
		return clientInfo;
	}

	public void setClientInfo(Map<String, String> clientInfo) {
		this.clientInfo = clientInfo;
	}

	@DynamoDbSortKey
	public String getCreatedAt() {
		return createdAt;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MgClientTableV2() {}
	public MgClientTableV2(String id, Map<String, String> clientInfo) {
		this.id = id;
		this.clientInfo = clientInfo;
	}

	/**
	 * 결과를 확인하기 위한 toString()
	 */
	@Override
	public String toString() {
		return "MgClientTableV2 [id=" + id + ", clientInfo=" + clientInfo + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + ", insertUser=" + insertUser + ", updatedUser=" + updatedUser + "]";
	}

}
