package com.improving.demo.controller;

import com.improving.demo.persistent.Widget;
import com.improving.demo.service.WidgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class JdbcController {
    private final WidgetService service;

    @GetMapping(value = "/widgets", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Widget>> getWidgets() {
        return ResponseEntity.ok(service.getWidgets());
    }

    @GetMapping(value = "/widget/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Widget> getWidget(@PathVariable Long id) {
        return ResponseEntity.ok(service.getWidget(id));
    }

    @DeleteMapping(value = "/widget/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteWidget(@PathVariable Long id) {
        service.deleteWidget(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/widget", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Widget> createWidget(@RequestBody Widget widget) {
        return ResponseEntity.ok(service.createWidget(widget));
    }

    @PutMapping(value = "/widget", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Widget> updateWidget(@RequestBody Widget widget) {
        return ResponseEntity.ok(service.updateWidget(widget));
    }
}
