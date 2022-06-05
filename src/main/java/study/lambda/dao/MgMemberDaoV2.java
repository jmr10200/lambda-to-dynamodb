package study.lambda.dao;

import java.util.Iterator;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import study.lambda.table.v2.MgClientTableV2;
import study.lambda.table.v2.MgMemberTableV2;

public interface MgMemberDaoV2 {
	
	void insertMember(DynamoDbTable<MgMemberTableV2> table, MgMemberTableV2 data);

	MgMemberTableV2 searchMember(DynamoDbTable<MgMemberTableV2> table, Key key);

	void batchInsertMembers(DynamoDbEnhancedClient enhancedClient, BatchWriteItemEnhancedRequest batchWriteItemEnhancedRequest);

	Iterator<MgMemberTableV2> scanMembers(DynamoDbTable<MgMemberTableV2> table, ScanEnhancedRequest scanEnhancedRequest);

	/** client */
	void batchInsertClients(DynamoDbEnhancedClient enhancedClient, BatchWriteItemEnhancedRequest batchWriteItemEnhancedRequest);
	Iterator<MgClientTableV2> scanClients(DynamoDbTable<MgClientTableV2> table, ScanEnhancedRequest scanEnhancedRequest);
}
