/*
 * MIDIMessage.java
 *
 * Created on 10 September 2006, 17:03 By Jeff Koftinoff <jeffk@jdkoftinoff.com>
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

import java.io.Serializable;
    
/**
 * The MIDIMessage class is a container for any type of MIDI Message.
 * @author jeffk
 */
public class MIDIMessage 
        implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 388972184698617337L;
    
    int status;
    int byte1;
    int byte2;
    int byte3;
    MIDISystemExclusive sysex;

    
    
    /** Creates a new instance of MIDIMessage, with no data */
    public MIDIMessage()
    {
        status = 0;
        byte1 = 0;
        byte2 = 0;
        byte3 = 0;
        sysex = null;
    }
    
    /** Creates a new instance of MIDIMessage, with a short message */
    public MIDIMessage( int status_, int byte1_, int byte2_ )
    {
        status = status_;
        byte1 = byte1_;
        byte2 = byte2_;
        byte3 = 0;
        sysex = null;
    }
    
    /** Creates a new instance of MIDIMessage, with a sysex */
    public MIDIMessage( MIDISystemExclusive sysex_ )
    {
        status = MIDI.Status.SYSEX_START;
        byte1 = 0;
        byte2 = 0;
        byte3 = 0;
        sysex = sysex_;
    }

    public MIDIMessage( MIDIMessage other )
    {
        status = other.status;
        byte1 = other.byte1;
        byte2 = other.byte2;
        byte3 = other.byte3;
        if( other.sysex != null )
            sysex = new MIDISystemExclusive( other.sysex );
        else
            sysex = null;
    }
    
    /** Set the object to represent a note on message */
    public void setNoteOn( int channel, int note, int velocity )
    {
        
        status = MIDI.Status.NOTE_ON + channel;
        byte1 = note;
        byte2 = velocity;
        sysex = null;
    }
    
    /** Set the object to represet a note off message with velocity */
    public void setNoteOff( int channel, int note, int velocity )
    {
        status = MIDI.Status.NOTE_OFF + channel;
        byte1 = note;
        byte2 = velocity;
        sysex = null;
    }
    
    
    /** if the message is a channel message, return true */
    public boolean isChannelMessage()
    {
        return status>=0x80 && status<0xf0;
    }
    
    /** if the message is a channel message, return the channel number it is on */
    public int getChannel() throws MIDIError
    {
        if( !isChannelMessage() )
            throw new MIDIStatusError("Message has no channel");
        return status & 0xf;
    }
    
    /** if the message is a note on message, return true.
     * Messages with note on status bytes and velocity of 0 are
     * actually note off messages
     */
    public boolean isNoteOn()
    {
        return (((status & 0xf0 ) == MIDI.Status.NOTE_ON) && byte2!=0);
    }
    
    /** if the message is a note off message, return true.
     * Messages with note on status bytes and velocity of 0 are
     * actually note off messages.
     */
    public boolean isNoteOff()
    {
        return ((status & 0xf0 ) == MIDI.Status.NOTE_OFF) ||
                (((status & 0xf0 ) == MIDI.Status.NOTE_ON) && byte2==0 );
    }
    
    public boolean isPolyAftertouch()
    {
        return ((status & 0xf0 ) == MIDI.Status.POLY_PRESSURE);
    }
    
    public boolean isSysEx()
    {
        return status == MIDI.Status.SYSEX_START && sysex!=null;
    }
    /** If the message is a note on, note off, or polyphonic aftertouch,
     * return the note number
     */
    public int getNote() throws MIDIError
    {
        if( !isNoteOn() && !isNoteOff() && !isPolyAftertouch() )
                throw new MIDIStatusError();
        //MIDI.StatusError("Message has no note");
        return byte1;
    }
    
    /** If the message is a note on, note off, or polyphonic aftertouch,
     * return the velocity or aftertouch
     */
    public int getVelocity() throws MIDIError
    {
        if( !isNoteOn() && !isNoteOff() && !isPolyAftertouch() )
                throw new MIDIStatusError("Message has no velocity");
        
        return byte2;
    }
    
    public void setSysex( MIDISystemExclusive sysex_ ) throws MIDIError
    {
        if( sysex_== null )
            throw new MIDIStatusError("Expected System Exclusive Message");
        
        sysex = sysex_;
        status = MIDI.Status.SYSEX_START;
    }
    
    public MIDISystemExclusive getSysex() throws MIDIError
    {
        if( !isSysEx() )
            throw new MIDIStatusError("Not a System Exclusive message");
        return sysex;
    }
    
}
