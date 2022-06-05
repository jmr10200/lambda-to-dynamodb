# lambda-to-dynamodb

## 구현 내용
Lambda를 실행하여 dynamoDB에 데이터를 등록,삭제,조회

## 실행 방법
Lambda에 해당 소스의 jar 파일  혹은 zip 파일로 업로드를 한 후
[테스트]에 아래의 Json 데이터를 입력하여 실행하면 입력한 데이터가 등록된다.

* 입력예 1 : MG_MEMBER의 경우
'''
{
  "id": "v220",
  "mg_name": "yamada",
  "birth_date": "1988/12/12",
  "email_address": "tototototo@example.com"
}
'''

* 입력예 2 : MG_CLIENT의 경우
'''
{
  "id": "aa10",
  "full_name": "山田　太郎",
  "address": "test address 新宿10",
  "email_address": "yamada@example.com"
}
'''
