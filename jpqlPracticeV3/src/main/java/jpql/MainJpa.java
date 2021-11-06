package jpql;

import org.hibernate.dialect.MySQL57Dialect;
import org.hibernate.dialect.MySQL5Dialect;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

public class MainJpa {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx= em.getTransaction();
        tx.begin();

        //값 타입 컬렉션 사용용
       try{


           //페이징



               Team team = new Team();
               team.setName("teamA");
               em.persist(team);


               Member member= new Member();
               member.setUsername("member1");
               member.setAge(10);

               member.setTeam(team);


               em.persist(member);



           em.flush();
           em.clear();


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

           String query = "select function ('group_concat',m.username)from Member m";
           List<String> resultList = em.createQuery(query, String.class)
                   .getResultList();

           for (String s : resultList) {
               System.out.println("s = "+s);
           }





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
