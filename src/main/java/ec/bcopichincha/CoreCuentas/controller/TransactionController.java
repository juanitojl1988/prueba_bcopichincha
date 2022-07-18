package ec.bcopichincha.CoreCuentas.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.bcopichincha.CoreCuentas.model.util.Report;
import ec.bcopichincha.CoreCuentas.model.util.RequestTransaction;
import ec.bcopichincha.CoreCuentas.model.util.Response;
import ec.bcopichincha.CoreCuentas.service.TransactionService;

import java.text.ParseException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@PropertySource(ignoreResourceNotFound = true, value = "classpath:otherprops.properties")
@RestController
@RequestMapping("/api/transaction")
@Slf4j
public class TransactionController extends AbstractRestHandler {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    
    /**
     * Permite Crear una Transaccion
     * @param transaction
     * @return
     */
    @RequestMapping(value = "/createTransaction", method = RequestMethod.POST, consumes = {
            "application/json" }, produces = { "application/json" })
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody ResponseEntity createTransaction(@Validated @RequestBody RequestTransaction transaction) {
        log.info("Inicial Crea un Cliente");
        Response response = this.transactionService.executeTransaction(transaction);
        if (response.isError()) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok().body(response);

    }

    @RequestMapping(value = "report", method = RequestMethod.GET, produces = { "application/json" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<Report> report(
			@RequestParam(value = "fechaIni", required = true) String dateIni,
			@RequestParam(value = "fechaFin", required = true) String dateEnd) throws ParseException {

		return this.transactionService.report(dateIni, dateEnd);
	}

}
