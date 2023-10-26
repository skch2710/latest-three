package com.springboot.latestthree.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColumnFilter {

  private Column column;
  private String operator;
  private String value;

}
