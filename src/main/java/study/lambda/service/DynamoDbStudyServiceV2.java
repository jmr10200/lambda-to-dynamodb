package study.lambda.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTags;
import software.amazon.awssdk.enhanced.dynamodb.mapper.StaticTableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.StaticTableTag;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import study.lambda.constants.Constants;
import study.lambda.dao.MgMemberDaoV2;
import study.lambda.dao.impl.MgMemberDaoImplV2;
import study.lambda.model.MgMember;
import study.lambda.table.v1.MgMemberTable;
import study.lambda.table.v2.ClientInfoV2;
import study.lambda.table.v2.MgClientTableV2;
import study.lambda.table.v2.MgMemberTableV2;
import study.lambda.utils.DynamoDBUtils;
import study.lambda.utils.TableMappingUtil;

public class DynamoDbStudyServiceV2 {

	static final Logger logger = LogManager.getLogger(DynamoDbStudyServiceV2.class);

	private DynamoDbClient dynamoDbClient;
	private DynamoDbEnhancedClient enhancedClient;

	/** MgMemberDaoV2 주입 */
	private MgMemberDaoV2 mgMemberDaoV2 = new MgMemberDaoImplV2();
		
	public DynamoDbStudyServiceV2() {}

	private void createCredential() {
		EnvironmentVariableCredentialsProvider credentialProvider = EnvironmentVariableCredentialsProvider.create();
		dynamoDbClient = DynamoDbClient.builder().region(Region.AP_NORTHEAST_1).credentialsProvider(credentialProvider).build();
		enhancedClient = DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient).build();
	}
	
	/**
	 * 1건 등록
	 * @param input
	 */
	public void createMember(Object input) {
		try {
			createCredential();
			
			// input 데이터를 모델에 매핑
			Map<String, String> m = (Map<String, String>) input;

			// DynamoDb table 생성
			DynamoDbTable<MgMemberTableV2> mgMemberTable = enhancedClient.table("MG_MEMBER",
					TableSchema.fromBean(MgMemberTableV2.class));
			
			// 등록할 member 생성
			MgMemberTableV2 mgMember = new MgMemberTableV2(m.get(Constants.ID), m.get(Constants.NAME),
					m.get(Constants.BIRTHDATE), m.get(Constants.BIRTHDATE));
			DynamoDBUtils.insertCommonItem(mgMember);
			logger.info("등록할 데이터 정보 : " + mgMember.toString());
			
			// 등록실행
			mgMemberDaoV2.insertMember(mgMemberTable, mgMember);
			
			// getItem() : 등록된 데이터 확인 (1건)
			Key key = Key.builder().partitionValue(mgMember.getId()).build();
			MgMemberTableV2 result = mgMemberDaoV2.searchMember(mgMemberTable, key);
			logger.info("등록된 데이터 조회하여 확인(load) : " + result.toString());
			
			// TODO query, partitionKey : 등록된 데이터 확인 (1건)
			
			// close()
			dynamoDbClient.close();
		} catch (DynamoDbException e) {
			logger.error(e.getMessage());
		}

	}
	
	/**
	 * 복수건 등록
	 * @param input
	 */
	public void createMembers(Object input) {
		try {
			createCredential();
			
			// input 데이터를 모델에 매핑
			Map<String, String> m = (Map<String, String>) input;

			// DynamoDb table 생성
			DynamoDbTable<MgMemberTableV2> mgMemberTable = enhancedClient.table("MG_MEMBER",
					TableSchema.fromBean(MgMemberTableV2.class));
			
			List<MgMember> members = new ArrayList<>();
			String prefixId = m.get(Constants.ID).substring(0, m.get(Constants.ID).length()-1);
			int num = 101;
			for (int i = 0; i < 5; i++) {
				// 등록할 데이터 생성
				MgMember member = new MgMember(m.get(Constants.ID), m.get(Constants.NAME), m.get(Constants.BIRTHDATE),
						m.get(Constants.BIRTHDATE));
				// string 으로 변환하여 id저장
				String id = prefixId + String.valueOf(num);
				// 복수데이터 생성하기 위해 id만 바꿔서 리스트 생성
				member.setId(id);
				members.add(member);
				// id += 1
				num++;
			}
			// dynamoDB Table 매핑
			List<MgMemberTableV2> insertMembers = new ArrayList<>();
			for(MgMember mem : members) {
				// 1건씩 매핑하여 MgMemberTable을 생성해준다음
				MgMemberTableV2 insertMember = TableMappingUtil.memberToMgMemberTableV2(mem);
				DynamoDBUtils.insertCommonItem(insertMember);
				// 등록 실행전 데이터 확인
				logger.info("등록할 데이터 정보  mgMember.toString() : " + insertMember.toString());
				// 리스트에 add하여 생성
				insertMembers.add(insertMember);
			}
			
			// FIXME addPutItem의 방법을 다르게 할 순 없나?
			// FIXME 수동으로 5개를 넣어 테스트는 확인했지만, 갯수를 정하지 않고 들어갈 수 있는 방법 찾아야 한다.
			// FIXME each(), stream() 등을 사용한 메소드가 있을 가능성이 존재한다.
			BatchWriteItemEnhancedRequest batchWriteItemEnhancedRequest = BatchWriteItemEnhancedRequest.builder()
					.writeBatches(WriteBatch.builder(MgMemberTableV2.class).mappedTableResource(mgMemberTable)
							.addPutItem(r -> r.item(insertMembers.get(0)))
							.addPutItem(r -> r.item(insertMembers.get(1)))
							.addPutItem(r -> r.item(insertMembers.get(2)))
							.addPutItem(r -> r.item(insertMembers.get(3)))
							.addPutItem(r -> r.item(insertMembers.get(4))).build())
					.build();

			// Add these two items to the table.
			mgMemberDaoV2.batchInsertMembers(enhancedClient, batchWriteItemEnhancedRequest);
			logger.info("batchWriteItem() end");
			/**
			 * 복수건 확인
			 */
			// scan : 등록된 데이터 확인
			ScanEnhancedRequest scanEnhancedRequest = ScanEnhancedRequest.builder()
					.filterExpression(Expression.builder().expression("begins_with(id, :v1)")
							.putExpressionValue(":v1", AttributeValue.builder().s(prefixId).build()).build())
					.build();
			
			Iterator<MgMemberTableV2> results = mgMemberDaoV2.scanMembers(mgMemberTable, scanEnhancedRequest);
	        
			while(results.hasNext()) {
	        	MgMemberTableV2 resultMember = results.next();
	        	logger.info("scan result 확인 : "+ resultMember.toString());
	        }

			// TODO query, filterExpression : 등록된 데이터 확인 (복수)
			
			// close()
			dynamoDbClient.close();
		} catch (DynamoDbException e) {
			logger.error(e.getMessage());
		}

	}

	public void createClient(Object input) {
		try {
			createCredential();
			
			// input 데이터를 모델에 매핑
			Map<String, String> m = (Map<String, String>) input;

			// DynamoDb table 생성
			DynamoDbTable<MgClientTableV2> mgClientTable = enhancedClient.table("MG_CLIENT",TableSchema.fromClass(MgClientTableV2.class));
			
			// 등록할 client 생성
//			ClientInfoV2 clientInfo = new ClientInfoV2(m.get(Constants.FULL_NAME), m.get(Constants.ADDRESS), m.get(Constants.EMAIL_ADDRESS));
			Map<String, String> clientInfo = new HashMap<>();
			clientInfo.put(Constants.FULL_NAME, m.get(Constants.FULL_NAME));
			clientInfo.put(Constants.ADDRESS, m.get(Constants.ADDRESS));
			clientInfo.put(Constants.EMAIL_ADDRESS, m.get(Constants.EMAIL_ADDRESS));
			
			MgClientTableV2 mgClient = new MgClientTableV2(m.get(Constants.ID), clientInfo);
			DynamoDBUtils. insertCommonItem(mgClient);
			// 등록 실행전 데이터 확인
			logger.info("등록할 mgClient 데이터 정보  : " + mgClient.toString());
			// 등록실행
			mgClientTable.putItem(mgClient);
			
			/**
			 * 1건 확인
			 */
			// getItem : 등록된 데이터 확인  (1건)
			Key key = Key.builder().partitionValue(mgClient.getId()).sortValue(mgClient.getCreatedAt()).build();
			MgClientTableV2 result = mgClientTable.getItem(r -> r.key(key));
			logger.info("등록된 데이터 조회하여 확인 : " + result.toString());
			
			// TODO query, partitionKey : 등록된 데이터 확인  (1건)
			
			// close()
			dynamoDbClient.close();
		} catch (DynamoDbException e) {
			logger.error(e.getMessage());
		}

	}
	
	
	public void createClients(Object input) {
		try {
			createCredential();
			
			// input 데이터를 모델에 매핑
			Map<String, String> m = (Map<String, String>) input;

			// DynamoDb table 생성
			DynamoDbTable<MgClientTableV2> mgClientTable = enhancedClient.table(Constants.MG_CLIENT, TableSchema.fromBean(MgClientTableV2.class));
			
			Map<String, String> clientInfo = new HashMap<>();
			clientInfo.put(Constants.FULL_NAME, m.get(Constants.FULL_NAME));
			clientInfo.put(Constants.ADDRESS, m.get(Constants.ADDRESS));
			clientInfo.put(Constants.EMAIL_ADDRESS, m.get(Constants.EMAIL_ADDRESS));

			String prefixId = m.get(Constants.ID).substring(0, m.get(Constants.ID).length()-1);
			
			// dynamoDB Table 매핑
			List<MgClientTableV2> insertClients = new ArrayList<>();
			int num = 1;
			for (int i = 0; i < 5; i++) {
				String id = prefixId + String.valueOf(num);
				MgClientTableV2 client = TableMappingUtil.clientToMgMemberTableV2(clientInfo, id);
				insertClients.add(client);
				num++;
			}
			
			// FIXME addPutItem의 방법을 다르게 할 순 없나?
			// FIXME 수동으로 5개를 넣어 테스트는 확인했지만, 갯수를 정하지 않고 들어갈 수 있는 방법 찾아야 한다.
			// FIXME each(), stream() 등을 사용한 메소드가 있을 가능성이 존재한다.
			BatchWriteItemEnhancedRequest batchWriteItemEnhancedRequest = BatchWriteItemEnhancedRequest.builder()
					.writeBatches(WriteBatch.builder(MgClientTableV2.class).mappedTableResource(mgClientTable)
							.addPutItem(r -> r.item(insertClients.get(0)))
							.addPutItem(r -> r.item(insertClients.get(1)))
							.addPutItem(r -> r.item(insertClients.get(2)))
							.addPutItem(r -> r.item(insertClients.get(3)))
							.addPutItem(r -> r.item(insertClients.get(4))).build())
					.build();

			// Add these two items to the table.
			mgMemberDaoV2.batchInsertClients(enhancedClient, batchWriteItemEnhancedRequest);
			logger.info("batchWriteItem() end");
			
			/**
			 * 복수건 확인
			 */
			// scan : 등록된 데이터 확인
			ScanEnhancedRequest scanEnhancedRequest = ScanEnhancedRequest.builder()
					.filterExpression(Expression.builder().expression("begins_with(id, :v1)")
							.putExpressionValue(":v1", AttributeValue.builder().s(prefixId).build()).build())
					.build();
			
			Iterator<MgClientTableV2> results = mgMemberDaoV2.scanClients(mgClientTable, scanEnhancedRequest);
	        
			while(results.hasNext()) {
				MgClientTableV2 resultMember = results.next();
	        	logger.info("scan result 확인 : "+ resultMember.toString());
	        }

			// TODO query, filterExpression : 등록된 데이터 확인 (복수)
			
			// close()
			dynamoDbClient.close();
		} catch (DynamoDbException e) {
			logger.error(e.getMessage());
		}

	}
}
