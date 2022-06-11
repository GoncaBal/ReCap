package com.kodlamaio.rentACar.core.utilities.mapping;

import org.modelmapper.ModelMapper;

public interface ModelMapperService {
ModelMapper forResponse(); // Burada belirli kolonlarla çalışabileceğimiz için kullanılır.
ModelMapper forRequest(); //her şey mapplenmek zorunda. Buraada tablodaki verileri gireceğimiz için hepsi gelmeli
}
