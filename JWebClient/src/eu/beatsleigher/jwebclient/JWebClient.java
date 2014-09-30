/*
 * Copyright (C) 2014 Beatsleigher.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */

package eu.beatsleigher.jwebclient;

import eu.beatsleigher.jwebclient.interfaces.Disposeable;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * The JWebClient class provides methods, functions and properties to make downloading data, 
 * files and pretty much anything else as easy as possible from within the Java runtime environment.
 * 
 * The JWebClient is aimed to be event-driven and simple to use.
 * For more information on the methods, variables, functions, events and properties, 
 * please read their respective documentations.
 * 
 * @author Beatsleigher
 * @since 30-09-2014 (American: 09-30-2014)
 * @version 1.0
 */
@SuppressWarnings({"FieldMayBeFinal"})
public class JWebClient implements Disposeable {
    
    /**
     * If a file is to be downloaded, this is the variable that stores its information.
     * @author Beatsleigher
     * @since 30-09-2014
     */
    private final File dlPath;
    
    /**
     * This variable stores the URL (web address) from where the data/file/string will be downloaded from.
     * @author Beatsleigher
     * @since 30-09-2014
     * @version 1.0
     */
    private final URL dlSource;
    
    /**
     * If a String-object is downloaded, this is where it'll be stored.
     * @author Beatsleigher
     * @since 30-09-2014
     * @version 1.0
     */
    private String downloadedString = null;
    
    /**
     * If data is downloaded, this is where the data will be stored in the form of a byte array.
     * @author Beatsleigher
     * @since 30-09-2014
     * @version 1.0
     */
    private byte[] downloadedData = null;
    
    /**
     * This list contains all of the event handlers for the onDownloadProgressChangedEvent-events.
     * These events, as obviously stated by the name, will be fired when the progress of a download has changed. Duh...
     * @author Beatsleigher
     * @since 30-09-2014
     * @version 1.0
     */
    private final List<Object> downloadProgressChangedEventHandlerList;
    
    /**
     * This list contains all of the event handlers for the onDownloadFileCompletedEvent-events.
     * @author Beatsleigher
     * @since 30-09-2014
     * @version 1.0
     */
    private final List<Object> downloadFileCompletedEventHandlerList;
    
    /**
     * This list contains all of the handlers for the onDownloadStringCompletedEvent-events.
     * @author Beatsleigher
     * @since 30-09-2014
     * @version 1.0
     */
    private final List<Object> downloadStringCompletedEventHandlerList;
    
    /**
     * This... you know what this list contains. If you don't, you really, <i>really</i> shouldn't be looking at this JavaDoc...
     * @author Beatsleigher
     * @since 30-09-2014
     * @version 1.0
     */
    private final List<Object> downloadDataCompletedEventHandlerList;
    
    /**
     * The first of many constructors.
     * 
     * I do NOT recommend using this constructor, but as I know so many developers (who aren't the brightest bulbs in the lamp), 
     * I'll just add this one.
     * @param dlSource The URL (web address) from where to download the file/string/data from.
     * E.G.: "http://yourweb.com/file.txt"
     * @param dlPath The file to download the file to. This {@see String} object will be converted to a {@see File} object!
     * @throws IOException This exception would only be thrown if an idiot provided an invalid URL or put null as a parameter.
     * (If you're that idiot, and you're reading this, please note that there <b>are</b> also other constructors to choose from...)
     * @author Beatsleigher
     * @since 30-09-2014
     * @version 1.0
     */
    public JWebClient(String dlSource, String dlPath) throws IOException {
        this.dlPath = new File(dlPath);
        this.dlSource = new URL(dlSource);
        this.downloadProgressChangedEventHandlerList = new ArrayList<>();
        this.downloadFileCompletedEventHandlerList = new ArrayList<>();
        this.downloadStringCompletedEventHandlerList = new ArrayList<>();
        this.downloadDataCompletedEventHandlerList = new ArrayList<>();
    }
    
    /**
     * Basic constructor.
     * 
     * This constructor is more forgiving than others. 
     * @param dlSource The URL (web address) from which the file/string/data will be downloaded.
     * This parameter will be converted to a URL object.
     * E.G.: http://yourweb.com/file.txt
     * @param dlPath The file to download the file to. As mentioned above, this constructor is more forgiving than others.
     * Feel free to set this parameter to null. Just don't be stupid enough to then try and download a file. 
     * It'll blow a hole in the space-time continuum. Trust me on this. I'm the one who developed this thing.
     * @throws IOException This exception will only be thrown if you typed an invalid URL.
     * @author Beatsleigher
     * @since 30-09-2014
     * @version 1.0
     */
    public JWebClient(String dlSource, File dlPath) throws IOException {
        this.dlPath = dlPath;
        this.dlSource = new URL(dlSource);
        this.downloadProgressChangedEventHandlerList = new ArrayList<>();
        this.downloadFileCompletedEventHandlerList = new ArrayList<>();
        this.downloadStringCompletedEventHandlerList = new ArrayList<>();
        this.downloadDataCompletedEventHandlerList = new ArrayList<>();
    }
    
    /**
     * Basic constructor.
     * 
     * Be careful with this one. It might blow! (Enter awesome movie quote here)
     * @param dlSource The URl (web address) from which to download the file/string/data.
     * This parameter is a {@see URL} object.
     * @param dlPath The path to download the file to.
     * This {@see String} object is converted to a {@see File} object. Be careful not to set this to null.
     * @throws IOException This exception is thrown if you're just being plain stupid.
     * @author Beatsleigher
     * @since 30-09-2014
     * @version 1.0
     */
    public JWebClient(URL dlSource, String dlPath) throws IOException {
        this.dlSource = dlSource;
        this.dlPath = new File(dlPath);
        this.downloadProgressChangedEventHandlerList = new ArrayList<>();
        this.downloadFileCompletedEventHandlerList = new ArrayList<>();
        this.downloadStringCompletedEventHandlerList = new ArrayList<>();
        this.downloadDataCompletedEventHandlerList = new ArrayList<>();
    }
    
    /**
     * <b>Recommended Constructor!</b>
     * 
     * This constructor is the (second) most forgiving constructor in this class!
     * 
     * This constructor only takes explicit {@see URL} and {@see File} objects.
     * @param dlSource The URL (web address) to download the file/string/data from.
     * E.G.: http://yourweb.com/file.txt
     * @param dlPath The path to download the file to.
     * @author Beatsleigher
     * @since 30-09-2014
     * @version 1.0
     */
    public JWebClient(URL dlSource, File dlPath) {
        this.dlSource = dlSource;
        this.dlPath = dlPath;
        this.downloadProgressChangedEventHandlerList = new ArrayList<>();
        this.downloadFileCompletedEventHandlerList = new ArrayList<>();
        this.downloadStringCompletedEventHandlerList = new ArrayList<>();
        this.downloadDataCompletedEventHandlerList = new ArrayList<>();
    }
    
    public JWebClient(URL dlSource) {
        this.dlSource = dlSource;
        this.dlPath = null;
        this.downloadProgressChangedEventHandlerList = new ArrayList<>();
        this.downloadFileCompletedEventHandlerList = new ArrayList<>();
        this.downloadStringCompletedEventHandlerList = new ArrayList<>();
        this.downloadDataCompletedEventHandlerList = new ArrayList<>();
    }

    @Override
    public void dispose() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
