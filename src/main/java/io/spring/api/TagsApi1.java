package io.spring.api;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.spring.application.TagsQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping(path = "tags1")
public class TagsApi1 {
  private TagsQueryService tagsQueryService;
  private Counter counter;

  @Autowired
  public TagsApi1(TagsQueryService tagsQueryService, MeterRegistry registry) {
      this.tagsQueryService = tagsQueryService;
  }

  @GetMapping
  public ResponseEntity getTags() {
      Metrics.counter("test.metric","a","1").increment();
      Metrics.counter("test.metric","c","1").increment();
    return ResponseEntity.ok(
        new HashMap<String, Object>() {
          {
            put("tags", tagsQueryService.allTags());
          }
        });
  }
}
