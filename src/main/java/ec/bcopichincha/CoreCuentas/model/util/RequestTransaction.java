package ec.bcopichincha.CoreCuentas.model.util;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class RequestTransaction {
    
private String numAccount;
private Character typeAccount;
private Double amount;


}
