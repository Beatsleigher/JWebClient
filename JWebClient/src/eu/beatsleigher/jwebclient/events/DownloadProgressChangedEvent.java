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

package eu.beatsleigher.jwebclient.events;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Event class.
 * 
 * This class represents an event which is fired when the progress of an ongoing event has changed.
 * @author Beatsleigher
 */
public class DownloadProgressChangedEvent extends EventObject {
    
    private final URL downloadSource;
    private final File downloadPath;
    private final float progressPercentage;
    private final long sourceSize;
    private final long fileSize;

    public DownloadProgressChangedEvent(Object source, URL downloadSource, File downloadPath, 
                                                                           float progressPercentage, long sourceSize, long fileSize) {
        super(source);
        this.source = source;
        this.downloadPath = downloadPath;
        this.downloadSource = downloadSource;
        this.progressPercentage = progressPercentage;
        this.sourceSize = sourceSize;
        this.fileSize = fileSize;
    }
    
    /**
     * Gets and returns the URL from which the data was downloaded.
     * @return {@see URL} object containing the web address
     */
    public URL getDownloadSource() { return downloadSource; }
    
    /**
     * Gets and returns the path to where the file is being downloaded.
     * @return A {@see File} object which represents the file that is being downloaded to the local computer.
     * @author Beatsleigher
     * @since 30-09-2014
     * @version 1.0
     */
    public File getDownloadPath() { return downloadPath; }
    
    /**
     * Gets and returns the progress percentage.
     * @return Floating point number (float) containing the progress of the download.
     * @author Beatsleigher
     * @since 30-09-2014
     * @version 1.0
     */
    public float getProgressPercentage() { return progressPercentage; }
    
    /**
     * Gets and returns the size of the file that is being downloaded.
     * (The remote file's size (in bytes))
     * @return A 64-bit signed integer containing the length of the remote file
     * @author Beatsleigher
     * @since 30-09-2014
     * @version 1.0
     */
    public long getSourceSize() { return sourceSize; }
    
    /**
     * Gets and returns the size of the local file which is being downloaded.
     * @return A 64-bit signed integer containing the size of the local file.
     * @author Beatsleigher
     * @since 30-09-2014
     * @version 1.0
     */
    public long getFileSize() { return fileSize; }
    
}
