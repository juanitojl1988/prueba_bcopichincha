package ec.bcopichincha.CoreCuentas;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import ec.bcopichincha.CoreCuentas.controller.AccountController;
import ec.bcopichincha.CoreCuentas.model.Account;
import ec.bcopichincha.CoreCuentas.model.Client;
import ec.bcopichincha.CoreCuentas.service.AccountService;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AccountController.class)
@SpringBootTest
public class ClientControllerTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mockMvc;

    @Mock
    AccountService accountService;

    @Test
    public void createAccount() throws Exception {

        Account account = new Account();
        account.setId(1L);
        account.setClient(new Client(1L));
        account.setInitialBalance(100.00);
        account.setState(true);
        account.setTypeAccount('C');
        account.setUpdatedAt(new Date());

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/account/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(account));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.state", is("C")));
    }

    @Test
    public void getAccountByIdAPI() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/employees/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").value(1));
    }

}
