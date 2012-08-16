/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/guava/tags/1.0.0-beta-1/src/main/java/uk/co/iizuka/common/binding/guava/GuavaConverters.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package uk.co.iizuka.common.binding.guava;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;

import uk.co.iizuka.common.binding.Converter;

/**
 * Factory for adapting Guava to converters.
 * 
 * @author Mark Hobson
 * @version $Id: GuavaConverters.java 97383 2011-12-28 20:22:56Z mark@IIZUKA.CO.UK $
 */
public final class GuavaConverters
{
	// constructors -----------------------------------------------------------
	
	private GuavaConverters()
	{
		throw new AssertionError();
	}
	
	// public methods ---------------------------------------------------------
	
	public static <S> Converter<S, Boolean> forPredicate(Predicate<? super S> predicate)
	{
		return forFunction(Functions.forPredicate(predicate));
	}
	
	public static <T> Converter<Object, T> forSupplier(Supplier<? extends T> supplier)
	{
		return forFunction(Functions.forSupplier(supplier));
	}
	
	public static <S, T> Converter<S, T> forFunction(Function<? super S, ? extends T> function)
	{
		return forFunction(function, null);
	}
	
	public static <S, T> Converter<S, T> forFunction(final Function<? super S, ? extends T> toFunction,
		final Function<? super T, ? extends S> fromFunction)
	{
		checkNotNull(toFunction, "toFunction cannot be null");
		
		return new Converter<S, T>()
		{
			@Override
			public T convert(S source)
			{
				return toFunction.apply(source);
			}
			
			@Override
			public S unconvert(T target)
			{
				if (fromFunction == null)
				{
					throw new UnsupportedOperationException();
				}
				
				return fromFunction.apply(target);
			}
		};
	}
}
