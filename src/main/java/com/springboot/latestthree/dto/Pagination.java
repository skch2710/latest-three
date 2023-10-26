package com.springboot.latestthree.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pagination {
	private int pageNumber;
	private int pageSize;
	private String sortBy;
	private String sortOrder;
	private ColumnFilter[] columnFilters;
	private boolean export;
}