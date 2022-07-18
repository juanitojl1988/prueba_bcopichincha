package ec.bcopichincha.CoreCuentas.repository.imp;

import ec.bcopichincha.CoreCuentas.model.Account;
import ec.bcopichincha.CoreCuentas.model.Transaction;
import ec.bcopichincha.CoreCuentas.model.util.Report;
import ec.bcopichincha.CoreCuentas.model.util.RequestTransaction;
import ec.bcopichincha.CoreCuentas.model.util.Response;
import ec.bcopichincha.CoreCuentas.repository.CustomTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class CustomTransactionRepositoryImpl implements CustomTransactionRepository {

    private final static String SQL_GET_ACCOUNT = "select * from account a where a.num_account =:numAccount";
    private final static String SQL_GET_AMOUNT_DAY = "select sum(value) from transaction t where t.account_id =:account and t.state =true and t.type_transaction ='D'  and created_at =now()";
    private final static String SQL_GET_BALANCE = "select * from transaction t where t.account_id =:account and t.state =true order by t.created_at  desc limit 1";

    @Autowired
    private EntityManagerFactory emf;

    @Override
    public Optional<Response> executeTransaction(double limitDaily, RequestTransaction requestTransaction) {
        EntityManager entityManager = null;
        try {
            boolean isCredit = true;
            double balance = 0;
            entityManager = emf.createEntityManager();
            Query lQuery = entityManager.createNativeQuery(SQL_GET_ACCOUNT, Account.class);
            setParametersQuery(lQuery, Map.of("numAccount", requestTransaction.getNumAccount().trim()));
            Account account = (Account) lQuery.getSingleResult();
            if (account == null) {
                return Optional.of(new Response(true, "Cuenta No Existe"));
            }
            if (!account.getState()) {
                return Optional.of(new Response(true, "La cuenta esta deshabilitada"));
            }

            lQuery = entityManager.createNativeQuery(SQL_GET_BALANCE, Transaction.class);
            setParametersQuery(lQuery, Map.of("account", account.getId()));
            Transaction lastTransaction = (Transaction) lQuery.getSingleResult();
            balance = lastTransaction == null ? 0 : lastTransaction.getBalance();

            // para cuando se realiza la transacion de Retiro
            if (Double.compare(requestTransaction.getAmount(), 0) == 0) {
                isCredit = false;

                // verifica que tenga saldo en la cuenta
                if (lastTransaction == null || Double.compare(balance, 0) == 0
                        || (balance - requestTransaction.getAmount()) < 0) {
                    return Optional.of(new Response(true, "Saldo no disponible"));
                }

                // verifica que no se haya pasado el limite
                lQuery = entityManager.createNativeQuery(SQL_GET_AMOUNT_DAY, Double.class);
                setParametersQuery(lQuery, Map.of("account", account.getId()));
                Double totalInDay = lQuery.getSingleResult() == null ? 0
                        : Double.parseDouble(lQuery.getSingleResult().toString());
                // if ((totalInDay + requestTransaction.getAmount()) = > limitDaily) {
                // return Optional.of(new Response(true, "Cupo diario Excedido"));
                // }
            }
            // empieza la transaccion
            entityManager.getTransaction().begin();
            Transaction untransaction = new Transaction();
            untransaction.setAccount(account);
            untransaction.setState(true);
            untransaction.setTypeTransaction(isCredit ? 'C' : 'D');
            untransaction.setDescription(isCredit ? "DEposito de " + requestTransaction.getAmount().toString()
                    : "Retiro de " + requestTransaction.getAmount().toString());
            untransaction.setValue(requestTransaction.getAmount());
            untransaction.setBalance(
                    isCredit ? (balance + requestTransaction.getAmount()) : (balance - requestTransaction.getAmount()));
            entityManager.persist(untransaction);
            entityManager.getTransaction().commit();
            return Optional.of(new Response(false, "ok", untransaction));
        } catch (Exception e) {
            log.error("Error al Crear la Transaccion", e);
            try {
                if (entityManager != null && entityManager.getTransaction() != null) {
                    entityManager.getTransaction().rollback();
                }
            } catch (Exception ex) {
                log.error("Error al Crear la Transaccion", ex);
                return Optional.of(new Response(true, "Error al Realizar la transaccion"));
            }
            return Optional.of(new Response(true, "Error al Realizar la transaccion"));
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void setParametersQuery(Query query, Map<String, Object> parameters) {
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public List<Report> report(Date dateIni, Date dateEnd) {
        EntityManager entityManager = null;
        try {
            entityManager = emf.createEntityManager();
            var query = entityManager.createNativeQuery(
                    "select t.created_at,c.name,a.num_account ,a.type_account , a.initial_balance ,t.state,t.value ,t.balance  from  transaction t "
                            +
                            "inner join account a on t.account_id =a.id " +
                            "inner join client c  on c.id =a.client_id  " +
                            "where t.created_at  between :fechaIni and :fechaEnd",
                    Report.class);
            query.setParameter("fechaIni", dateIni);
            query.setParameter("fechaIni", dateEnd);
            return query.getResultList();
        } catch (Exception ex) {
            log.error("Error al generar el reporte", ex);
            return Collections.EMPTY_LIST;
        }
    }
}
