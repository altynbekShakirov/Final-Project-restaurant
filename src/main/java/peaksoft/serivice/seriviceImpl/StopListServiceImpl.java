package peaksoft.serivice.seriviceImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.StopListResponse;
import peaksoft.dto.response.pageResponse.PageStopListResponse;
import peaksoft.entity.MenuItem;
import peaksoft.entity.StopList;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.StopListRepository;
import peaksoft.serivice.StopListService;

import java.util.Set;

@Service
public class StopListServiceImpl implements StopListService {
    private final StopListRepository stopListRepository;
    private final MenuItemRepository menuItemRepository;

    public StopListServiceImpl(StopListRepository stopListRepository, MenuItemRepository menuItemRepository) {
        this.stopListRepository = stopListRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public SimpleResponse saveStopList(StopListRequest stopListRequest) {
        StopList stopList = new StopList();
        stopList.setDate(stopListRequest.date());
        stopList.setReason(stopListRequest.reason());
        MenuItem byId = menuItemRepository.findByName(stopListRequest.menuName()).orElseThrow(() -> new NotFoundException("This id:" + stopListRequest.menuName() + " does not exist"));
        stopList.setMenuItem(byId);
        stopListRepository.save(stopList);
        return new SimpleResponse(HttpStatus.OK, "Successfully saved!!!");
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
    public SimpleResponse update(Long id, StopListRequest stopListRequest) {
        StopList stopList =stopListRepository.findById(id).orElseThrow(()->new NotFoundException("This id: "+id+" doest not exist!!!"));
        stopList.setDate(stopListRequest.date());
        stopList.setReason(stopListRequest.reason());
        MenuItem menuItem = menuItemRepository.findByName(stopListRequest.menuName()).orElseThrow(() -> new NotFoundException("This name: " + stopListRequest.menuName() + "does not exist!!!"));
        stopList.setMenuItem(menuItem);
        stopListRepository.save(stopList);
        return new SimpleResponse(HttpStatus.OK, "Successfully updated!!!");

    }

    @Override
    public SimpleResponse delete(Long id) {
        StopList stopList = stopListRepository.findById(id).orElseThrow(() -> new NotFoundException("This id:" + id + " does not exist!!"));
        stopListRepository.delete(stopList);
        return new SimpleResponse(HttpStatus.OK, "Successfully deleted!!");
    }
    @Override
    public PageStopListResponse getStopListPage(int currentPage, int size) {
        Pageable pageable = PageRequest.of(currentPage -1, size);
        Page<StopList> all = stopListRepository.findAll(pageable);
        return PageStopListResponse.builder().stopLists(all.getContent()).currentPage(pageable.getPageNumber()+1)
                .size(all.getTotalPages()).build();

    }
}
