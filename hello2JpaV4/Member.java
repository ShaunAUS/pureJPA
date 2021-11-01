package hello2Jpa;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;
    private int age;
    private String username;

    @Embedded
    private Address homeAddress;


    //값 타입을 컬렉션으로 저장
    @ElementCollection
    //테이블 명 지정// FK값 지정=JoinColumn
    @CollectionTable(name="FAVORITE_FOOD",joinColumns =
    @JoinColumn(name="MEMBER_ID")
    )
    //테이블 컬럼명
    @Column(name="FOOD_NAME")
    private Set<String> favoriteFoods = new HashSet<>();


    //값 타입을 컬렉션으로 저장
    @ElementCollection
    //테이블 명 지정 // FK값 지정=JoinColumn
    @CollectionTable(name="ADDRESS",joinColumns =
    @JoinColumn(name="MEMBER_ID")
    )
    private List<Address> addressHistory=new ArrayList<>();




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Set<String> getFavoriteFoods() {
        return favoriteFoods;
    }

    public void setFavoriteFoods(Set<String> favoriteFoods) {
        this.favoriteFoods = favoriteFoods;
    }

    public List<Address> getAddressHistory() {
        return addressHistory;
    }

    public void setAddressHistory(List<Address> addressHistory) {
        this.addressHistory = addressHistory;
    }


}
