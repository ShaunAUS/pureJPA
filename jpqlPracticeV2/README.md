

JPQL 을 사용해서 데이터를 가져올떄 데이터 타입은 어떻게 정할까??

만약 가져온 데이터 타입이 애매하거나 모르겠으면??

**JPQL 조회 타입**

1. Object[] 타입으로조회

3. new 명령어로 조회(DTO 로 반환)
- 단순 값을 바로 DTO 로 바로조회
- 순서와 타입이 일치하는 생성자 필요


**반환값 개수 타입**

1. query.getResultList()

- 결과 하나이상, 결과 없으면 빈리스트 반환, 널포인트 x

2. query.getSingleResult()
- 결과가 정확히 하나, 단일 객체 반환


**반환값 타입 모를떄**

1. TypeQuery : 반환 타입이 명확 할떄
2. Query : 반환 타입이 불명확 할때  -> 요즘 spirng Data JPA에서는 Optional 로 쓴다

더 자세히 보러가기:https://velog.io/@wnsqud70/JPQL-with-Errors
