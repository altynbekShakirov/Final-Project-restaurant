package peaksoft.serivice;

import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.request.ChequeUpdateRequest;
import peaksoft.dto.response.ChequeFinalResponse;
import peaksoft.dto.response.ChequeResponse;
import peaksoft.dto.response.SimpleResponse;

import java.util.Set;

public interface ChequeService {
    SimpleResponse saveCheque(ChequeRequest chequeRequest);
    Set<ChequeResponse> getAllCheque();
//    ChequeFinalResponse getById(Long id);
    SimpleResponse updateCheque(Long id, ChequeUpdateRequest chequeRequest);
    SimpleResponse deleteById(Long id);
    ChequeFinalResponse findById(Long id);

}
