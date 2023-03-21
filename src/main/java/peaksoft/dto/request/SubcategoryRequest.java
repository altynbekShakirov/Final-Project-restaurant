package peaksoft.dto.request;

public record SubcategoryRequest(

        String name,
        CategoryRequest categoryRequest
) {
}
