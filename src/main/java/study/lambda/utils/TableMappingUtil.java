package study.lambda.utils;

import java.util.Map;

import com.amazonaws.util.StringUtils;

import software.amazon.awssdk.utils.CollectionUtils;
import study.lambda.model.MgMember;
import study.lambda.table.v1.MgMemberTable;
import study.lambda.table.v2.MgClientTableV2;
import study.lambda.table.v2.MgMemberTableV2;

public class TableMappingUtil {

	public static MgMemberTable memberToMgMemberTable(MgMember member) {
		MgMemberTable result = new MgMemberTable();

		if (!StringUtils.isNullOrEmpty(member.getId())) {
			result.setId(member.getId());
		}

		if (!StringUtils.isNullOrEmpty(member.getBirthDate())) {
			result.setBirthDate(member.getBirthDate());
		}
		
		if (!StringUtils.isNullOrEmpty(member.getMgName())) {
			result.setMgName(member.getMgName());
		}
		
		if (!StringUtils.isNullOrEmpty(member.getEmailAddress())) {
			result.setEmailAddress(member.getEmailAddress());
		}

		return result;
	}
	
	// V1 갱신
	public static MgMemberTable memberToUpdateMgMemberTable(MgMemberTable targetData, MgMember updateData) {

		// 갱시하는 데이터만 설정
		
		if (!StringUtils.isNullOrEmpty(updateData.getBirthDate())) {
			targetData.setBirthDate(updateData.getBirthDate());
		}
		
		if (!StringUtils.isNullOrEmpty(updateData.getMgName())) {
			targetData.setMgName(updateData.getMgName());
		}
		
		if (!StringUtils.isNullOrEmpty(updateData.getEmailAddress())) {
			targetData.setEmailAddress(updateData.getEmailAddress());
		}
		
		return targetData;
	}

	// V2 
	public static MgMemberTableV2 memberToMgMemberTableV2(MgMember member) {
		MgMemberTableV2 result = new MgMemberTableV2();

		if (!StringUtils.isNullOrEmpty(member.getId())) {
			result.setId(member.getId());
		}

		if (!StringUtils.isNullOrEmpty(member.getBirthDate())) {
			result.setBirthDate(member.getBirthDate());
		}
		
		if (!StringUtils.isNullOrEmpty(member.getMgName())) {
			result.setMgName(member.getMgName());
		}
		
		if (!StringUtils.isNullOrEmpty(member.getEmailAddress())) {
			result.setEmailAddress(member.getEmailAddress());
		}

		return result;
	}

	public static MgClientTableV2 clientToMgMemberTableV2(Map<String, String> clientInfo, String id) {
		MgClientTableV2 result = new MgClientTableV2();
		
		if (!StringUtils.isNullOrEmpty(id)) {
			result.setId(id);
		}
		
		if (!CollectionUtils.isNullOrEmpty(clientInfo)) {
			result.setClientInfo(clientInfo);
		}
		
		return result;
	}

}
