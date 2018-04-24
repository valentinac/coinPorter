package com.qidiancamp.porterbatch;

import java.util.List;
import org.springframework.batch.item.ItemWriter;

public class SampleWriter implements ItemWriter<SampleItem> {
  @Override
  public void write(List<? extends SampleItem> items) throws Exception {
    for (SampleItem item : items) {
      System.out.println(item.getName());
    }
  }
}
