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

package celestibytes.example.game.launch;

import celestibytes.ctie.core.Engine;

import celestibytes.example.game.ExampleGame;

public class Launch
{
    public static ExampleGame game = null;
    
    public static void main(String[] args)
    {
        game = new ExampleGame();
        Engine.out.info("Starting the Example Game");
        game.start();
    }
}
