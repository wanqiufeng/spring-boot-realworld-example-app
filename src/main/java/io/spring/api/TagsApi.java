package io.spring.api;

import graphql.com.google.common.collect.Lists;
import io.micrometer.core.instrument.*;
import io.spring.application.TagsQueryService;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "tags")
public class TagsApi {
  private TagsQueryService tagsQueryService;
  private Counter counter;

  @Autowired
  public TagsApi(TagsQueryService tagsQueryService, MeterRegistry registry) {
      this.counter = registry.counter("test_metric","A","","B","","C","");
      this.tagsQueryService = tagsQueryService;
  }

  @GetMapping
  public ResponseEntity getTags() {
      Metrics.counter("test_metric", Lists.newArrayList(Tag.of("A","1"),Tag.of("B","1"),Tag.of("C","1"))).increment();
      Metrics.counter("test_metric", Lists.newArrayList(Tag.of("A","2"),Tag.of("B","3"),Tag.of("C","4"))).increment();

    return ResponseEntity.ok(
        new HashMap<String, Object>() {
          {
            put("tags", tagsQueryService.allTags());
          }
        });
  }
}
