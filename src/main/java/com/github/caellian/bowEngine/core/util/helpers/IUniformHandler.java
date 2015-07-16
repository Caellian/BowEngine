/*
 * BowEngine, modular and easy to use game engine
 * Copyright (C) 2015 Caellian
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

package com.github.caellian.bowEngine.core.util.helpers;

import com.github.caellian.bowEngine.core.Transform;
import com.github.caellian.bowEngine.render.Material;
import com.github.caellian.bowEngine.render.Shader;

/**
 * This class allows users to manage uniforms unhandled by Bow Engine without use of ASM, patching the engine files or
 * modifying source code.
 *
 * @author Caellian
 */
public interface IUniformHandler
{
	boolean updateUniformStruct(Transform transform, Material material, Shader shader, String uniformName, String uniformType);

	boolean updateUndefinedUniformStruct(Transform transform, Material material, Shader shader, String uniformName, String uniformType);
}
