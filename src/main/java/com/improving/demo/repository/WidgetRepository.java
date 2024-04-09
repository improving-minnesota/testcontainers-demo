package com.improving.demo.repository;

import com.improving.demo.persistent.Widget;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WidgetRepository extends CrudRepository<Widget, Long> {
}
