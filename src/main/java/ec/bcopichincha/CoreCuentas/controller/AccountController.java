package ec.bcopichincha.CoreCuentas.controller;

import ec.bcopichincha.CoreCuentas.model.Account;
import ec.bcopichincha.CoreCuentas.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@Slf4j
public class AccountController extends AbstractRestHandler {

	private final AccountService accountService;

	@Autowired
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	/**
	 * Obtiene todas las cuentas por paginados
	 * 
	 * @param page
	 * @param size
	 * @return list
	 */
	@RequestMapping(value = "accounts", method = RequestMethod.GET, produces = { "application/json" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Page<Account> findAll(
			@RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
			@RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size) {

		log.info("Inicial Consulta de Todos los Clientes");
		return this.accountService.findAll(page, size);
	}

	/**
	 * Obtiene todos los Clientes Activos por paginas.
	 * 
	 * @param page
	 * @param size
	 * @return list
	 */
	@RequestMapping(value = "accountByState", method = RequestMethod.GET, produces = { "application/json" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Page<Account> findAllByState(
			@RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
			@RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size) {

		log.info("Inicial Consulta de Todos los Clientes por estado");
		return this.accountService.findAllByState(page, size);
	}

	/**
	 * Permite Crear Cuentas
	 *
	 * @param Client client
	 * @return client
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody Account create(@Validated @RequestBody Account account) {
		log.info("Inicial Crea un Cliente");
		return this.accountService.save(account);
	}

	/**
	 * Permite actualizar Cuentas.
	 * 
	 * @param Account
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = { "application/json" }, produces = {
			"application/json" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Account update(@Validated @RequestBody Account account) {
		return this.accountService.update(account);
	}

	/**
	 * Permite Recuperar una cuenta por Id
	 * 
	 * @param id
	 * @return account
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Account getClient(@PathVariable("id") Long id) {
		return this.accountService.findOne(id);
	}

	/**
	 * Permite eliminar un Account
	 * 
	 * @param id
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = { "application/json" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteClient(@PathVariable("id") Long id) {
		this.accountService.delete(id);
	}

}
