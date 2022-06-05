package study.lambda.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.SaveBehavior;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.util.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import study.lambda.constants.Constants;
import study.lambda.dao.MgMemberDaoV1;
import study.lambda.dao.impl.MgMemberDaoImplV1;
import study.lambda.model.MgMember;
import study.lambda.table.v1.MgMemberTable;
import study.lambda.utils.DynamoDBUtils;
import study.lambda.utils.TableMappingUtil;

public class DynamoDbStudyServiceV1 {
	static final Logger logger = LogManager.getLogger(DynamoDbStudyServiceV1.class);
	private AmazonDynamoDB amazonDynamoDB;
	private DynamoDBMapper dynamoDBMapper;
	private DynamoDB dynamoDB;
	private MgMemberDaoV1 mgMemberDaoV1 = new MgMemberDaoImplV1();
	
	public DynamoDbStudyServiceV1(AmazonDynamoDB amazonDynamoDB, DynamoDBMapperConfig mapperConfig) {
		this.amazonDynamoDB = amazonDynamoDB;
		dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB, mapperConfig);
		dynamoDB = new DynamoDB(amazonDynamoDB);
	}
	
	/**
	 * 1건 등록
	 * @param input
	 */
	public void createMgMember(Object input) {
		
		// input 데이터를 모델에 매핑
		Map<String, String> m = (Map<String, String>) input;
		
		// 등록할 데이터 생성
		MgMember member = new MgMember(m.get(Constants.ID), m.get(Constants.NAME), 
				m.get(Constants.BIRTHDATE), m.get(Constants.BIRTHDATE));
		
		// dynamoDB Table 매핑
		MgMemberTable insertMember = TableMappingUtil.memberToMgMemberTable(member);
		DynamoDBUtils.insertCommonItem(insertMember);
		
		// 1건 등록
		mgMemberDaoV1.upsertMember(dynamoDBMapper, insertMember);
		
		// 등록한 데이터를 취득하여 결과 확인
		logger.info("created MgMember");
		MgMemberTable result = dynamoDBMapper.load(MgMemberTable.class, insertMember.getId());
		logger.info("createdMgMember 의 result ? " + result.toString());
	}
	
	/**
	 * 1건 갱신 : 갱신대상의 데이터 정보를 모두 가지고 있어야할 필요가 있다.
	 * 데이터를 취득하여 화면에 표시한 후, 수정화면에서 해당 데이터의 내용을 수정한다고 가정했을 때,
	 * 즉, 데이터취득 -> 수정한 데이터 입력받아서 수정 한다는 이미지
	 */
	public void updateMgMembers(Object input) {
		// input 데이터를 모델에 매핑
		Map<String, String> m = (Map<String, String>) input;
		
		// 갱신하는 아이디로 갱신 데이터를 취득 
		String updateId = m.get(Constants.ID);
		MgMemberTable targetData = mgMemberDaoV1.searchMember(dynamoDBMapper, updateId);
		logger.info("수정할 데이터 : " + targetData.toString());
		
		// 데이터 갱신할 데이터 생성
		MgMember member = new MgMember(m.get(Constants.ID), m.get(Constants.NAME), 
				m.get(Constants.BIRTHDATE), m.get(Constants.BIRTHDATE));
		// dynamoDB Table 매핑
		targetData = TableMappingUtil.memberToUpdateMgMemberTable(targetData, member);
		DynamoDBUtils.updateCommonItem(targetData);
		
		// update 실행
		mgMemberDaoV1.upsertMember(dynamoDBMapper, targetData);
		
		logger.info("update success!! MG MEMBER");
		
		// 갱신한 데이터를 취득하여 업데이트 된 결과확인
		MgMemberTable updatedData = mgMemberDaoV1.searchMember(dynamoDBMapper, targetData.getId());
		logger.info("갱신 결과 확인 :" + updatedData.toString());
	}

	/**
	 * 복수건 등록
	 * @param input
	 */
	public void createMgMembers(Object input) {
		// input 데이터를 모델에 매핑
		Map<String, String> m = (Map<String, String>) input;

		List<MgMember> members = new ArrayList<>();
		int num = 101;
		for (int i = 0; i < 5; i++) {
			// 등록할 데이터 생성
			MgMember member = new MgMember(m.get(Constants.ID), m.get(Constants.NAME), 
					m.get(Constants.BIRTHDATE),	m.get(Constants.BIRTHDATE));
			// string 으로 변환하여 id저장
			String id = String.valueOf(num);
			// 복수데이터 생성하기 위해 id만 바꿔서 리스트 생성
			member.setId(id);
			members.add(member);
			// id += 1
			num++;
		}
		
		// dynamoDB Table 매핑
		List<MgMemberTable> insertMembers = new ArrayList<>();
		for(MgMember mem : members) {
			// 1건씩 매핑하여 MgMemberTable을 생성해준다음
			MgMemberTable insertMember = TableMappingUtil.memberToMgMemberTable(mem);
			DynamoDBUtils.insertCommonItem(insertMember);
			// 리스트에 add하여 생성
			insertMembers.add(insertMember);
		}
		
		// 갱신시에는 아래와같이 셋팅하여 save()
//		DynamoDBUtils.updateCommonItem(updateMember);
		// 복수건 등록
		dynamoDBMapper.batchSave(insertMembers);
	
		// TODO 등록 후 조회하여 확인

	}

	public void deleteMgMembers(Object input) {
		// input 데이터를 모델에 매핑
		Map<String, String> m = (Map<String, String>) input;
		
		List<MgMember> members = new ArrayList<>();
		int num = 1000;
		for (int i = 0; i < 3; i++) {
			MgMember member = new MgMember(m.get(Constants.ID), m.get(Constants.NAME), m.get(Constants.BIRTHDATE),
					m.get(Constants.BIRTHDATE));
			// string 으로 변환하여 id저장
			String id = String.valueOf(num);
			// 복수데이터 생성하기 위해 id만 바꿔서 리스트 생성
			member.setId(id);
			members.add(member);
			// id += 1
			num++;
		}
		// dynamoDB Table 매핑
		List<MgMemberTable> deleteMembers = new ArrayList<>();
		for(MgMember mem : members) {
			// 1건씩 매핑하여 MgMemberTable을 생성해준다음
			MgMemberTable insertMember = TableMappingUtil.memberToMgMemberTable(mem);
			DynamoDBUtils.insertCommonItem(insertMember);
			// 리스트에 add하여 생성
			deleteMembers.add(insertMember);
		}
		
		dynamoDBMapper.batchDelete(deleteMembers);
	}
	
}
