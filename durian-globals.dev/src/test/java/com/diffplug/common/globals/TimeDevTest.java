/*
 * Copyright 2020 DiffPlug
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.diffplug.common.globals;


import org.assertj.core.api.Assertions;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TimeDevTest {
	@Test
	public void _01_realtime() {
		Assertions.assertThat(Math.abs(Time.now() - System.currentTimeMillis())).isLessThanOrEqualTo(1);
	}

	@Test
	public void _02_realtime() throws Exception {
		try (AutoCloseable wipeGlobals = GlobalsDev.wipe()) {
			TimeDev devTime = TimeDev.install();
			devTime.set(123);
			Assertions.assertThat(Time.now()).isEqualTo(123);
			devTime.set(-456);
			Assertions.assertThat(Time.now()).isEqualTo(-456);

		}
	}

	@Test
	public void _03_realtime_again() {
		Assertions.assertThat(Math.abs(Time.now() - System.currentTimeMillis())).isLessThanOrEqualTo(1);
	}

	@Test
	public void _04_cant_install_after_init() {
		Assertions.assertThatThrownBy(TimeDev::install)
				.isExactlyInstanceOf(IllegalStateException.class)
				.hasMessage("Unable to install com.diffplug.common.globals.TimeDev because com.diffplug.common.globals.Time$Prod was already installed.");
	}
}
