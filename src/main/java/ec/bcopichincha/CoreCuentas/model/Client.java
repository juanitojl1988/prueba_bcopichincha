package ec.bcopichincha.CoreCuentas.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "client")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client extends Person {

    @Column(name = "username", length = 32, unique = true, nullable = false)
    private String username; // username =clienteid

    @Column(name = "password", length = 25, nullable = false)
    private String password;

    @Column(name = "state", nullable = false)
    private Boolean state;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;


    
    public Client(Long id) {
        super(id);
    }



    public Client(String username, String password, Boolean state) {
        this.username = username;
        this.password = password;
        this.state = state;
    }

   

    public Client(String identificationCard, String name, Character gender, Integer age, String address,
            String telephone, String username, String password, Boolean state, Date updatedAt) {
        super(identificationCard, name, gender, age, address, telephone);
        this.username = username;
        this.password = password;
        this.state = state;
        this.updatedAt = updatedAt;
    }

}
