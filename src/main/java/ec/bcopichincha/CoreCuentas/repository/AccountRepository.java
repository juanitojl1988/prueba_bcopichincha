package ec.bcopichincha.CoreCuentas.repository;

import ec.bcopichincha.CoreCuentas.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {

    static String SQL_ACCOUNT_BY_CLIENT = "select * from account a where a.client_id =:cclient and a.state =true";
    static String SQL_ACCOUNT_BY_STATE = "select * from account a where a.state =true";

    Page<Account> findAll(Pageable pageable);

    @Query(nativeQuery = true, value = SQL_ACCOUNT_BY_STATE)
    Page<Account> findAllByState(Pageable pageable);

    @Query(nativeQuery = true, value = SQL_ACCOUNT_BY_CLIENT)
    Page<Account> findAllByClient(@Param("cclient") long cclient, Pageable pageable);

}
