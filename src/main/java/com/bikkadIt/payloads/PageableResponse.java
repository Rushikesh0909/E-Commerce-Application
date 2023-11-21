package com.bikkadIt.payloads;

import lombok.*;
import org.springframework.http.ResponseEntity;

import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageableResponse<T> {

  private List<T> content;

  private int pageNumber;

  private int pageSize;

  private long totalElement;

  private int totalPages;

  private boolean lastPage;
}
