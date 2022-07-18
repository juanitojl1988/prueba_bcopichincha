package ec.bcopichincha.CoreCuentas.controller;

import ec.bcopichincha.CoreCuentas.model.Client;
import ec.bcopichincha.CoreCuentas.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client")
@Slf4j
public class ClientController extends AbstractRestHandler {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * Obtiene todos los Clientes por paginas.
     * 
     * @param page
     * @param size
     * @return list
     */
    @RequestMapping(value = "clients", method = RequestMethod.GET, produces = { "application/json" })
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Page<Client> findAll(
            @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
            @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size) {

        log.info("Inicial Consulta de Todos los Clientes");
        return this.clientService.findAll(page, size);
    }

    /**
     * Obtiene todos los Clientes Activos por paginas.
     * 
     * @param page
     * @param size
     * @return list
     */
    @RequestMapping(value = "clientsByState", method = RequestMethod.GET, produces = { "application/json" })
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Page<Client> findAllByState(
            @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
            @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size) {

        log.info("Inicial Consulta de Todos los Clientes por estado");
        return this.clientService.findAllByState(page, size);
    }

    /**
     * Permite Crear Clientes.
     *
     * @param Client client
     * @return client
     */
    @RequestMapping(value = "/client", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
            "application/json" })
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Client create(@Validated @RequestBody Client client) {
        log.info("Inicial Crea un Cliente");

        return this.clientService.save(client);
    }

    /**
     * Permite actualizar Cliente.
     *
     * @param Client client
     * @return client
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = { "application/json" }, produces = {
            "application/json" })
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Client update(@Validated @RequestBody Client client) {
        return this.clientService.update(client);
    }

    /**
     * Permite Recuperar un Cliente
     * 
     * @param id
     * @return Client
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json" })
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Client getClient(@PathVariable("id") Long id) {
        return this.clientService.findOne(id);
    }

    /**
     * Permite eliminar un cliente
     * 
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = { "application/json" })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable("id") Long id) {
        this.clientService.delete(id);
    }

}
