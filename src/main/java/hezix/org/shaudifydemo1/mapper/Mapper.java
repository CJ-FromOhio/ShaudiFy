package hezix.org.shaudifydemo1.mapper;

import org.springframework.stereotype.Component;

@Component
public interface Mapper<F,T> {
    T toEntity(F f);
    F toDto(T t);
}
