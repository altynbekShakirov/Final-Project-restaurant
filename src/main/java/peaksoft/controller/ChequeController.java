package peaksoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.request.ChequeUpdateRequest;
import peaksoft.dto.response.ChequeFinalResponse;
import peaksoft.dto.response.ChequeResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.serivice.ChequeService;

import java.util.Set;

@RestController
@RequestMapping("/api/cheques")
@RequiredArgsConstructor
public class ChequeController {
    private final ChequeService chequeService;
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse saveCheque(@RequestBody ChequeRequest chequeRequest){
        return chequeService.saveCheque(chequeRequest);
    }
    @GetMapping
    @PreAuthorize("permitAll()")
    public Set<ChequeResponse> getAllCheque(){
        return chequeService.getAllCheque();
    }
    @GetMapping("/{id}/get")
    @PreAuthorize("permitAll()")
    public ChequeFinalResponse getByIdCheque(@PathVariable Long id){
        return chequeService.findById(id);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse delete(@PathVariable Long id){
        return chequeService.deleteById(id);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public  SimpleResponse update(@PathVariable Long id, @RequestBody ChequeUpdateRequest chequeUpdateRequest){
        return chequeService.updateCheque(id,chequeUpdateRequest);
    }


}
