package io.github.tiagoadmstz.interfaces;

@FunctionalInterface
public interface ExceptionFunction<T, R> {

    R apply(T t) throws Exception;

}
