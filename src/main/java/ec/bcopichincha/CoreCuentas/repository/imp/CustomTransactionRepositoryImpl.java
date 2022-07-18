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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class CustomTransactionRepositoryImpl implements CustomTransactionRepository {

    private final static String SQL_GET_ACCOUNT = "select * from account a where a.num_account =:numAccount";
    private final static String SQL_GET_AMOUNT_DAY = "select sum(value) from transaction t where t.account_id =:account and t.state =true and t.type_transaction ='D'  and CAST(created_at AS DATE)=CAST(now() AS DATE)";
    private final static String SQL_GET_BALANCE = "select * from transaction t where t.account_id =:account and t.state =true order by t.created_at  desc limit 1";

    private final static String SQL_REPORT = "select t.created_at,c.name,a.num_account , (case when a.type_account='A' then 'Ahorros' else 'Corriente' end) as type_account , a.initial_balance ,t.state,t.value ,t.balance "
            + " from  transaction t"
            + " inner join account a on t.account_id =a.id "
            + " inner join client c  on c.id =a.client_id "
            + " where t.created_at  between :fechaIni and :fechaEnd"
            + " and c.identification_card =:identification order by t.created_at desc";

    @Autowired
    private EntityManagerFactory emf;

    @Override
    public Optional<Response> executeTransaction(double limitDaily, RequestTransaction requestTransaction) {
        EntityManager entityManager = null;
        try {
            boolean isCredit = requestTransaction.getAmount().doubleValue() > 0;
            double balance = 0;
            double value = requestTransaction.getAmount().doubleValue() < 0
                    ? (-1 * requestTransaction.getAmount().doubleValue())
                    : requestTransaction.getAmount().doubleValue();
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
            if (!isCredit) {
                // verifica que tenga saldo en la cuenta
                if (lastTransaction == null || balance == 0
                        || (balance - value) < 0) {
                    return Optional.of(new Response(true, "Saldo no disponible"));
                }

                // verifica que no se haya pasado el limite
                lQuery = entityManager.createNativeQuery(SQL_GET_AMOUNT_DAY);
                setParametersQuery(lQuery, Map.of("account", account.getId()));
                double totalInDay = lQuery.getSingleResult() == null ? 0
                        : Double.parseDouble(lQuery.getSingleResult().toString());

                double sumTotal = totalInDay + value;
                if (sumTotal > limitDaily) {
                    return Optional.of(new Response(true, "Cupo diario Excedido"));
                }
            }
            // empieza la transaccion
            entityManager.getTransaction().begin();
            Transaction untransaction = new Transaction();
            untransaction.setAccount(account);
            untransaction.setState(true);
            untransaction.setTypeTransaction(isCredit ? 'C' : 'D');
            untransaction.setDescription(isCredit ? "Deposito de " + value : "Retiro de " + value);
            untransaction.setValue(value);
            untransaction.setBalance(isCredit ? (balance + value) : (balance - value));
            untransaction.setCreatedAt(new Date());
            entityManager.persist(untransaction);
            entityManager.getTransaction().commit();
            untransaction.getAccount().setClient(null);
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
    public List<Report> report(Date dateIni, Date dateEnd, String identification) {
        EntityManager entityManager = null;
        List<Report> listReport = new ArrayList();
        try {
            // t.created_at,c.name,a.num_account , (case when a.type_account='A' then
            // 'Ahorros' else 'Corriente' end) as type_account ,
            // a.initial_balance 5 ,t.state 6,t.value 7 ,t.balance
            entityManager = emf.createEntityManager();
            var query = entityManager.createNativeQuery(SQL_REPORT);
            query.setParameter("fechaIni", dateIni);
            query.setParameter("fechaEnd", dateEnd);
            query.setParameter("identification", identification);
            List<Object[]> list = query.getResultList();
            if (list != null && !list.isEmpty()) {
                for (Object[] array : list) {
                    Report rep = new Report();
                    rep.setClient((String) array[1]);
                    rep.setAvailable_balance((Double)array[7]);
                    rep.setDate((Date)array[0]);
                    rep.setMovement((Double)array[6]);
                    rep.setNum_account((String) array[2]);
                    rep.setState(true);
                    rep.setType((String) array[3]);
                    listReport.add(rep);
                }

            }
            return listReport;
        } catch (Exception ex) {
            log.error("Error al generar el reporte", ex);
            return Collections.EMPTY_LIST;
        }
    }
}
