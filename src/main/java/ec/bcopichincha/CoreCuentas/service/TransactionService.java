package ec.bcopichincha.CoreCuentas.service;

import ec.bcopichincha.CoreCuentas.model.Transaction;
import ec.bcopichincha.CoreCuentas.model.util.Report;
import ec.bcopichincha.CoreCuentas.model.util.RequestTransaction;
import ec.bcopichincha.CoreCuentas.model.util.Response;
import ec.bcopichincha.CoreCuentas.repository.TransactionRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class TransactionService implements ServiceInterface<Transaction, Long> {

    @Autowired
    private TransactionRepository transactionRepository;

    @Value("${transaction.limitDaily}")
    private double limitDaily;

    @Override
    public Page<Transaction> findAll(Integer page, Integer size) {
        // TODO Auto-generated method stub
        return null;
    }

    public Response executeTransaction(RequestTransaction requestTransaction) {
        return this.transactionRepository.executeTransaction(limitDaily, requestTransaction).get();
    }

    public List<Report> report(String dateIni, String dateEnd,String identification) throws ParseException {

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateI = formatter.parse(dateIni);
        Date dateF = formatter.parse(dateEnd);
        return this.transactionRepository.report(dateI, dateF,identification);
    }

    @Override
    public Transaction save(Transaction entity) {
        return this.save(entity);
    }

    @Override
    public Transaction update(Transaction entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Transaction findOne(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub

    }

}
