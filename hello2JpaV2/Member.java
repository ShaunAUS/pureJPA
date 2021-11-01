package hello2Jpa;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;
    private int age;
    private String username;

    //기간 period
    @Embedded
    private Period workPeriod;

    //주소
    @Embedded
    private Address homeAddress;

    public void setId(Long id) {
        this.id = id;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setWorkPeriod(Period workPeriod) {
        this.workPeriod = workPeriod;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Member() {
    }

    public Member(Long id, int age, String username) {
        this.id = id;
        this.age = age;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public String getUsername() {
        return username;
    }

    public Period getWorkPeriod() {
        return workPeriod;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }
}
