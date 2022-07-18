package ec.bcopichincha.CoreCuentas.repository;

import ec.bcopichincha.CoreCuentas.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {

    static String SQL_CLIENT_BY_STATE = "select * from client c  where c.state =true";

    Page<Client> findAll(Pageable pageable);

    @Query(nativeQuery = true, value = SQL_CLIENT_BY_STATE)
    Page<Client> findAllByState(Pageable pageable);

}
