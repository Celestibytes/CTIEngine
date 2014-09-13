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

import celestibytes.ctie.core.Game;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/**
 * Handles and initializes the game {@link Display}.
 * <p/>
 * When a new {@link Display} is created,
 * {@link DisplayHelper#initGL(String, int, int)} should be called.
 * <p/>
 * When the {@link Display} is closed, {@link DisplayHelper#destroyGL()} should
 * be called.
 * <p/>
 * When the {@link Display} is resized, {@link DisplayHelper#onResize()} should
 * be called.
 * 
 * @author PizzAna
 * @see Display
 * @see Game
 */
public class DisplayHelper
{
    /**
     * Tells if the initialization has already run.
     */
    private static boolean init = false;
    
    /**
     * The window width.
     */
    private static int windowWidth;
    
    /**
     * The window height.
     */
    private static int windowHeight;
    
    /**
     * Initializes the {@link Display}.
     * 
     * @param name
     *            the name.
     * @param windowWidth
     *            the window width.
     * @param windowHeight
     *            the window height.
     * @throws LWJGLException
     *             if the initialization fails.
     */
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
    
    /**
     * Destroys the {@link Display}.
     */
    public static void destroyGL()
    {
        Display.destroy();
    }
    
    /**
     * Called when the {@link Display} is resized.
     */
    public static void onResize()
    {
        // TODO
    }
    
    /**
     * Gives the {@code windowWidth}.
     * 
     * @return the {@code windowWidth}.
     */
    public static int getWindowWidth()
    {
        return windowWidth;
    }
    
    /**
     * Sets the {@code windowWidth} to the given value.
     * 
     * @param windowWidth
     *            the value to set
     */
    public static void setWindowWidth(int windowWidth)
    {
        DisplayHelper.windowWidth = windowWidth;
    }
    
    /**
     * Gives the {@code windowHeight}.
     * 
     * @return the {@code windowHeight}.
     */
    public static int getWindowHeight()
    {
        return windowHeight;
    }
    
    /**
     * Sets the {@code windowHeight} to the given value.
     * 
     * @param windowHeight
     *            the value to set
     */
    public static void setWindowHeight(int windowHeight)
    {
        DisplayHelper.windowHeight = windowHeight;
    }
}