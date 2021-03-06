/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hobsoft.entangle.guava;

import java.util.Collections;

import org.hobsoft.entangle.Converter;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicates;
import com.google.common.base.Suppliers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests {@code GuavaConverters}.
 * 
 * @author Mark Hobson
 * @see GuavaConverters
 */
public class GuavaConvertersTest
{
	// tests ------------------------------------------------------------------
	
	@Test
	public void forPredicateConvert()
	{
		Converter<Integer, Boolean> converter = GuavaConverters.forPredicate(Predicates.<Integer>alwaysTrue());
		
		assertTrue(converter.convert(1));
	}
	
	@Test
	public void forPredicateConvertWithSupertypePredicate()
	{
		Converter<Integer, Boolean> converter = GuavaConverters.forPredicate(Predicates.<Number>alwaysTrue());
		
		assertTrue(converter.convert(1));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void forPredicateUnconvert()
	{
		Converter<Integer, Boolean> converter = GuavaConverters.forPredicate(Predicates.<Integer>alwaysTrue());
		
		converter.unconvert(true);
	}
	
	@Test(expected = NullPointerException.class)
	public void forPredicateWithNull()
	{
		GuavaConverters.forPredicate(null);
	}
	
	@Test
	public void forSupplierConvert()
	{
		Converter<Object, Integer> converter = GuavaConverters.forSupplier(Suppliers.ofInstance(1));
		
		assertEquals((Integer) 1, converter.convert("x"));
	}
	
	@Test
	public void forSupplierConvertWithSupertypeSupplier()
	{
		Converter<Object, Number> converter = GuavaConverters.forSupplier(Suppliers.<Number>ofInstance(1));
		
		assertEquals(1, converter.convert("x"));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void forSupplierUnconvert()
	{
		Converter<Object, Integer> converter = GuavaConverters.forSupplier(Suppliers.ofInstance(1));
		
		converter.unconvert(1);
	}
	
	@Test(expected = NullPointerException.class)
	public void forSupplierWithNull()
	{
		GuavaConverters.forSupplier(null);
	}
	
	@Test
	public void forFunctionConvert()
	{
		Converter<String, Integer> converter = GuavaConverters.forFunction(function("a", 1));
		
		assertEquals((Integer) 1, converter.convert("a"));
	}
	
	@Test
	public void forFunctionConvertWithSupertypeFrom()
	{
		Converter<String, Integer> converter = GuavaConverters.forFunction(function((Object) "a", 1));
		
		assertEquals((Integer) 1, converter.convert("a"));
	}
	
	@Test
	public void forFunctionConvertWithSubtypeTo()
	{
		Converter<String, Number> converter = GuavaConverters.<String, Number>forFunction(function("a", 1));
		
		assertEquals(1, converter.convert("a"));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void forFunctionUnconvert()
	{
		Converter<String, Integer> converter = GuavaConverters.forFunction(function("a", 1));
		
		converter.unconvert(1);
	}
	
	@Test
	public void forFunctionWithInverseFunctionConvert()
	{
		Converter<String, Integer> converter = GuavaConverters.forFunction(function("a", 1), function(1, "a"));
		
		assertEquals((Integer) 1, converter.convert("a"));
	}
	
	@Test
	public void forFunctionWithInverseFunctionAndSupertypeFromConvert()
	{
		Converter<String, Integer> converter = GuavaConverters.forFunction(function((Object) "a", 1), function(1, "a"));
		
		assertEquals((Integer) 1, converter.convert("a"));
	}
	
	@Test
	public void forFunctionWithInverseFunctionAndSubtypeToConvert()
	{
		Converter<String, Number> converter = GuavaConverters.<String, Number>forFunction(function("a", 1),
			function((Number) 1, "a"));
		
		assertEquals(1, converter.convert("a"));
	}
	
	@Test
	public void forFunctionWithInverseFunctionAndInverseSupertypeFromConvert()
	{
		Converter<String, Integer> converter = GuavaConverters.forFunction(function("a", 1), function((Number) 1, "a"));
		
		assertEquals((Integer) 1, converter.convert("a"));
	}
	
	@Test
	public void forFunctionWithInverseFunctionAndInverseSubtypeToConvert()
	{
		Converter<Object, Integer> converter = GuavaConverters.<Object, Integer>forFunction(function((Object) "a", 1),
			function(1, "a"));
		
		assertEquals((Integer) 1, converter.convert("a"));
	}
	
	@Test
	public void forFunctionWithInverseFunctionUnconvert()
	{
		Converter<String, Integer> converter = GuavaConverters.forFunction(function("a", 1), function(1, "a"));
		
		assertEquals("a", converter.unconvert(1));
	}
	
	@Test(expected = NullPointerException.class)
	public void forFunctionWithNull()
	{
		GuavaConverters.forFunction(null);
	}
	
	// private methods --------------------------------------------------------
	
	private static <F, T> Function<F, T> function(F from, T to)
	{
		return Functions.forMap(Collections.singletonMap(from, to));
	}
}
