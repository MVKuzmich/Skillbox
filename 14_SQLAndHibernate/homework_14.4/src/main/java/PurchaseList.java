import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor

public class PurchaseList {

    @EmbeddedId
    private KeyForLPL id;


    private int price;

    @Column(name = "subscription_date")
    Date subscriptionDate;
}

