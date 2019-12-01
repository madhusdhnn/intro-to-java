package com.learning.projects.guessmovie.data;

@FunctionalInterface
public interface ThrowableConsumer<T> {

    void accept(T t) throws Exception;

}
