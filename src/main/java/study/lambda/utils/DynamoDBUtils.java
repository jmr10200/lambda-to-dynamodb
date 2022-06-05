package study.lambda.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.amazonaws.util.StringUtils;

import study.lambda.model.MgMember;
import study.lambda.table.v1.BaseTable;
import study.lambda.table.v1.MgMemberTable;
import study.lambda.table.v2.BaseTableV2;

public class DynamoDBUtils {

	
	public static void insertCommonItem(BaseTable baseTable) {
		baseTable.setCreatedAt(getTransactionTimeNow());
		baseTable.setUpdatedAt(getTransactionTimeNow());
		baseTable.setInsertUser("insert-user");
		baseTable.setUpdatedUser("insert-user");
		baseTable.setVersion(0);
	}
	
	public static void updateCommonItem(BaseTable baseTable) {
		baseTable.setUpdatedAt(getTransactionTimeNow());
		baseTable.setUpdatedUser("update-user");
		baseTable.setVersion(baseTable.getVersion() + 1);
	}
	
	private static String getTransactionTimeNow() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss'Z'");
		return ZonedDateTime.now(ZoneId.of("UTC")).format(formatter);
	}
	
	// V2
	public static void insertCommonItem(BaseTableV2 baseTable) {
		baseTable.setCreatedAt(getTransactionTimeNow());
		baseTable.setUpdatedAt(getTransactionTimeNow());
		baseTable.setInsertUser("insert-user");
		baseTable.setUpdatedUser("insert-user");
		baseTable.setVersion(0);
	}
}
