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
           em.persist(member);


           //TypeQuery vs Query


           Member resultt = em.createQuery("select m from Member m where m.username = :username", Member.class)
                   .setParameter("username", "member1")
                   .getSingleResult();

           System.out.println("singleResult" + resultt.getUsername());


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
