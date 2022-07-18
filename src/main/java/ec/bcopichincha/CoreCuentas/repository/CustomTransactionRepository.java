package ec.bcopichincha.CoreCuentas.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import ec.bcopichincha.CoreCuentas.model.util.Report;
import ec.bcopichincha.CoreCuentas.model.util.RequestTransaction;
import ec.bcopichincha.CoreCuentas.model.util.Response;

public interface CustomTransactionRepository {

    Optional<Response> executeTransaction(double limitDaily, RequestTransaction requestTransaction);

    List<Report> report(Date dateIni, Date dateEnd,String identification);
}
