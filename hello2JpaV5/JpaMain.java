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
            member.getAddressHistory().add(new AddressEntity("old1","street",10000));
            member.getAddressHistory().add(new AddressEntity("old1","street",10000));

            em.persist(member);

            em.flush();
            em.clear();








           Member findMember = em.find(Member.class, member.getId());

           //이렇게 하면 안됀다(싸이드 이펙트생긴다= 한쪽바꾸면 다른한쪽도 바뀌는) -> 불변으로 바꿔줘야함
           //findMember.getHomeAddress().setCity("newCity");


           //해결방안 =값 타임 수정은 무조건 인스턴스 새로 갈아껴주자
           Address a = findMember.getHomeAddress();
           findMember.setHomeAddress(new Address("newCity",a.getStreet(),a.getZipcode()));


           //수정 시작
           //치킨 ->한식
           //String 자체가 값 타입 , 통쨰로 갈아끼워야 한다.
           findMember.getFavoriteFoods().remove("치킨");
           findMember.getFavoriteFoods().add("한식");

           //컬렉션들은 기본적으로 대상을 찾을때 equals 사용
           //그래서 equals 와 hashCode 재정의 잘 되있어야함
           // 값 타입이기때문에 인스턴스를 통쨰로 갈아 끼워야함
          /* findMember.getAddressHistory().remove(new Address("old1","street",10000));
           findMember.getAddressHistory().add(new Address("newCity","street",10000));*/








           //조회
           //값 타입 컬렉션은 기본이 '지연로딩'
           //직접 건드려야 진짜 객체데이터가 나온다
           /*Member findMember = em.find(Member.class, member.getId());
           List<Address> addressHistory = findMember.getAddressHistory();
           for (Address address : addressHistory) {
               System.out.println("address = " + address.getCity());
           }


           Set<String> favoriteFoods = findMember.getFavoriteFoods();
           for (String favoriteFood : favoriteFoods) {

               System.out.println("favoriteFood = "+ favoriteFood);
           }*/
           //조회 끝

           tx.commit();
        } catch(Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }
    }

}
