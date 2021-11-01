package hello2Jpa;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Embeddable
public class Period {

    //period
    private LocalDateTime startDate;
    private LocalDateTime endDate;


    public Period() {

    }

    public Period(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
