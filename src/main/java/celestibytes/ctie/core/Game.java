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

package celestibytes.ctie.core;

import celestibytes.ctie.util.GLData;
import celestibytes.ctie.util.InitializationHelper;

import okkapel.ogljguisystem.GuiManager;
import okkapel.ogljguisystem.util.MouseHelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.util.Random;

/**
 * An abstract {@link Object} that should be supertype for all games that use
 * CTIEngine.
 * 
 * @author PizzAna
 *
 */
public abstract class Game
{
    /**
     * The {@link GuiManager} of the game.
     */
    public static GuiManager guiManager = null;
    
    /**
     * The delta value.
     */
    private static float delta;
    
    /**
     * The time of the last cycle.
     */
    private static long lastCycle = System.currentTimeMillis();
    
    /**
     * If this value is different from {@code 100}, the game will exit.
     */
    private static int exitCode = 100;
    
    /**
     * The FPS limit of the game.
     */
    private static int fpsLimit = 60;
    
    /**
     * The window width.
     */
    private static int windowWidth;
    
    /**
     * The window height.
     */
    private static int windowHeight;
    
    /**
     * The main output logger of the game.
     */
    public Logger out;
    
    /**
     * The instance of {@link Random} for the game to use.
     */
    protected final Random random = new Random(System.currentTimeMillis());
    
    /**
     * The name of the game.
     */
    public String name;
    
    /**
     * The time of the last FPS check.
     */
    private long fpsCheckLast = System.currentTimeMillis();
    
    /**
     * The count of frames of the game.
     */
    private int frameCount = 0;
    
    /**
     * Starts the game.
     * 
     * @param name
     *            the name.
     * @param maxFps
     *            the maximum frames per second.
     * @param windowWidth
     *            the window width.
     * @param windowHeight
     *            the window height.
     */
    public void start(String name, int maxFps, int windowWidth, int windowHeight)
    {
        this.name = name;
        
        Game.fpsLimit = maxFps;
        
        Game.windowWidth = windowWidth;
        Game.windowHeight = windowHeight;
        
        if (out == null)
        {
            out = LogManager.getLogger(name);
        }
        
        baseInit();
        
        preLoad();
        init();
        
        while (keepRunning())
        {
            if (Display.wasResized())
            {
                InitializationHelper.onResize();
            }
            
            calculateDelta();
            
            MouseHelper.update();
            guiManager.mouseUpdate();
            
            gameLoop();
            
            int errorCode = GL11.glGetError();
            
            if (errorCode != GL11.GL_NO_ERROR)
            {
                out.error(GLU.gluErrorString(errorCode));
            }
            
            frameCount++;
            
            if ((System.currentTimeMillis() - fpsCheckLast) > 1000)
            {
                out.debug("FPS: " + frameCount + ", Logic time: " + (System.currentTimeMillis() - lastCycle) + "ms");
                frameCount = 0;
                fpsCheckLast = System.currentTimeMillis();
            }
            
            Display.update();
            Display.sync(fpsLimit);
        }
        
        GLData.unloadAll();
        InitializationHelper.destroyGL();
    }
    
    /**
     * The games base initialization.
     */
    private void baseInit()
    {
        try
        {
            InitializationHelper.initGL(name, windowWidth, windowHeight);
            GLData.loadTextures();
            GuiManager.init(GLData.textureGuiDecor, windowWidth, windowHeight);
            guiManager = new GuiManager();
        }
        catch (LWJGLException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Calculates the delta.
     */
    private void calculateDelta()
    {
        delta = (System.currentTimeMillis() - lastCycle) / 1000f;
        lastCycle = System.currentTimeMillis();
    }
    
    /**
     * Stops the game.
     */
    protected void stop()
    {
        System.exit(exitCode);
    }
    
    /**
     * Schedules stop.
     * 
     * @param exitCode
     *            the system exit code.
     */
    public static void scheduleStop(int exitCode)
    {
        Game.exitCode = exitCode != 100 ? exitCode : -1;
    }
    
    /**
     * Tells if the game should keep running.
     * 
     * @return {@code false} if the game has requested stopping, otherwise
     *         {@code true}.
     */
    protected boolean keepRunning()
    {
        return exitCode == 100 && !Display.isCloseRequested();
    }
    
    /**
     * Gives the delta of the {@link Game}.
     * 
     * @return the delta.
     */
    public static float getDelta()
    {
        return delta;
    }
    
    /**
     * Gives the fpsLimit of the {@link Game}.
     * 
     * @return the fpsLimit.
     */
    public static int getFpsLimit()
    {
        return fpsLimit;
    }
    
    /**
     * @param delta
     *            the delta to set
     */
    public static void setDelta(float delta)
    {
        Game.delta = delta;
    }
    
    /**
     * @param fpsLimit
     *            the fpsLimit to set
     */
    public static void setFpsLimit(int fpsLimit)
    {
        Game.fpsLimit = fpsLimit;
    }
    
    /**
     * Gives the windowWidth of the {@link Game}.
     * 
     * @return the windowWidth.
     */
    public static int getWindowWidth()
    {
        return windowWidth;
    }
    
    /**
     * Gives the windowHeight of the {@link Game}.
     * 
     * @return the windowHeight.
     */
    public static int getWindowHeight()
    {
        return windowHeight;
    }
    
    /**
     * @param windowWidth
     *            the windowWidth to set
     */
    public static void setWindowWidth(int windowWidth)
    {
        Game.windowWidth = windowWidth;
    }
    
    /**
     * @param windowHeight
     *            the windowHeight to set
     */
    public static void setWindowHeight(int windowHeight)
    {
        Game.windowHeight = windowHeight;
    }
    
    /**
     * Loads the game textures, configurations and etc.
     */
    protected abstract void preLoad();
    
    /**
     * The main initialization method.
     */
    public abstract void init();
    
    /**
     * Called before the game exits. Use this to delete textures, shaders, VBOs,
     * etc.
     */
    public abstract void deInit();
    
    /**
     * Called every frame. This is where all game logic and rendering is
     * handled.
     */
    protected abstract void gameLoop();
}
