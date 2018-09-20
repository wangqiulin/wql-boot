package com.wql.boot.wqlboot.common.util.number;

import java.math.BigDecimal;

@FunctionalInterface
public interface ToBigDecimalFunction<T> {
	
	BigDecimal applyAsBigDecimal(T value);
	
}
