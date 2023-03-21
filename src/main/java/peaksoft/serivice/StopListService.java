package peaksoft.serivice;

import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.request.StopListUpdateRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.StopListResponse;
import peaksoft.entity.StopList;

import java.util.Set;

public interface StopListService {
    SimpleResponse saveStopList(Long id, StopListRequest stopListRequest);
    StopListResponse getById(Long id);
    Set<StopListResponse>getAllStopLists();
    SimpleResponse update(Long id, StopListUpdateRequest stopListRequest);
    SimpleResponse delete(Long id);
}
