/*
 * Copyright (C) 2014 Celestibytes
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 */

package celestibytes.ctie.util;

import org.lwjgl.opengl.GL11;

public class GLData
{
    public static int textureGuiDecor = -1;
    
    public static void loadTextures()
    {
        textureGuiDecor = TextureLoader.loadTexture("src/main/resources/textures/gui/gui_decoration.png");
    }
    
    public static void unloadAll()
    {
        if (textureGuiDecor != -1)
        {
            GL11.glDeleteTextures(textureGuiDecor);
        }
    }
}
