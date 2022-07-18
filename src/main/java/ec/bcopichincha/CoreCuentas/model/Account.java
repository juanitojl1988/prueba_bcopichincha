package ec.bcopichincha.CoreCuentas.model;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "account")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Account {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long id;

    @Column(name = "num_account", length = 40, unique = true, nullable = false)
    private String numAccount;

    @Column(name = "type_account", length = 1, nullable = false)
    private Character typeAccount;

    @Column(name = "initial_balance", nullable = false)
    private Double initialBalance;

    @Column(name = "state", nullable = false)
    private Boolean state;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Client client;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;



    

}