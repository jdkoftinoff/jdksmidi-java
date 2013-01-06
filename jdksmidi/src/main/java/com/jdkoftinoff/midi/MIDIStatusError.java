/*
 * MIDIStatusError.java
 *
 * Created on 12 September 2006, 17:35 By Jeff Koftinoff <jeffk@jdkoftinoff.com>
 *
 * https://github.com/jdkoftinoff/jdksmidi-java
 *
 *  libjdkmidijava Java Class Library for MIDI
 *
 *  Copyright (C) 2006  J.D. Koftinoff Software, Ltd.
 *  www.jdkoftinoff.com
 *  jeffk@jdkoftinoff.com
 *
 *  *** RELEASED UNDER THE GNU GENERAL PUBLIC LICENSE (GPL) September 11, 2006 ***
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.jdkoftinoff.midi;

/**
 *
 */

public class MIDIStatusError extends MIDIError
{
    /**
     * 
     */
    private static final long serialVersionUID = 8440247834999989083L;

    /** Creates a new instance of MIDI StatusError */
    public MIDIStatusError()
    {
        super();
    }
    
    public MIDIStatusError( String reason )
    {
        super( reason );
    }
}