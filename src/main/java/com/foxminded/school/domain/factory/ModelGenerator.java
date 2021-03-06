package com.foxminded.school.domain.factory;

public interface ModelFactory<E> {
    
     E generate(int counter);
}
