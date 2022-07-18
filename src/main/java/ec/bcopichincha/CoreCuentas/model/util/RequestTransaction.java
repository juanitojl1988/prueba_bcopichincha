package ec.bcopichincha.CoreCuentas.model.util;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@XmlRootElement
public class RequestTransaction {
    
private String numAccount;
private Character typeAccount;
private Double amount;


}
