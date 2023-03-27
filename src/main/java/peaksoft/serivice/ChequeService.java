package peaksoft.serivice;

import peaksoft.dto.request.ChequeOfRestaurantAmountDayRequest;
import peaksoft.dto.request.ChequeOneDayTotalAmountRequest;
import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.request.ChequeUpdateRequest;
import peaksoft.dto.response.*;
import peaksoft.dto.response.pageResponse.PageChequeResponse;

import java.util.Set;

public interface ChequeService {
    SimpleResponse saveCheque(ChequeRequest chequeRequest);

    Set<ChequeResponse> getAllCheque();

    SimpleResponse updateCheque(Long id, ChequeUpdateRequest chequeRequest);

    SimpleResponse deleteById(Long id);

    ChequeFinalResponse findById(Long id);

    ChequeOneDayTotalAmountResponse findAllChequesOneDayTotalAmount(ChequeOneDayTotalAmountRequest chequeOneDAtTotalAmountRequest);

    ChequeOfRestaurantAmountDayResponse countRestGrantTotalForDay(ChequeOfRestaurantAmountDayRequest chequeOfRestaurantAmountDayRequest);

    PageChequeResponse getPageCheque(int page, int size);
}
