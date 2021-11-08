package jpql;

import javax.persistence.*;

@Entity
@NamedQuery(
        name = "Member.findByUsername",
        query = "select m from Member m where m.username= :username"

)
public class Member {

    @Id @GeneratedValue
    private long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="TEAM_ID")
    private Team team;


    public void changeTeam(Team team){

        this.team=team;
        team.getMembers().add(this);

    }

    public Team getTeam() {
        return team;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setTeam(Team team) {
    }
}
