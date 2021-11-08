package jpql;

import org.hibernate.dialect.MySQL57Dialect;
import org.hibernate.dialect.MySQL5Dialect;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class MainJpa {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx= em.getTransaction();
        tx.begin();


       try{



               Team teamA = new Team();
               teamA.setName("teamA");
               em.persist(teamA);


               Team teamB = new Team();
               teamB.setName("teamB");
               em.persist(teamB);


               Member member1= new Member();
               member1.setUsername("member1");
               member1.setTeam(teamA);
               em.persist(member1);

               Member member2= new Member();
               member2.setUsername("member2");
               member2.setTeam(teamA);
               em.persist(member2);

               Member member3= new Member();
               member3.setUsername("member3");
               member3.setTeam(teamB);
               em.persist(member3);


               em.flush();
               em.clear();


           //벌크 연산
           // 영속성 컨텍스트 말고 바로 DB에 결과를 반영한다
           // 그래서 벌크연산후 Clear() 필수 !Spring data jpa 에서는 @Modifying 이 자동으로 clear해준다.
           int result = em.createQuery("update Member m set m.age = 20")
                   .executeUpdate();


           //영속성 컨텍스트 1차캐시 AGE값은 여전히 0 // DB는 AGE= 20
           System.out.println("member1.getAge() = " + member1.getAge());
           System.out.println("member2.getAge() = " + member2.getAge());
           System.out.println("member3.getAge() = " + member3.getAge());

           System.out.println("result"+ result);




           //named쿼리
           //엔티티위에 애노테이션으로 만들어준다.
           // = @Query 도 named쿼리 기반으로 만들어져 자동으로 어플리케이션 로딩시점에 쿼리 검증 해줌

           /*List<Member> resultList = em.createNamedQuery("Member.findByUsername", Member.class)
                   .setParameter("username", "회원1")
                   .getResultList();

           for (Member member : resultList) {
               System.out.println("member" + member);
           }*/


           // 엔티티 직접 사용
           //  m =  쿼리문이 나갈때 m.id(pk값) 으로 변환된다
           /*String query = "select m from Member m where m = :member";

           Member singleResult = em.createQuery(query, Member.class)
                   .setParameter("member", member1)
                   .getSingleResult();


           System.out.println("findMember = "+ singleResult);
*/


           //엔티티 직접사용 외래키
           //  m.team =  쿼리문이 나갈때 team.id(fk값) 으로 변환된다
          /* String query2 = "select m from Member m where m.team= :team";

           List<Team> resultList = em.createQuery(query2, Team.class)
                   .setParameter("team", teamA)
                   .getResultList();


           for (Team team : resultList) {

               System.out.println("resultList" + resultList);

           }*/




           //fetch join
           // 연관된 테이블 한꺼번에 가져오기
           

           /*String query = "select m from Member m";

           List<Member> result = em.createQuery(query, Member.class)
                   .getResultList();

           for (Member member : result) {
               System.out.println("member = "+ member.getUsername()+ "," + member.getTeam().getName());*/

               //Member 1 = 팀A(SQL)   // 쿼리 두방 날라감
               //Member 2 = 팀A(1차캐시)    -> Member 1 db에서 가져오고 1차캐시에 데이터 넣어둔다
               //Member 3  팀 B(SQL)  // 또 쿼리 두방 날라감

               // 회원 10000명  ->    N+1 문제
               // -> 쿼리 많이 날려 성능 저하 문제 발생

           //}






           //fetch join 사용
           /*String query2 = "select m from Member m join fetch m.team";

           // fetch join 사용시 데이터를 한방쿼리로 다 불러왔으니 team 은 프록시가 아니고 진짜 데이터가 온다.
           List<Member> result2 = em.createQuery(query2, Member.class)
                   .getResultList();

           for (Member member : result2) {
               System.out.println("member = "+ member.getUsername()+ "," + member.getTeam().getName());

           }*/

           
           //컬렉션 fetch join


           /*String query3 = "select t from Team t join fetch t.members";*/
           // 일대다 관계에서 데이터 뻥튀기 -> Distinct 제거 하지만 쿼리만으로는 안된다.
           //애플리케이션에 데이터가 올라올대 JPA가 중복제거 한번더 해준다. (컬렉션 부분에서 중복제거)

           /*String query3 = "select distinct t from Team t join fetch t.members";

           List<Team> result3 = em.createQuery(query3, Team.class)
                   .getResultList();

           for (Team team : result3) {
               System.out.println("team = "+ team.getName()+ "," + team.getMembers().size());

           }
*/







           //컬렉션 값 연관 경로

           // 어떤 멤버를 정할지 모른다. 묵시적 내부조인은 발생한다.
           //어떤 멤버를 정할지 모르니 . 으로 찍을수있는게 없다.(탐색불가) // 명시적 조인으로 해결 가능//  size()는 가능
           //잘 쓰이지는 않는다. 왜냐하면
          /* String query33 = "select t.members from Team t";

           Collection resultList = em.createQuery(query33, Collection.class)
                   .getResultList();

           for (Object s : resultList) {
               System.out.println("s = "+s);
           }*/




           //단일값 연관경로

          /*String query23 = "select m.team from Member m";
           List<Team> resultList2 = em.createQuery(query23, Team.class)
                   .getResultList();

           for (Team s : resultList2) {
               System.out.println("s = "+s);
           }*/




           //JPQL 함수

          /* String query = "select locate('de', 'abcdefg')from Member m";
           String query = "select substring(m.username, 2, 3)from Member m";
           String query = "select concat('a','b')from Member m";


           List<String> resultList = em.createQuery(query, String.class)
                   .getResultList();

           for (String s : resultList) {
               System.out.println("s = "+s);
           }*/




           //JPQL 사용자 정의 함수

           /*String query = "select function ('group_concat',m.username)from Member m";
           List<String> resultList = em.createQuery(query, String.class)
                   .getResultList();

           for (String s : resultList) {
               System.out.println("s = "+s);
           }*/





           //nullif
           // 두 값이 같으면 null 반환, 다르면 첫번쨰 값 반환
           //m.username 어드민 이면 null반환
/*           String query = "select coalesce(m.username,'어드민')from Member m";


           List<String> resultList = em.createQuery(query, String.class)
                   .getResultList();

           for (String s : resultList) {
               System.out.println("s = "+s);
           }*/


           //COALESCE
           //하나씩 조회해서 null이 아니면 반환
           // m.username 이없으면 '이름없는 회원' 으로 반환
           /*String query = "select coalesce(m.username,'이름 없는 회원')from Member m";


           List<String> resultList = em.createQuery(query, String.class)
                   .getResultList();

           for (String s : resultList) {
               System.out.println("s = "+s);
           }*/




           //case식
           //자바의 switch case 와 매우 흡사하다
           // 나중에 querydsl 에서 더 편하게 사용할수 있다. 이런게 있다 알아만두자
          /* String query = "select" +
                   "case when m.age<=10 then '학생요금' " +
                   "case when m.age>=10 then '일반요금' " +
                   "else '일반요금' end from Member m";

           List<String> resultList = em.createQuery(query, String.class)
                   .getResultList();

           for (String s : resultList) {
               System.out.println("s = "+s);
           }*/


           //서브쿼리
           //일반적인 SQL 서브 쿼리와 같다
           // 메인쿼리와 서브쿼리는 서로 전혀 관계가없다. // 메인쿼리에서 서브쿼리로 끌고오면 성능이 잘안나온다




           //조인쿼리
           //inner join ( inner생략가능)
           /*List<Member> result=em.createQuery("select m from Member m inner join m.team t",Member.class)
                   .getResultList();

           //inner join2
           List<Member> result2=em.createQuery("select m from Member m inner join m.team t where t.name = :teamName",Member.class)
                   .getResultList();

           //left outer join( outer 생략가능)
           List<Member> result3=em.createQuery("select m from Member m left join m.team t",Member.class)
                   .getResultList();

           //세타 조인
           // 연관 관계가 없는 테이블에서 쓰인다.
           //막 조인한다그래서 막조인이라고도 불린다
           List<Member> result4=em.createQuery("select m from Member m, Team t where m.username=t.name",Member.class)
                   .getResultList();*/



           // on절
           // 1,조인 대상 필터링(조건걸기)  2, 연관관계 없는 엔티티 외부조인 가능
           // member 과 team 을 불러오는데  team.name 이 teamA인것만
           /*List<Member> result5=em.createQuery("select m from Member m left join m.team t on t.name='teamA' ",Member.class)
                   .getResultList();


           //연관관계 없는 엔티티 외부조인
           List<Member> result6=em.createQuery("select m from Member left outer join Team t on m.username = t.name",Member.class)
                   .getResultList();*/





           //페이징
           //setFirstResult 몇번쨰부터   // setMaxResults 몇개 가져 올꺼야

           /*List<Member> result=em.createQuery("select m from Member m order by m.age desc",Member.class)
                   .setFirstResult(1)
                   .setMaxResults(10)
                   .getResultList();


           System.out.println("result size" + result.size());*/





           //프로젝션
           //프로젝션 반환 값 타입

           // 결과 타입을 모를때//   new연산자
           // DTO에 바로 넣어서 반환한다. // 패키지주소랑 잘 적어줘야함
           // 순서와 타입이 일치하는 생성자 필요
          /* List<MemberDTO> query = em.createQuery("select new jpql.MemberDTO(m.username,m.age) from Member m",MemberDTO.class
           )
                   .getResultList();

           MemberDTO memberDTO=query.get(0);
           System.out.println("username" + memberDTO.getUsername());
           System.out.println("age" + memberDTO.getAge());*/




           // 결과 타입을 모를때   Object[]
           /* List<Object[]> query = em.createQuery("select m.username,m.age from Member m")
                   .getResultList();

            Object[] result=query.get(0);
           System.out.println("username" + result[0]);
           System.out.println("age" + result[1]);*/





           tx.commit();
        } catch(Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }
    }

}
