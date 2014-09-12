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

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

/**
 * An {@link Object} that loads textures.
 * 
 * @author PizzAna
 *
 */
public class TextureLoader
{
    /**
     * Loads a texture.
     * 
     * @param filename
     *            the name of the texture file.
     * @return the texture id.
     */
    public static int loadTexture(String filename)
    {
        try
        {
            BufferedImage img = ImageIO.read(new File(filename));
            
            // Array for image pixel data
            int[] pixels = new int[img.getWidth() * img.getHeight() * 4];
            
            // Write pixel data to the array
            img.getRGB(0, 0, img.getWidth(), img.getHeight(), pixels, 0, img.getWidth());
            
            // Byte buffer for pixel data
            ByteBuffer pixs = BufferUtils.createByteBuffer(img.getWidth() * img.getHeight() * 4);
            
            // Put pixel data into the buffer
            for (int y = 0; y < img.getHeight(); y++)
            {
                for (int x = 0; x < img.getWidth(); x++)
                {
                    int pixl = pixels[y * img.getWidth() + x];
                    pixs.put((byte) ((pixl >> 16) & 255)); // Red
                    pixs.put((byte) ((pixl >> 8) & 255)); // Green
                    pixs.put((byte) ((pixl) & 255)); // Blue
                    pixs.put((byte) ((pixl >> 24) & 255)); // Alpha
                }
            }
            
            // Required
            pixs.flip();
            int texId = GL11.glGenTextures();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);
            System.out.println(texId);
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, img.getWidth(), img.getHeight(), 0, GL11.GL_RGBA,
                    GL11.GL_UNSIGNED_BYTE, pixs);
            
            // Apparently I forgot these
            // GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
            // GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            // GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
            // GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
            
            return texId;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        System.err.println("Texture loading failed!");
        
        return -1;
    }
}
