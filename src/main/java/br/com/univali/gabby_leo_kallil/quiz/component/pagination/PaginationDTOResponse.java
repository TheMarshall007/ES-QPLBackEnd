package br.com.univali.gabby_leo_kallil.quiz.component.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationDTOResponse<T> {

    private List<T> content;
    private Integer currentPage;
    private Integer totalItems;
    private Integer fixedTotalItems = 15;
    private Integer totalPages;

    public PaginationDTOResponse(List<T> content, Integer currentPage, Integer totalItems, Integer totalPages) {
        this.content = content;
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }

}
