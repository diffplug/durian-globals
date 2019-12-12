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


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;

/**
 * Testing implementation of {@link com.diffplug.common.globals.Time} for use with {@link
 * com.diffplug.common.globals.GlobalsHarness}.
 */
public class DevTime extends Time {
	private volatile long now;

	@Override
	protected synchronized long getNow() {
		return now;
	}

	/** Creates and installs a DevTime instance. */
	public static DevTime install() {
		return GlobalsHarness.install(Time.class, new DevTime());
	}

	private DevTime() {}

	/** Sets {@link com.diffplug.common.globals.Time#now()} to the given value. */
	public synchronized void set(long to) {
		now = to;
	}

	/** Sets {@link com.diffplug.common.globals.Time#now()} to the given value. */
	public synchronized void set(ZonedDateTime zoneDateTime) {
		set(zoneDateTime.toEpochSecond() * 1000L + zoneDateTime.getNano() / 1000);
	}

	/** Sets {@link com.diffplug.common.globals.Time#now()} to the given value in the UTC timezone. */
	public synchronized void setUTC(LocalDateTime localDate) {
		set(localDate.atZone(ZoneOffset.UTC));
	}

	/**
	 * Sets {@link com.diffplug.common.globals.Time#now()} to the start of the day of the given date
	 * in the UTC timezone.
	 */
	public synchronized void setUTC(LocalDate localDate) {
		setUTC(localDate.atStartOfDay());
	}

	/** Adds the given amount of time to now. */
	public synchronized void add(TimeUnit unit, long delta) {
		now += unit.toMillis(delta);
	}
}
