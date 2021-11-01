package hello2Jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;

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

            em.flush();
            em.clear();

            //조회
           //값 타입 컬렉션은 기본이 '지연로딩'
           //직접 건드려야 진짜 객체데이터가 나온다
           Member findMember = em.find(Member.class, member.getId());
           List<Address> addressHistory = findMember.getAddressHistory();
           for (Address address : addressHistory) {
               System.out.println("address = " + address.getCity());
           }


           Set<String> favoriteFoods = findMember.getFavoriteFoods();
           for (String favoriteFood : favoriteFoods) {

               System.out.println("favoriteFood = "+ favoriteFood);
           }


           tx.commit();
        } catch(Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }
    }

}
