package ec.bcopichincha.CoreCuentas.model.util;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Response {
    private boolean error;
    private String message;
    private Object object;
    public Response(boolean error, String message) {
        this.error = error;
        this.message = message;
    }



    
}
