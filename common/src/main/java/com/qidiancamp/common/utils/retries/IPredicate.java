package com.qidiancamp.common.utils.retries;

public interface IPredicate<T> {
  boolean test(T t);
}
