package hello2Jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx= em.getTransaction();
        tx.begin();

        //값 타입 컬렉션 사용용
       try{

            Member member= new Member();
            member.setUsername("member1");
            //임베디드 타입
            member.setHomeAddress(new Address("city1","street",10000));

            //FavoriteFoods = set<String>
            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("치킨2");
            member.getFavoriteFoods().add("치킨3");


            //AddressHistory = List<Address>
            member.getAddressHistory().add(new Address("old1","street",10000));
            member.getAddressHistory().add(new Address("old1","street",10000));

            em.persist(member);





        tx.commit();
        } catch(Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }
    }

}
