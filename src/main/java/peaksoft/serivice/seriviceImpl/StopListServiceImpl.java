package peaksoft.serivice.seriviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.request.StopListUpdateRequest;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.StopListResponse;
import peaksoft.entity.MenuItem;
import peaksoft.entity.StopList;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.StopListRepository;
import peaksoft.serivice.MenuItemService;
import peaksoft.serivice.StopListService;

import java.util.NoSuchElementException;
import java.util.Set;
@Service
@RequiredArgsConstructor
public class StopListServiceImpl implements StopListService {
    private final StopListRepository stopListRepository;
    private final MenuItemRepository menuItemRepository;
    @Override
    public SimpleResponse saveStopList(Long id, StopListRequest stopListRequest) {
        if(stopListRepository.counts(stopListRequest.date(),menuItemRepository.findById(id).orElseThrow().getName())>0){
            throw new RuntimeException("has already");
        }
        StopList stopList = new StopList();
        stopList.setDate(stopListRequest.date());
        stopList.setReason(stopListRequest.reason());
        var byId = menuItemRepository.findById(id).orElseThrow(()->new NoSuchElementException("This id:"+id+" does not exist"));
        stopList.setMenuItem(byId);
        stopListRepository.save(stopList);
        return new SimpleResponse(HttpStatus.OK,"Successfully saved!!!");
    }

    @Override
    public StopListResponse getById(Long id) {
        return stopListRepository.getByStopListId(id);
    }

    @Override
    public Set<StopListResponse> getAllStopLists() {
        return stopListRepository.getAll();
    }

    @Override
    public SimpleResponse update(Long id, StopListUpdateRequest stopListRequest) {
        StopList stopList = new StopList();
        stopList.setDate(stopListRequest.date());
        stopList.setReason(stopListRequest.reason());
        stopList.setMenuItem(menuItemRepository.findByName(stopListRequest.name()));
        stopListRepository.save(stopList);
        return new SimpleResponse(HttpStatus.OK,"Successfully updated!!!");

    }

    @Override
    public SimpleResponse delete(Long id) {
         stopListRepository.deleteById(id);
         return new SimpleResponse(HttpStatus.OK,"Successfully deleted!!");
    }
}
