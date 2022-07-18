package ec.bcopichincha.CoreCuentas.service;

import ec.bcopichincha.CoreCuentas.exeption.AccountNotFoundException;
import ec.bcopichincha.CoreCuentas.model.Account;
import ec.bcopichincha.CoreCuentas.model.Transaction;
import ec.bcopichincha.CoreCuentas.repository.AccountRepository;
import ec.bcopichincha.CoreCuentas.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AccountService implements ServiceInterface<Account, Long> {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Page<Account> findAll(Integer page, Integer size) {
        return this.accountRepository.findAll(PageRequest.of(page, size));
    }

    public Page<Account> findAllByState(Integer page, Integer size) {
        return this.accountRepository.findAllByState(PageRequest.of(page, size));
    }

    public Page<Account> findAllByClient(long cclient, Integer page, Integer size) {

        return this.accountRepository.findAllByClient(cclient, PageRequest.of(page, size));
    }

    @Override
    public Account save(Account entity) {
        Account account = this.accountRepository.save(entity);
        if (account != null) {
            Transaction transaction = new Transaction();
            transaction.setAccount(account);
            transaction.setBalance(account.getInitialBalance());
            transaction.setCreatedAt(new Date());
            transaction.setDescription("Deposito inicial " + account.getInitialBalance());
            transaction.setState(true);
            transaction.setTypeTransaction('C');
            transaction.setValue(account.getInitialBalance());
            this.transactionRepository.save(transaction);
            return account;
        }
        throw new AccountNotFoundException();
    }

    @Override
    public Account update(Account entity) {
        Optional<Account> unAccount = this.accountRepository.findById(entity.getId());
        if (!unAccount.isPresent()) {
            throw new AccountNotFoundException(entity.getId() + "");
        }
        unAccount.get().setInitialBalance(entity.getInitialBalance());
        unAccount.get().setNumAccount(entity.getNumAccount());
        unAccount.get().setTypeAccount(entity.getTypeAccount());
        unAccount.get().setUpdatedAt(entity.getUpdatedAt());
        return this.accountRepository.save(unAccount.get());
    }

    @Override
    public Account findOne(Long id) {
        Optional<Account> unAccount = this.accountRepository.findById(id);
        if (!unAccount.isPresent()) {
            throw new AccountNotFoundException(id + "");
        }
        return unAccount.get();
    }

    @Override
    public void delete(Long id) {
        Optional<Account> unClient = this.accountRepository.findById(id);
        unClient.ifPresentOrElse(c -> {
            this.accountRepository.deleteById(id);
        }, () -> {
            throw new AccountNotFoundException(id + "");
        });
    }

}
