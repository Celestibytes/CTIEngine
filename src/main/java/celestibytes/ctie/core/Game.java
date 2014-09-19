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

import celestibytes.ctie.input.BasicGameInput;
import celestibytes.ctie.util.GLData;
import celestibytes.ctie.util.DisplayHelper;
import okkapel.kkplglutil.util.KeyBindHandler;
import okkapel.ogljguisystem.GuiManager;
import okkapel.ogljguisystem.util.MouseHelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.util.Random;

public abstract class Game
{
    public GuiManager guiManager = null;
    public Logger out;
    public final String name;
    
    protected final Random random = new Random(System.currentTimeMillis());
    
    private long lastCycle = System.currentTimeMillis();
    private long fpsCheckLast = System.currentTimeMillis();
    private int exitCode = 100;
    private int fpsLimit = 60;
    private int frameCount = 0;
    private float delta;
    private int windowWidth;
    private int windowHeight;
    
    // Temporary!
    private int guiDecorationTexture = -1;
    
    public Game(String name, int windowWidth, int windowHeight)
    {
        this(name, Engine.DEFAULT_MAX_FPS, windowWidth, windowHeight);
    }
    
    public Game(String name, int maxFps, int windowWidth, int windowHeight)
    {
        this.name = name;
        this.fpsLimit = maxFps;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }
    
    public void start()
    {
        if (out == null)
        {
            out = LogManager.getLogger(name);
        }
        
        baseInit();
        
        preLoad();
        // Temporary, need to wait for the guiDecor texture to be loaded in preLoad()
        GuiManager.init(this.guiDecorationTexture, windowWidth, windowHeight);
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
            
            BasicGameInput.clearInput();
            KeyBindHandler.updateKBs();
            
            gameLoop();
            
            int errorCode = GL11.glGetError();
            
            if (errorCode != GL11.GL_NO_ERROR)
            {
                out.error(GLU.gluErrorString(errorCode));
            }
            
            frameCount++;
            
            if ((System.currentTimeMillis() - fpsCheckLast) > 1000)
            {
//                out.debug("FPS: " + frameCount + ", Logic time: " + (System.currentTimeMillis() - lastCycle) + "ms");
                System.out.println("FPS: " + frameCount + ", Logic time: " + (System.currentTimeMillis() - lastCycle) + "ms");
                frameCount = 0;
                fpsCheckLast = System.currentTimeMillis();
            }
            
            Display.update();
            Display.sync(fpsLimit);
        }
        
        GLData.unloadAll();
        KeyBindHandler.deleteKeyBinds();
        DisplayHelper.destroyGL();
    }
    
    private void baseInit()
    {
        try
        {
            DisplayHelper.initGL(name, windowWidth, windowHeight);
            GLData.loadTextures();
            guiManager = new GuiManager();
        }
        catch (LWJGLException e)
        {
            e.printStackTrace();
        }
    }
    
    private void calculateDelta()
    {
        delta = (System.currentTimeMillis() - lastCycle) / 1000f;
        lastCycle = System.currentTimeMillis();
    }
    
    protected void stop()
    {
        System.exit(exitCode);
    }
    
    public void scheduleStop(int exitCode)
    {
        this.exitCode = exitCode != 100 ? exitCode : -1;
    }
    
    protected boolean keepRunning()
    {
        return exitCode == 100 && !Display.isCloseRequested();
    }
    
    public float getDelta()
    {
        return delta;
    }
    
    public void setDelta(float delta)
    {
        this.delta = delta;
    }
    
    public int getFpsLimit()
    {
        return fpsLimit;
    }
    
    public void setFpsLimit(int fpsLimit)
    {
        this.fpsLimit = fpsLimit;
    }
    
    public int getWindowWidth()
    {
        return windowWidth;
    }
    
    public void setWindowWidth(int windowWidth)
    {
        this.windowWidth = windowWidth;
    }
    
    public int getWindowHeight()
    {
        return windowHeight;
    }
    
    public void setWindowHeight(int windowHeight)
    {
        this.windowHeight = windowHeight;
    }
    
    public int getGuiDecorTex() {
    	return this.guiDecorationTexture;
    }
    
    public void setGuiDecorTex(int tex) {
    	if(this.guiDecorationTexture == -1) {
    		this.guiDecorationTexture = tex;
    	}
    }
    
    protected abstract void preLoad();
    
    protected abstract void init();
    
    protected abstract void deInit();
    
    protected abstract void gameLoop();
}
