package study.lambda;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.SaveBehavior;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import study.lambda.service.DynamoDbStudyServiceV1;
import study.lambda.service.DynamoDbStudyServiceV2;

public class LambdaFunctionHandler implements RequestHandler<Object, String> {

	static final Logger logger = LogManager.getLogger(LambdaFunctionHandler.class);

	/** V1 */
	private AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.AP_NORTHEAST_1).build();
	private DynamoDBMapperConfig mapperConfig = DynamoDBMapperConfig.builder().withSaveBehavior(SaveBehavior.CLOBBER)
			.build();
	private DynamoDbStudyServiceV1 dynamoDbStudyServiceV1 = new DynamoDbStudyServiceV1(client, mapperConfig);
	
	/** V2 */
	private DynamoDbStudyServiceV2 dynamoDbStudyServiceV2 = new DynamoDbStudyServiceV2();
	
	@Override
	public String handleRequest(Object input, Context context) {

		logger.info("hello lambda!!");
		logger.info("parameter : " + input.toString());

		// JST 출력
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String now = LocalDateTime.now(ZoneId.of("Asia/Tokyo")).format(formatter);
		// UTC 출력
//    	uuuu-MM-DD'T'HH:mm:ss.SSSZ
//    	DateTimeFormatter uformatter = DateTimeFormatter.ofPattern("uuuu-MM-DD'T'HH:mm:ss'Z'");
//    	ZonedDateTime.now(ZoneId.of("UTC")).format(uformatter);
		logger.info("hello lambda!! 現在日時： " + now);

		// V1
		// 1건 등록
//		logger.info("createMgMember start");
//		dynamoDbStudyServiceV1.createMgMember(input);
//		logger.info("createMgMember end");
		// 1건 갱신
//		logger.info("updateMgMember start");
//		dynamoDbStudyServiceV1.updateMgMembers(input);
//		logger.info("updateMgMember end");
		// 복수건 등록
//		logger.info("createMgMembers start");
//		dynamoDbStudyServiceV1.createMgMembers(input);
//		logger.info("createMgMembers end");
		
		// 복수건 삭제
//		logger.info("deleteMgMembers start");
//		dynamoDbStudyServiceV1.deleteMgMembers(input);
//		logger.info("deleteMgMembers end");

		// V2
		// 1건 등록
//		logger.info("createMgMember V2 start");
//		dynamoDbStudyServiceV2.createMember(input);
//		logger.info("createMgMember V2 end");
		// 복수건 등록
		logger.info("createMgMembers V2 start");
		dynamoDbStudyServiceV2.createMembers(input);
		logger.info("createMgMembers V2 end");
		
		
		// V2 client
//		logger.info("createMgClient V2 start");
//		dynamoDbStudyServiceV2.createClient(input);
//		logger.info("createMgClient V2 end");
		
		
		
		
		return "OK!!";
		
		
	}

}
