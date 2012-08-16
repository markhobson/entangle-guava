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

import org.hobsoft.entangle.Converter;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Factory for adapting Guava to converters.
 * 
 * @author Mark Hobson
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
			public T convert(S source)
			{
				return toFunction.apply(source);
			}
			
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
