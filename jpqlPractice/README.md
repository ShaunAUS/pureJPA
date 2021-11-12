
**왜 JPQL을 사용할까**

- 순수 JPA에서는 지금까지 조회를 할떄 Find() 메서드를 통하여 간단한 검색만 해왔다. 조금더 복잡하고 디테일한 쿼리는 JPA에서 어떻게 작설할까?

- EX) Member중 age가 20이상인 회원을 검색한다면?

**JPQL**

- 엔티티 객체 대상 / SQL 은 데이터 베이스 테이블 대상

- JPA는 SQL을 추상화한 JPQL이라는 객체지향 쿼리 언어 제공

- JPQL은 결국 SQL로 변환된다.

**Native Query**
 
- 문법이 SQL이랑 똑같네 그럼 이건 대체 언제쓰는것인가??  -> JQL로 해결할수 없는 특정 데이터베이스에 의존적인 기능



**프로젝트 생성시 오류**


<img width="438" alt="오류 1" src="https://user-images.githubusercontent.com/86937655/141488060-3639ccb9-f157-4de0-8ac7-5546362920ee.PNG">

- 오류에는 구글링 만한게 없다. 바로 구글링을 해주자!

- maven 프로젝튼 따로설정하지 않는 이상 자바 default 버전으로 1.5를 쓴다고한다. 이걸 8로 바꿔주자.

<img width="420" alt="오류2" src="https://user-images.githubusercontent.com/86937655/141488081-aa8b98a7-c829-44a0-95df-d1cceb133aab.PNG">

**JAXB**

- Java Architecture for XML Bind을 뜻한다.

- 우리는 Maven 형식으로 프로젝트를 구성할때 등등 프로젝트를 하면서 많은 xml을 사용하는데 xml을 java Object 형식으로 바꿔주는 역할을 하는 기술이 JAXB 였다


**왜 이 오류가 일어났을까??**

- JAXB(Java Architecture for XML Binding)는 Java6에서 2.0버전으로 내장되었고, Java9에서 모듈화 방식을 사용하면서 vm옵션을 통해 모듈을 추가(--add-modules java.xml.bind)해야 사용 가능하였고, Java11부터는 결국 제거되었습니다.

-> 결국에는 JAXB가 없어서 일어난 일이다. 적절한 디펜던시를 추가해주자!


좀더 자세한 내용은 https://velog.io/@wnsqud70/JPQL-with-Errors
