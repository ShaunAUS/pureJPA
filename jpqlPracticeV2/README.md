**JPQL 프로젝션**

- JPQL 에서 select 절에 조회할 대상을 지정하는것 


**1. 내부조인(inner join)**
= SELECT m FROM Member m [INNER] JOIN m.team t
= 멤버는 있고 팀이 없을 때 아예 안나온다

**2. 외부조인(left outter join)**
= SELECT m FROM Member m LEFT [OUTER] JOIN m.team t
= 멤버는 있고 팀이 없을 때 멤버만 나온다

**3. 세타조인**
= select count(m) from Member m, Team t where m.username =t.name
= 연관관계없는 테이블 비교할때


**JPQL 기본함수**

- 우리가 써왔던 함수들 기능과 매우 흡사하다
- DB종속적이라 DB를 바꾸면 못쓸수 있다.

**사용자 정의 함수**

- 등록되지 않은 함수들 즉 제공하지 않은 함수들은 함수를 등록 해야한다

- 우리가 새로운 함수를 정의하는게 아니고 DB에 원래 있었던 함수들을 JPQL 에 추가해 주는것 


**학습후 느낀점**

-> 쿼리문에 이런 함수들을 쓸수있다는 점은 전혀 몰랐던 사실이라 신기했다. JPQL 은 기존에 사용했던 쿼리문과 매우 흡사하여 자세한 설명은 생략했다.
Spring Data Jpa를 먼저 배우고 다시 뒤로 온 입장으로써 Spring Data Jpa만 알았을 당시의 퍼즐들이 서서히 맞춰지고 채워지는 느낌이든다. 이래서 기초가 중요하구나 또 한번 느꼈다.

더 자세히 보러가기:https://velog.io/@wnsqud70/JPQL-PART-2