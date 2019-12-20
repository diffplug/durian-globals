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

/** Mechanism for changing {@link Globals} for the purpose of testing. */
public class GlobalsDev {
	private GlobalsDev() {}

	/** Wipes all globals, and returns an AutoCloseable which will wipe them again when closed. */
	public static AutoCloseable wipe() {
		Globals.wipe();
		return new AutoCloseable() {
			@Override
			public void close() {
				Globals.wipe();
			}
		};
	}

	/** Installs the given value as the implementation of the given class, and errors loudly if something else has already been installed. */
	public static <T, Dev extends T> Dev install(Class<T> clazz, Dev value) {
		T actual = Globals.getOrSetTo(clazz, () -> value);
		if (actual == value) {
			return value;
		} else {
			throw new IllegalStateException("Unable to install " + value.getClass().getName() + " because " + actual.getClass().getName() + " was already installed.");
		}
	}
}
