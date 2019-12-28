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


import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;

/** Alias for {@link System#currentTimeMillis}, but can be manipulated within tests by {@link TimeDev}. */
public abstract class Time {
	// package-private to prevent others from implementing
	Time() {}

	protected abstract long getNow();

	/** Alias for {@link System#currentTimeMillis}, but can be manipulated within tests by {@link TimeDev}. */
	public static long now() {
		return Globals.getOrSetTo(Time.class, Prod::new).getNow();
	}

	/** Returns a {@link Clock} in the given timezone, which uses {@link Time#now} as its source of truth. */
	public static Clock clock(ZoneId zone) {
		return new ClockImpl(zone);
	}

	/** Returns UTC {@link Clock} which uses {@link Time#now} as its source of truth. */
	public static Clock clockUtc() {
		return clock(ZoneOffset.UTC);
	}

	private static class ClockImpl extends Clock {
		private final ZoneId zoneId;

		private ClockImpl(ZoneId zoneId) {
			this.zoneId = zoneId;
		}

		@Override
		public ZoneId getZone() {
			return zoneId;
		}

		@Override
		public Clock withZone(ZoneId zone) {
			return new ClockImpl(zone);
		}

		@Override
		public Instant instant() {
			return Instant.ofEpochMilli(now());
		}
	}

	private static class Prod extends Time {
		@Override
		protected long getNow() {
			return System.currentTimeMillis();
		}
	}
}
