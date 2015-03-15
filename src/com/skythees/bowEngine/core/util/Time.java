/*
 * Software developed by Skythees
 * Copyright (C) 2015 Skythees
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.skythees.bowEngine.core.util;

public class Time {
    @SuppressWarnings("UnusedDeclaration")
    public static final long SECOND = 1000000000L;

    private static double delta;

    @SuppressWarnings("UnusedDeclaration")
    public static long getTime() {
        return System.nanoTime();
    }

    public static double getDelta() {
        return delta;
    }

    @SuppressWarnings("UnusedDeclaration")
    public static void setDelta(double delta) {
        Time.delta = delta;
    }
}
