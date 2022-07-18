package ec.bcopichincha.CoreCuentas.model.util;

import lombok.*;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@XmlRootElement
public class Report {

    private Date date;
    private String client;
    private String num_account;
    private String type;
    private double initial_balance;
    private boolean state;
    private double movement;
    private double available_balance;

   

}
