package study.lambda.dao.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import study.lambda.dao.MgMemberDaoV1;
import study.lambda.model.MgMember;
import study.lambda.table.v1.MgMemberTable;
import study.lambda.utils.TableMappingUtil;

public class MgMemberDaoImplV1 implements MgMemberDaoV1 {

	
	@Override
	public void upsertMember(DynamoDBMapper dynamoDBMapper, MgMemberTable mgMemberTable) {
		dynamoDBMapper.save(mgMemberTable);
	}

	@Override
	public MgMemberTable searchMember(DynamoDBMapper dynamoDBMapper, String id) {
		return dynamoDBMapper.load(MgMemberTable.class, id);
	}

}
