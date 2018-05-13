package com.dt.betting.db.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

@Component
public class IdGenerator {

	private final Map<Class<?>, AtomicLong> ID_BY_CLASS = new HashMap<>();

	public synchronized Long createId(Class<?> clazz) {
		ID_BY_CLASS.putIfAbsent(clazz, new AtomicLong(0));
		return ID_BY_CLASS.get(clazz).getAndIncrement();
	}
}
