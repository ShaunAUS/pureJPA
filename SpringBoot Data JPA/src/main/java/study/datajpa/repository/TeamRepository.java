package study.datajpa.repository;


import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;


//순수JPA  CRUD
@Repository
public class TeamRepository {

    @PersistenceContext
    private EntityManager em;

     public Team save (Team team){
         em.persist(team);
         return team;
     }


     public void delete(Team team){
         em.remove(team);
     }

     public List<Team> findAll(){
         return em.createQuery("select t from Team t",Team.class)
                 .getResultList();

     }

    public Optional<Team> findById(Long id){
        Team team =em.find(Team.class,id);
        // member가 null일수도 잇고 아닐수도 있다
        return Optional.ofNullable(team);
    }

    public long count(){
         return em.createQuery("select count(t) from Team t",Long.class)
                 .getSingleResult();
    }
}
