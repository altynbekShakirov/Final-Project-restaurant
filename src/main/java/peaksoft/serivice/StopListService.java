package peaksoft.serivice;

import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.StopListResponse;
import peaksoft.dto.response.pageResponse.PageStopListResponse;

import java.util.Set;

public interface StopListService {
    SimpleResponse saveStopList( StopListRequest stopListRequest);

    StopListResponse getById(Long id);

    Set<StopListResponse> getAllStopLists();

    SimpleResponse update(Long id, StopListRequest stopListRequest);

    SimpleResponse delete(Long id);
    PageStopListResponse getStopListPage(int currentPage, int size);
}
