package study.lambda.table.v1;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import study.lambda.constants.Constants;


@DynamoDBDocument
@DynamoDBTable(tableName = Constants.MG_CLIENT)
public class MgClientTable extends BaseTable {
	
	String id;
	ClientInfo clientInfo;
	
	@DynamoDBAttribute(attributeName = Constants.ID)
	public String getId() {
		return id;
	}
	
	@DynamoDBAttribute(attributeName = Constants.CLIENT_INFO)
	public ClientInfo getClientInfo() {
		return clientInfo;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public void setClientInfo(ClientInfo clientInfo) {
		this.clientInfo = clientInfo;
	}

	public MgClientTable() {}
	public MgClientTable(String id, ClientInfo clientInfo) {
		this.id = id;
		this.clientInfo = clientInfo;
	}

	@Override
	public String toString() {
		return "MgClientTable [id=" + id + ", clientInfo=" + clientInfo + "]";
	}

}
