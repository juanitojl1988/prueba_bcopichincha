package ec.bcopichincha.CoreCuentas.service;

import ec.bcopichincha.CoreCuentas.exeption.ClientNotFoundException;
import ec.bcopichincha.CoreCuentas.model.Client;
import ec.bcopichincha.CoreCuentas.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService implements ServiceInterface<Client, Long> {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Page<Client> findAll(Integer page, Integer size) {
        return this.clientRepository.findAll(PageRequest.of(page, size));
    }

    public Page<Client> findAllByState(Integer page, Integer size) {
        return this.clientRepository.findAllByState(PageRequest.of(page, size));
    }

    @Override
    public Client update(Client entity) {

        Optional<Client> unClient = this.clientRepository.findById(entity.getId());
        if (!unClient.isPresent()) {
            throw new ClientNotFoundException();
        }
        unClient.get().setAddress(entity.getAddress());
        unClient.get().setAge(entity.getAge());
        unClient.get().setGender(entity.getGender());
        unClient.get().setIdentificationCard(entity.getIdentificationCard());
        unClient.get().setName(entity.getName());
        unClient.get().setPassword(entity.getPassword());
        unClient.get().setState(entity.getState());
        unClient.get().setTelephone(entity.getTelephone());
        unClient.get().setUpdatedAt(entity.getUpdatedAt());
        unClient.get().setUsername(entity.getUsername());
        return this.clientRepository.save(unClient.get());
    }

    @Override
    public Client save(Client entity) {
        return this.clientRepository.save(entity);
    }

    @Override
    public Client findOne(Long id) {
        return this.clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        Optional<Client> unClient = this.clientRepository.findById(id);
        unClient.ifPresentOrElse(c -> {
            this.clientRepository.deleteById(id);
        }, () -> {
            throw new ClientNotFoundException();
        });
    }

}
