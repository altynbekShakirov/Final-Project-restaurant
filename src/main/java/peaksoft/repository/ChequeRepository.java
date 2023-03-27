package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.ChequeResponse;
import peaksoft.entity.Cheque;

import java.util.Set;

public interface ChequeRepository extends JpaRepository<Cheque, Long> {
    @Query("select  new peaksoft.dto.response.ChequeResponse(concat(c.user.firstName,' ',c.user.lastName),c.menuItems,c.priceAverage,c.user.restaurant.service)from  Cheque c ")
    Set<ChequeResponse> findAllCheque();

}