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

        try{

        Address address =new Address("city","street",10000);

        Member member= new Member();
        member.setUsername("member1");
        member.setHomeAddress(address);
        em.persist(member);

        // set 없애고(객체 불변)  바꿀방법이 없을떄는
        // 하나 바꾸려면 아예 통째로 갈아 끼워넣어야한다
        Address newAddress= new Address("city",address.getStreet(), address.getZipcode());
        member.setHomeAddress(newAddress);



        //값을 복사해서 끼워 넣어야지 사이드 이팩트 피할수 있다
        /*Address copyAddress= new Address(address.getCity(),address.getStreet(), address.getZipcode());
        Member member2= new Member();
        member.setUsername("member2");
        member.setHomeAddress(copyAddress);
        em.persist(member);

        member.getHomeAddress().setCity("newcity");*/

        tx.commit();
        } catch(Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }
    }

}
