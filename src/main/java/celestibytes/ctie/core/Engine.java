/*
 * Copyright (C) 2014 Celestibytes
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package celestibytes.ctie.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Engine
{
    public static final String NAME = "CTIEngine";
    public static final int DEFAULT_MAX_FPS = 60;
    public static Logger out = LogManager.getLogger(NAME);
}
