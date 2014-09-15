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

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class DisplayHelper
{
    private static boolean init = false;
    
    private static int windowWidth;
    
    private static int windowHeight;
    
    public static void initGL(String name, int windowWidth, int windowHeight) throws LWJGLException
    {
        if (init)
        {
            return;
        }
        
        setWindowWidth(windowWidth);
        setWindowHeight(windowHeight);
        
        Display.setTitle(name);
        Display.setDisplayMode(new DisplayMode(windowWidth, windowHeight));
        // Display.setResizable(true);
        Display.create();
        
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glViewport(0, 0, windowWidth, windowHeight);
        // GLU.gluPerspective(90, windowWidth/windowHeight, 2.0f, 2.0f);
        GL11.glOrtho(0, windowWidth, windowHeight, 0, 2.0d, -2.0d);
        
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
    }
    
    public static void destroyGL()
    {
        Display.destroy();
    }
    
    public static void onResize()
    {
        // TODO
    }
    
    public static int getWindowWidth()
    {
        return windowWidth;
    }
    
    public static void setWindowWidth(int windowWidth)
    {
        DisplayHelper.windowWidth = windowWidth;
    }
    
    public static int getWindowHeight()
    {
        return windowHeight;
    }
    
    public static void setWindowHeight(int windowHeight)
    {
        DisplayHelper.windowHeight = windowHeight;
    }
}
