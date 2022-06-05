package study.lambda.dao.impl;

import java.util.Iterator;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import study.lambda.dao.MgMemberDaoV2;
import study.lambda.table.v2.MgClientTableV2;
import study.lambda.table.v2.MgMemberTableV2;

public class MgMemberDaoImplV2 implements MgMemberDaoV2 {

	@Override
	public void insertMember(DynamoDbTable<MgMemberTableV2> table, MgMemberTableV2 data) {
		table.putItem(data);
	}

	@Override
	public MgMemberTableV2 searchMember(DynamoDbTable<MgMemberTableV2> table, Key key) {
		return table.getItem(r -> r.key(key));
	}

	@Override
	public void batchInsertMembers(DynamoDbEnhancedClient enhancedClient,
			BatchWriteItemEnhancedRequest batchWriteItemEnhancedRequest) {
		enhancedClient.batchWriteItem(batchWriteItemEnhancedRequest);
	}

	@Override
	public Iterator<MgMemberTableV2> scanMembers(DynamoDbTable<MgMemberTableV2> table,
			ScanEnhancedRequest scanEnhancedRequest) {
		return table.scan(scanEnhancedRequest).items().iterator();
	}
	
	/**
	 * Client
	 */
	@Override
	public void batchInsertClients(DynamoDbEnhancedClient enhancedClient,
			BatchWriteItemEnhancedRequest batchWriteItemEnhancedRequest) {
		enhancedClient.batchWriteItem(batchWriteItemEnhancedRequest);
	}

	@Override
	public Iterator<MgClientTableV2> scanClients(DynamoDbTable<MgClientTableV2> table,
			ScanEnhancedRequest scanEnhancedRequest) {
		// TODO Auto-generated method stub
		return table.scan(scanEnhancedRequest).items().iterator();
	}
	
	
}
