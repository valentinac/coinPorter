package com.qidiancamp.utils.retries;

public interface IPredicate<T> {
  boolean test(T t);
}
