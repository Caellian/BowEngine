/*
 * BowEngine, modular and easy to use game engine
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.skythees.bowEngine.core.exceptions;

/**
 * Created on 06.03.15.
 */
@SuppressWarnings("WeakerAccess")
public class BowEngineException extends Exception
{
	@SuppressWarnings("unused")
	public BowEngineException()
	{
		super();
	}

	@SuppressWarnings("unused")
	public BowEngineException(String message)
	{
		super(message);
	}

	@SuppressWarnings("unused")
	public BowEngineException(String message, Throwable cause)
	{
		super(message, cause);
	}

	@SuppressWarnings("unused")
	public BowEngineException(Throwable cause)
	{
		super(cause);
	}

	@SuppressWarnings("unused")
	protected BowEngineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
