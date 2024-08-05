package hades3.cleanboard.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public class Date {

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    public Date(){
        LocalDateTime now = LocalDateTime.now();
        this.setCreatedDate(now);
        this.setModifiedDate(now);
    }

}
