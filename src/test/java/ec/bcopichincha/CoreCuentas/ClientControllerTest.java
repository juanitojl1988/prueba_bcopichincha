package ec.bcopichincha.CoreCuentas;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import ec.bcopichincha.CoreCuentas.controller.AccountController;
import ec.bcopichincha.CoreCuentas.model.Account;
import ec.bcopichincha.CoreCuentas.model.Client;
import ec.bcopichincha.CoreCuentas.repository.ClientRepository;
import ec.bcopichincha.CoreCuentas.service.AccountService;
import ec.bcopichincha.CoreCuentas.service.ClientService;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ClientControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ClientRepository clienteRepositporu;

    @Test
    public void createAccount() throws Exception {
        Client c = null;
        if (!clienteRepositporu.existsById(10L)) {
            c = new Client(10L);
            c.setAddress("ninguna");
            c.setAge(45);
            c.setGender('M');
            c.setIdentificationCard("5555555555");
            c.setName("Juna");
            c.setPassword("23541");
            c.setTelephone("32547");
            c.setUpdatedAt(new Date());
            c.setUsername("pablo");
            c.setState(true);
            clienteRepositporu.save(c);
        }

        Account account = new Account();
        account.setId(10L);
        account.setClient(c);
        account.setInitialBalance(100.00);
        account.setState(true);
        account.setTypeAccount('C');
        account.setUpdatedAt(new Date());

        // when
        ResponseEntity<Account> superHeroResponse = restTemplate.postForEntity("/api/account/create",
                account, Account.class);

        // then
        assertThat(superHeroResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

}
