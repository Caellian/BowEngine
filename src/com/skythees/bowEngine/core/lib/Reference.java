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

package com.skythees.bowEngine.core.lib;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created on 03.03.15.
 */
public class Reference {
    @SuppressWarnings("UnusedDeclaration")
    public static final String ENGINE_NAME = "Bow Engine";
    @SuppressWarnings("UnusedDeclaration")
    public static final String ENGINE_ID = "bow_engine";
    @SuppressWarnings("UnusedDeclaration")
    public static final String ENGINE_VERSION = "0.0.1";
    @SuppressWarnings("UnusedDeclaration")
    public static final String AUTHOR = "Skythees";

    @SuppressWarnings("SameParameterValue")
    public static URI getURIPath(String path) {
//        return Reference.class.getClassLoader().getResource(path);
        try {
            return new URI(path);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
