package com.bobbyaray.cache;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by robertray on 6/29/17.
 */
public interface StockRepository extends CrudRepository<StockEntry, String> {
}
