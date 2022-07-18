package ec.bcopichincha.CoreCuentas.repository;

import ec.bcopichincha.CoreCuentas.model.Transaction;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long>,CustomTransactionRepository{
    
}
