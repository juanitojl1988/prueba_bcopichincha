package ec.bcopichincha.CoreCuentas.model.util;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@XmlRootElement
public class Response {
    private boolean error;
    private String message;
    private Object object;
    public Response(boolean error, String message) {
        this.error = error;
        this.message = message;
    }



    
}
