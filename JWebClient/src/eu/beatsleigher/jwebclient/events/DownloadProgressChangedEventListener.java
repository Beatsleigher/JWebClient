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

/**
 * Listener interface for JWebClient.
 * Provides (a) method(s) which will be called when the progress of a download has changed.
 * 
 * @author Beatsleigher
 * @since 30-09-2014
 * @version 1.0
 */
public interface DownloadProgressChangedEventListener {
    
    public void onDownloadProgressChanged(DownloadProgressChangedEvent evt);
    
}
