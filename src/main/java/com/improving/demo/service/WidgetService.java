package com.improving.demo.service;

import com.improving.demo.persistent.Widget;
import com.improving.demo.repository.WidgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WidgetService {
    private final WidgetRepository repository;

    public Widget getWidget(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Widget %s is not found", id)));
    }

    public List<Widget> getWidgets() {
        var widgets = new ArrayList<Widget>();
        repository.findAll().forEach(widgets::add);
        return widgets;
    }

    public Widget createWidget(Widget request) {
        var widget = Widget.builder().build();
        widget.setValue(request.getValue());
        return repository.save(widget);
    }

    public Widget updateWidget(Widget request) {
        var widget = getWidget(request.getId());
        widget.setValue(request.getValue());
        return repository.save(widget);
    }

    public void deleteWidget(Long id) {
        repository.deleteById(id);
    }
}
