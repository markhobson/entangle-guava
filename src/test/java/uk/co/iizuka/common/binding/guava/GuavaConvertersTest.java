/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/guava/tags/1.0.0-beta-1/src/test/java/uk/co/iizuka/common/binding/guava/GuavaConvertersTest.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package uk.co.iizuka.common.binding.guava;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicates;
import com.google.common.base.Suppliers;

import org.junit.Test;

import uk.co.iizuka.common.binding.Converter;

/**
 * Tests {@code GuavaConverters}.
 * 
 * @author Mark Hobson
 * @version $Id: GuavaConvertersTest.java 97383 2011-12-28 20:22:56Z mark@IIZUKA.CO.UK $
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
