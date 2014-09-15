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
import celestibytes.ctie.util.DisplayHelper;

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
    public GuiManager guiManager = null;
    
    /**
     * The main output logger of the game.
     */
    public Logger out;
    
    /**
     * The name of the game.
     */
    public String name;
    
    /**
     * The instance of {@link Random} for the game to use.
     */
    protected final Random random = new Random(System.currentTimeMillis());
    
    /**
     * The delta value.
     */
    private float delta;
    
    /**
     * The time of the last cycle.
     */
    private long lastCycle = System.currentTimeMillis();
    
    /**
     * If this value is different from {@code 100}, the game will exit.
     */
    private int exitCode = 100;
    
    /**
     * The FPS limit of the game.
     */
    private int fpsLimit = 60;
    
    /**
     * The window width.
     */
    private int windowWidth;
    
    /**
     * The window height.
     */
    private int windowHeight;
    
    /**
     * The time of the last FPS check.
     */
    private long fpsCheckLast = System.currentTimeMillis();
    
    /**
     * The count of frames of the game.
     */
    private int frameCount = 0;
    
    /**
     * 
     * Constructs a new {@link Game}.
     * 
     * @param name
     *            the name.
     * @param windowWidth
     *            the initial window width.
     * @param windowHeight
     *            the initial window height.
     */
    public Game(String name, int windowWidth, int windowHeight)
    {
        this(name, Engine.DEFAULT_MAX_FPS, windowWidth, windowHeight);
    }
    
    /**
     * 
     * Constructs a new {@link Game}.
     * 
     * @param name
     *            the name.
     * @param maxFps
     *            the maximum frames per second.
     * @param windowWidth
     *            the initial window width.
     * @param windowHeight
     *            the initial window height.
     */
    public Game(String name, int maxFps, int windowWidth, int windowHeight)
    {
        this.name = name;
        this.fpsLimit = maxFps;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }
    
    /**
     * Starts the game.
     */
    public void start()
    {
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
                DisplayHelper.onResize();
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
        DisplayHelper.destroyGL();
    }
    
    /**
     * The games base initialization.
     */
    private void baseInit()
    {
        try
        {
            DisplayHelper.initGL(name, windowWidth, windowHeight);
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
    public void scheduleStop(int exitCode)
    {
        this.exitCode = exitCode != 100 ? exitCode : -1;
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
     * Gives the {@code delta}.
     * 
     * @return the {@code delta}.
     */
    public float getDelta()
    {
        return delta;
    }
    
    /**
     * Sets the {@code delta} to the given value.
     * 
     * @param delta
     *            the value to set
     */
    public void setDelta(float delta)
    {
        this.delta = delta;
    }
    
    /**
     * Gives the {@code fpsLimit}.
     * 
     * @return the {@code fpsLimit}.
     */
    public int getFpsLimit()
    {
        return fpsLimit;
    }
    
    /**
     * Sets the {@code fpsLimit} to the given value.
     * 
     * @param fpsLimit
     *            the value to set
     */
    public void setFpsLimit(int fpsLimit)
    {
        this.fpsLimit = fpsLimit;
    }
    
    /**
     * Gives the {@code windowWidth}.
     * 
     * @return the {@code windowWidth}.
     */
    public int getWindowWidth()
    {
        return windowWidth;
    }
    
    /**
     * Sets the {@code windowWidth} to the given value.
     * 
     * @param windowWidth
     *            the value to set
     */
    public void setWindowWidth(int windowWidth)
    {
        this.windowWidth = windowWidth;
    }
    
    /**
     * Gives the {@code windowHeight}.
     * 
     * @return the {@code windowHeight}.
     */
    public int getWindowHeight()
    {
        return windowHeight;
    }
    
    /**
     * Sets the {@code windowHeight} to the given value.
     * 
     * @param windowHeight
     *            the value to set
     */
    public void setWindowHeight(int windowHeight)
    {
        this.windowHeight = windowHeight;
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
