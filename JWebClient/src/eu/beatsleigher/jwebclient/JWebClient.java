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
import eu.beatsleigher.jwebclient.events.*;
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
public abstract class JWebClient implements Disposeable {
    
    //<editor-fold defaultstate="collapsed" desc="Variables">
    /**
     * If a file is to be downloaded, this is the variable that stores its information.
     * @author Beatsleigher
     * @since 30-09-2014
     */
    private File dlPath;
    
    /**
     * This variable stores the URL (web address) from where the data/file/string will be downloaded from.
     * @author Beatsleigher
     * @since 30-09-2014
     * @version 1.0
     */
    private URL dlSource;
    
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
    private List<DownloadProgressChangedEventListener> downloadProgressChangedEventHandlerList;
    
    /**
     * This list contains all of the event handlers for the onDownloadFileCompletedEvent-events.
     * @author Beatsleigher
     * @since 30-09-2014
     * @version 1.0
     */
    private List<DownloadFileCompletedEventListener> downloadFileCompletedEventHandlerList;
    
    /**
     * This list contains all of the handlers for the onDownloadStringCompletedEvent-events.
     * @author Beatsleigher
     * @since 30-09-2014
     * @version 1.0
     */
    private List<DownloadStringCompletedEventListener> downloadStringCompletedEventHandlerList;
    
    /**
     * This... you know what this list contains. If you don't, you really, <i>really</i> shouldn't be looking at this JavaDoc...
     * @author Beatsleigher
     * @since 30-09-2014
     * @version 1.0
     */
    private List<Object> downloadDataCompletedEventHandlerList;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * The first of many constructors.
     * 
     * I do NOT recommend using this constructor, but as I know so many developers (who aren't the brightest bulbs in the lamp), 
     * I'll just add this one.
     * @param dlSource The URL (web address) from where to download the file/string/data from.
     * E.G.: "http://yourweb.com/file.txt"
     * @param dlPath The file to download the file to. This {@link String} object will be converted to a {@link File} object!
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
     * This parameter is a {@link URL} object.
     * @param dlPath The path to download the file to.
     * This {@link String} object is converted to a {@link File} object. Be careful not to set this to null.
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
     * This constructor only takes explicit {@link URL} and {@link File} objects.
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
    
    /**
     * This constructor is as basic as they come.
     * No parameters are required.
     * After using this constructor, please make sure you set the respective properties for the method(s) you'd like to use.
     */
    public JWebClient() {
        this.dlPath = null;
        this.dlSource = null;
        this.downloadProgressChangedEventHandlerList = new ArrayList<>();
        this.downloadFileCompletedEventHandlerList = new ArrayList<>();
        this.downloadStringCompletedEventHandlerList = new ArrayList<>();
        this.downloadDataCompletedEventHandlerList = new ArrayList<>();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Properties">
    /**
     * Gets and returns the download source (the URL from which the file/string/data is downloaded from).
     * This method is also present in the different events provided with this class.
     * Use those in-event methods when possible.
     * @return The URL from which the file/string/data is downloaded from.
     * @author Beatsleigher
     * @since 30-09-2014
     * @version 1.0
     */
    public URL getDownloadSource() { return dlSource; }
    
    /**
     * Gets and returns the download path (the path on the computer where the file is downloaded to).
     * E.G.: 
     * /home/user01/downloadedfile.sh
     * C:\Users\User01\Documents\downloadedFile.jpg
     * This method is also present in the different events provided with this class.
     * Use the in-event methods when possible.
     * @return The download path on this computer as a {@link File} object.
     * @author Beatsleigher
     * @since 30-09-2014
     * @version 1.0
     */
    public File getDownloadPath() { return dlPath; }
    
    /**
     * Gets and returns the downloaded {@link String} object.
     * This method should only be accessed when you're sure, that a string has been downloaded.
     * This method is present in the events provided with this class.
     * Use the in-event methods when possible.
     * @return The downloaded string. If downloaded string is null, returns null.
     * @author Beatsleigher
     * @since 30-09-2014
     * @version 1.0
     */
    public String getDownloadedString() { return downloadedString; }
    
    /**
     * Gets and returns the downloaded data.
     * Only use this method when you are sure, that data has been downloaded.
     * This method is present in the events provided with this class and their use is preferred.
     * @return The downloaded data as byte-array. If data is null, returns null.
     * @author Beatsleigher
     * @since 30-09-2014
     * @version 1.0
     */
    public byte[] getDownloadedData() { return downloadedData; }
    
    /**
     * Sets the {@link URL} object from which to download the file/string/data from.
     * @param url The {@link URL} object from which to download from.
     */
    public void setDownloadSource(URL url) { this.dlSource = url; }
    
    /**
     * Sets the {@link File} object to download the file to.
     * @param path The {@link File} object from which to download the file to.
     */
    public void setDownloadPath(File path) { this.dlPath = path; }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Event Processing Methods">
    public abstract void addDownloadProgressChangedEventListener();
    
    public abstract void addDownloadFileCompletedEventListener();
    
    public abstract void addDownloadStringCompletedEventHandler();
    
    public abstract void addDownloadDataCompletedEventHandler();
    //</editor-fold>
    
    /**
     * Downloads a file from the URL provided in the constructor 
     * and saves it to the location provided in the constructor on to the local computer.
     * @throws java.io.IOException This exception is thrown if something goes wrong during the download.
     * @author Beatsleigher
     * @since 30-09-2014
     * @version 1.0
     */
    public void downloadFile() throws IOException {
        HttpURLConnection httpConnect = null;
        float totalDataRead;
        BufferedInputStream bufferedIStream = null;
        FileOutputStream fileOStream = null;
        BufferedOutputStream bufferedOStream = null;
        byte[] data = new byte[1024];
        int i = 0;
        float progressPercentage = 0;
        long remoteSize = 0;
        
        try {
            httpConnect = (HttpURLConnection)dlSource.openConnection();
            totalDataRead = 0;
            remoteSize = httpConnect.getContentLengthLong();
            
            bufferedIStream = new BufferedInputStream(httpConnect.getInputStream());
            fileOStream = new FileOutputStream(dlPath);
            bufferedOStream = new BufferedOutputStream(fileOStream, 1024);
            
            // Download file
            while ((i = bufferedIStream.read(data, 0, 1024)) >= 0) {
                totalDataRead += i;
                bufferedOStream.write(data, 0, i);
                progressPercentage = (totalDataRead / remoteSize) * 100;
                for (DownloadProgressChangedEventListener evt : this.downloadProgressChangedEventHandlerList)
                    evt.onDownloadProgressChanged(
                            new DownloadProgressChangedEvent(this, dlSource, dlPath, progressPercentage, remoteSize, (long)totalDataRead));
            }
        } finally {
            bufferedIStream.close();
            fileOStream.close();
            bufferedIStream.close();
            for (DownloadFileCompletedEventListener evt : this.downloadFileCompletedEventHandlerList)
                evt.onDownloadFileCompletedEvent(new DownloadFileCompletedEvent(this, dlPath, dlSource));
        }
    }
    
    protected IOException m_downloadFileAsyncException = null;
    /**
     * Downloads a file from the URL provided too the path provided.
     * This methods runs asynchronously. 
     * The events will still be fired.
     * @throws IOException This exception is thrown if something goes wrong during the download.
     * @author Beatsleigher
     * @since 1-10-2014
     * @version 1.0
     */
    public void downloadFileAsync() throws IOException {
        m_downloadFileAsyncException = null;
        new Thread() {
            @Override
            public void run() {
                HttpURLConnection httpConnect = null;
                float totalDataRead;
                BufferedInputStream bufferedIStream = null;
                FileOutputStream fileOStream = null;
                BufferedOutputStream bufferedOStream = null;
                byte[] data = new byte[1024];
                int i = 0;
                float progressPercentage = 0;
                long remoteSize = 0;

                try {
                    httpConnect = (HttpURLConnection)dlSource.openConnection();
                    totalDataRead = 0;
                    remoteSize = httpConnect.getContentLengthLong();

                    bufferedIStream = new BufferedInputStream(httpConnect.getInputStream());
                    fileOStream = new FileOutputStream(dlPath);
                    bufferedOStream = new BufferedOutputStream(fileOStream, 1024);

                    // Download file
                    while ((i = bufferedIStream.read(data, 0, 1024)) >= 0) {
                        totalDataRead += i;
                        bufferedOStream.write(data, 0, i);
                        progressPercentage = (totalDataRead / remoteSize) * 100;
                        for (DownloadProgressChangedEventListener evt : downloadProgressChangedEventHandlerList)
                            evt.onDownloadProgressChanged(
                                    new DownloadProgressChangedEvent(this, dlSource, dlPath, progressPercentage, remoteSize, (long)totalDataRead));
                    }
                } catch (IOException ex) {
                    m_downloadFileAsyncException = ex;
                } finally {
                    try {
                        bufferedIStream.close();
                        fileOStream.close();
                        bufferedIStream.close();
                    } catch (IOException ex) {}
                    for (DownloadFileCompletedEventListener evt : downloadFileCompletedEventHandlerList)
                        evt.onDownloadFileCompletedEvent(new DownloadFileCompletedEvent(this, dlPath, dlSource));
                    interrupt();
                }
                interrupt();
            }
        }.start();
        if (m_downloadFileAsyncException != null)
            throw m_downloadFileAsyncException;
    }
    
    /**
     * Downloads and saves file from the provided {@link java.net.URL} to a {@link java.lang.String} object
     * @throws IOException This exception is thrown if something goes wrong during the download.
     * @author Beatsleigher
     * @since 01-09-2014
     * @version 1.0
     */
    public void downloadString() throws IOException {
        BufferedReader reader = null;
        String line = null;
        HttpURLConnection connection = null;
        StringBuilder readData = new StringBuilder();
        long remoteSize = 0;
        
        try {
            connection = (HttpURLConnection)dlSource.openConnection();
            reader = new BufferedReader(new InputStreamReader(dlSource.openStream()));
            remoteSize = connection.getContentLengthLong();
            
            while ((line = reader.readLine()) != null) {
                readData.append(String.format("{0}{1}", line, "\n"));
                for (DownloadProgressChangedEventListener evt : this.downloadProgressChangedEventHandlerList)
                    evt.onDownloadProgressChanged(
                            new DownloadProgressChangedEvent(this, dlSource, null, (readData.toString().length() / remoteSize) * 100, 
                                    remoteSize, (long)readData.toString().length()));
            }
            
        } finally {
            connection.disconnect();
            connection = null;
            reader.close();
            reader = null;
            downloadedString = readData.toString();
            for (DownloadStringCompletedEventListener evt : this.downloadStringCompletedEventHandlerList)
                evt.onDownloadStringCompleted(new DownloadStringCompletedEvent(this, dlSource, readData.toString()));
            readData.delete(0, readData.toString().length());
        }
    }
    
    protected IOException m_downloadStringAsyncException = null;
    /**
     * Downloads a String from the Internet asynchronously via the provided {@link java.net.URL}.
     * @throws IOException This is exception is thrown if something goes wrong during the download.
     * @author Beatsleigher
     * @since 01-09-2014
     * @version 1.0
     */
    public void downloadStringAsync() throws IOException {
        this.m_downloadStringAsyncException = null;
        new Thread() {
            @Override
            public void run() {
                BufferedReader reader = null;
                String line = null;
                HttpURLConnection connection = null;
                StringBuilder readData = new StringBuilder();
                long remoteSize = 0;

                try {
                    connection = (HttpURLConnection)dlSource.openConnection();
                    reader = new BufferedReader(new InputStreamReader(dlSource.openStream()));
                    remoteSize = connection.getContentLengthLong();

                    while ((line = reader.readLine()) != null) {
                        readData.append(String.format("{0}{1}", line, "\n"));
                        for (DownloadProgressChangedEventListener evt : downloadProgressChangedEventHandlerList)
                            evt.onDownloadProgressChanged(
                                    new DownloadProgressChangedEvent(this, dlSource, null, (readData.toString().length() / remoteSize) * 100, 
                                            remoteSize, (long)readData.toString().length()));
                    }

                } catch (IOException ex) {
                    m_downloadStringAsyncException = ex;
                } finally {
                    try {
                        connection.disconnect();
                        connection = null;
                        reader.close();
                        reader = null;
                    } catch (IOException ex) {}
                    downloadedString = readData.toString();
                    for (DownloadStringCompletedEventListener evt : downloadStringCompletedEventHandlerList)
                        evt.onDownloadStringCompleted(new DownloadStringCompletedEvent(this, dlSource, readData.toString()));
                    readData.delete(0, readData.toString().length());
                    interrupt();
                }
                interrupt();
            }
        }.start();
        if (m_downloadStringAsyncException != null)
            throw m_downloadStringAsyncException;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Overriden Methods">
    /** 
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * {@inheritDoc}
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        return obj.hashCode() == hashCode();
    }

    /**
     * {@inheritDoc}
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.dlPath);
        hash = 29 * hash + Objects.hashCode(this.dlSource);
        hash = 29 * hash + Objects.hashCode(this.downloadedString);
        hash = 29 * hash + Arrays.hashCode(this.downloadedData);
        hash = 29 * hash + Objects.hashCode(this.downloadProgressChangedEventHandlerList);
        hash = 29 * hash + Objects.hashCode(this.downloadFileCompletedEventHandlerList);
        hash = 29 * hash + Objects.hashCode(this.downloadStringCompletedEventHandlerList);
        hash = 29 * hash + Objects.hashCode(this.downloadDataCompletedEventHandlerList);
        hash = 29 * hash + Objects.hashCode(this.m_downloadFileAsyncException);
        return hash;
    }
    //</editor-fold>
    
}
