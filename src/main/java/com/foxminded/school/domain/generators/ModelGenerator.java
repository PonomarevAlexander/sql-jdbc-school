package com.foxminded.school.domain.generators;

public interface ModelGenerator<E> {
    
     E generate(int counter);
}
