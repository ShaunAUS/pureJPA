package jpql;

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

           Member member= new Member();
           member.setUsername("member1");
           member.setAge(10);
           em.persist(member);



           em.flush();
           em.clear();

            //프로젝션

           // 결과 타입을 모를때   new연산자
           List<MemberDTO> query = em.createQuery("select new jpql.MemberDTO(m.username,m.age) from Member m",MemberDTO.class
           )
                   .getResultList();

           MemberDTO memberDTO=query.get(0);
           System.out.println("username" + memberDTO.getUsername());
           System.out.println("age" + memberDTO.getAge());




            // 결과 타입을 모를때   Object[]
         /*   List<Object[]> query = em.createQuery("select m.username,m.age from Member m")
                   .getResultList();
            
            Object[] result=query.get(0);
           System.out.println("username" + result[0]);
           System.out.println("age" + result[1]);*/






           //이런 쿼리는 쿼리문을 보고 join문이 나가는지 알수가 없어서 비추하는 방법이다
           /*List<Team> query = em.createQuery("select m.team from Member m", Team.class)
                   .getResultList();*/


           //이렇게 써야 join이 들어간다고 명시적으로 보여줘야 인식이 쉽다.
/*
           List<Team> query = em.createQuery("select t from Member m.team", Team.class)
                   .getResultList();
*/






           //TypeQuery vs Query
           /*Member resultt = em.createQuery("select m from Member m where m.username = :username", Member.class)
                   .setParameter("username", "member1")
                   .getSingleResult();

           System.out.println("singleResult" + resultt.getUsername());*/


           //위에처럼 바꿔쓰는게 좋다
           /*TypedQuery<Member> result1 = em.createQuery("select m from Member m where m.username = :username", Member.class);
           result1.setParameter("username","member1");
           Member singleResult=result1.getSingleResult();
           System.out.println("singleResult" + singleResult.getUsername());*/





           /*//두번쨰 파라미터는 타입정보 //타입 정보 정확히 써서 제네릭 쓰여진다
           TypedQuery<Member> result1 = em.createQuery("select m from Member m", Member.class);
           // 컬렉션으로 결과 받기 // 결과가 없으면 빈리스트 반환, 널포인트 걱정 x
           List<Member> resultList = result1.getResultList();



           //결과 하나만 받기// ★결과가 정확히 하나 나와야함★ // 아니면 예외  --> SpringDataJpa 에서는 요즘 Optional 쓴다
           *//*TypedQuery<Member> result1 = em.createQuery("select m.username from Member m", Member.class);*//*
           Member singleResult = result1.getSingleResult();


           // 다른 타입(String) 정보 넣기
           TypedQuery<String> result2 = em.createQuery("select m.username from Member m",String.class);

           //타입 정보 모르면
           Query result3 = em.createQuery("select m.username,m.age from Member m");*/


           tx.commit();
        } catch(Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }
    }

}
