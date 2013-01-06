/*
 * MIDISystemExclusive.java
 *
 * Created on 11 September 2006, 13:58 By Jeff Koftinoff <jeffk@jdkoftinoff.com>
 *
 * https://clicker.jdkoftinoff.com/projects/trac/jdks/wiki/libjdkmidijava
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
import java.util.Vector;
import java.io.Serializable;


/**
 *
 */
public class MIDISystemExclusive
        implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 7907376115507540412L;
    protected Vector<Integer> sysex_bytes;
    protected int chk_sum;
    
    
    /** Creates a new instance of MIDISystemExclusive */
    public MIDISystemExclusive()
    {
        sysex_bytes = new Vector<Integer>(64);
        chk_sum = 0;
    }
    
    /** Creates a new instance of MIDISystemExclusive */
    public MIDISystemExclusive(int initial_size )
    {
        sysex_bytes = new Vector<Integer>(initial_size);
        chk_sum=0;
    }
    
    public MIDISystemExclusive( MIDISystemExclusive other )
    {
        sysex_bytes = new Vector<Integer>( other.sysex_bytes );
        chk_sum = other.chk_sum;
    }
         
    public void clear()
    {
        sysex_bytes.clear();
        chk_sum=0;
    }
    
    public int getLength()
    {
        return sysex_bytes.size();
    }

    public int getByte( int index )
    {
        return sysex_bytes.elementAt(index);
    }
    public void putByte( int val ) throws MIDIError
    {
        if( val<0 || val>0x7f )
            throw new MIDIRangeError( "System Exclusive data byte out of range" );
        sysex_bytes.add( val );
        chk_sum = (chk_sum + val) & 0x7f;
    }
    public void putSysByte( int val ) throws MIDIError
    {
        if( val<0x80 || val>0xff )
            throw new MIDIRangeError( "System Exclusive system byte out of range" );
        sysex_bytes.add( val );
    }
    
    public void	putEXC()
    {
        sysex_bytes.add( MIDI.Status.SYSEX_START );
    }
    public  void putEOX()
    {
        sysex_bytes.add( MIDI.Status.SYSEX_END );
    }
    
    public void putChecksum() 
    {
        sysex_bytes.add( chk_sum & 0x7f );
    }
    public int getChecksum()
    {
        return chk_sum & 0x7f;
    }
    public void clearChecksum()
    {
        chk_sum = 0;
    }
    
    // low nibble first
    public void putNibblizedByteLE( int b )
    {
        sysex_bytes.add( b&0xf );
        sysex_bytes.add( b>>4 );
    }
    public int getNibbilizedByteLE( int pos )
    {
        return (sysex_bytes.get(pos)&0xf) + ((sysex_bytes.get(pos+1)&0x0f)<<4);
    }
    
    // high nibble first
    public void putNibblizedByteBE( int b )
    {
        sysex_bytes.add( b>>4 );
        sysex_bytes.add( b&0xf );
    }
    public int getNibblizedByteBE( int pos )
    {
        return ((sysex_bytes.get(pos)&0x0f)<<4) + (sysex_bytes.get(pos+1)&0x0f);
    }
}
