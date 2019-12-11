/*
 * Copyright 2019 DiffPlug
 *
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
package com.diffplug.common.globals;


import com.diffplug.common.collect.ImmutableClassToInstanceMap;
import java.util.function.Supplier;

/** Mechanism for managing global variables. */
public class Globals {
	/** Static-methods only. */
	private Globals() {}

	/**
	 * Map which contains the globals.
	 */
	private static volatile ImmutableClassToInstanceMap<Object> map = ImmutableClassToInstanceMap.of();

	/** Gets the global value or calls the given function to set it if it doesn't already exist. */
	public static <T> T getOrSetTo(Class<T> clazz, Supplier<? extends T> defaultValueSetter) {
		T value = map.getInstance(clazz);
		if (value == null) {
			synchronized (Globals.class) {
				value = map.getInstance(clazz);
				if (value == null) {
					value = defaultValueSetter.get();
					ImmutableClassToInstanceMap.Builder<Object> builder = ImmutableClassToInstanceMap.builder();
					builder.putAll(map);
					builder.put(clazz, value);
					map = builder.build();
				}
			}
		}
		return value;
	}

	/** Wipes the globals. */
	static void wipe() {
		synchronized (Globals.class) {
			map = ImmutableClassToInstanceMap.of();
		}
	}
}
