package study.lambda.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import study.lambda.model.MgMember;
import study.lambda.table.v1.MgMemberTable;

public interface MgMemberDaoV1 {
	
	void upsertMember(DynamoDBMapper dynamoDBMapper, MgMemberTable mgMemberTable);

	MgMemberTable searchMember(DynamoDBMapper dynamoDBMapper, String id);
}
