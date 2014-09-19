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

import celestibytes.ctie.core.Engine;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

public class TextureLoader
{
    public static int loadTexture(String filename)
    {
    	String fullFilePath = null;
        try
        {
        	File imgFile = new File(filename);
        	fullFilePath = imgFile.getAbsolutePath();
        	
            // The image
            BufferedImage image = ImageIO.read(imgFile);
            
            // The image pixel data
            int[] pixels = new int[image.getWidth() * image.getHeight() * 4];
            
            // Write the pixel data into the array
            image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
            
            // Create a byte buffer for pixel data
            ByteBuffer pixelBuffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);
            
            // Put pixel data into the buffer
            for (int i = 0; i < image.getHeight(); i++)
            {
                for (int j = 0; j < image.getWidth(); j++)
                {
                    // Get the pixel from the array
                    int pixel = pixels[i * image.getWidth() + j];
                    
                    // Red
                    pixelBuffer.put((byte) ((pixel >> 16) & 255));
                    
                    // Green
                    pixelBuffer.put((byte) ((pixel >> 8) & 255));
                    
                    // Blue
                    pixelBuffer.put((byte) ((pixel) & 255));
                    
                    // Alpha
                    pixelBuffer.put((byte) ((pixel >> 24) & 255));
                }
            }
            
            pixelBuffer.flip();
            
            int id = GL11.glGenTextures();
            
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
            
            Engine.out.debug(id);
            
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, image.getWidth(), image.getHeight(), 0,
                    GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixelBuffer);
            
            // Apparently I forgot these
            // GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
            // GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            // GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
            // GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
            
            return id;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Engine.out.error("Failed to load texture from file: " + fullFilePath);
        }
        
        
        return -1;
    }
}
