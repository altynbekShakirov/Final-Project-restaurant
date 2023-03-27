package peaksoft.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.ChequeOfRestaurantAmountDayRequest;
import peaksoft.dto.request.ChequeOneDayTotalAmountRequest;
import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.request.ChequeUpdateRequest;
import peaksoft.dto.response.*;
import peaksoft.dto.response.pageResponse.PageChequeResponse;
import peaksoft.serivice.ChequeService;

import java.util.Set;

@RestController
@RequestMapping("/api/cheques")
@RequiredArgsConstructor
public class ChequeController {
    private final ChequeService chequeService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse saveCheque(@RequestBody @Valid ChequeRequest chequeRequest) {
        return chequeService.saveCheque(chequeRequest);
    }

    @GetMapping
    @PreAuthorize("permitAll()")
    public Set<ChequeResponse> getAllCheque() {
        return chequeService.getAllCheque();
    }

    @GetMapping("/{id}/get")
    @PreAuthorize("permitAll()")
    public ChequeFinalResponse getByIdCheque(@PathVariable Long id) {
        return chequeService.findById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse delete(@PathVariable Long id) {
        return chequeService.deleteById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse update(@PathVariable Long id, @RequestBody @Valid ChequeUpdateRequest chequeUpdateRequest) {
        return chequeService.updateCheque(id, chequeUpdateRequest);
    }

    @GetMapping("/countWaiter")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ChequeOneDayTotalAmountResponse chequeOneDayTotalAmountResponse(@RequestBody @Valid ChequeOneDayTotalAmountRequest chequeOneDayTotalAmountRequest) {
        return chequeService.findAllChequesOneDayTotalAmount(chequeOneDayTotalAmountRequest);
    }

    @GetMapping("/countRestaurant")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ChequeOfRestaurantAmountDayResponse chequeOfRestaurantAmountDayResponse(@RequestBody @Valid ChequeOfRestaurantAmountDayRequest chequeOfRestaurantAmountDayRequest) {
        return chequeService.countRestGrantTotalForDay(chequeOfRestaurantAmountDayRequest);
    }
    @GetMapping("/page")
    public PageChequeResponse getChequeResponse(@RequestParam int page, @RequestParam int size){
        return chequeService.getPageCheque(page,size);
    }


}
