package view.testing;


/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import model.util.adt.list.SecureListSort;


/**
 * The Model time-log class.
 * 
 * @author Julius Huelsmann
 * @version %I%, %U%
 */
public class MTimeLog implements Serializable {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The list of time items.
	 */
	private SecureListSort<MTime> ls_timeItems;

	
	/**
	 * Constructor of the model - time log class.
	 */
	public MTimeLog() {
		ls_timeItems = new SecureListSort<MTime>();
	}

	
	/**
	 * Returns an array of the titles of the list items that are added.
	 * @return the titles for the table.
	 */
	public final String[] getTitles() {
		ls_timeItems.toFirst(
				SecureListSort.ID_NO_PREDECESSOR,
				SecureListSort.ID_NO_PREDECESSOR);
		int length = 0;
		while (!ls_timeItems.isBehind() && !ls_timeItems.isEmpty()) {
			
			length++;
			ls_timeItems.next(SecureListSort.ID_NO_PREDECESSOR, 
					SecureListSort.ID_NO_PREDECESSOR);
		}
		
		int index = 0;
		String[] strg_title = new String[length];
		ls_timeItems.toFirst(SecureListSort.ID_NO_PREDECESSOR,
				SecureListSort.ID_NO_PREDECESSOR);
		while (!ls_timeItems.isBehind() && !ls_timeItems.isEmpty()) {
			strg_title[index] = ls_timeItems.getItem().getStrg_title();
			ls_timeItems.next(SecureListSort.ID_NO_PREDECESSOR, 
					SecureListSort.ID_NO_PREDECESSOR);
			index++;
		}
		
		return strg_title;
	}
	

	
	/**
	 * Returns an array of the titles of the list items that are added.
	 * @return the table.
	 */
	public final String[][] getTable() {
		ls_timeItems.toFirst(SecureListSort.ID_NO_PREDECESSOR, 
				SecureListSort.ID_NO_PREDECESSOR);
		int length = 0;
		while (!ls_timeItems.isBehind() && !ls_timeItems.isEmpty()) {
			
			length++;
			ls_timeItems.next(SecureListSort.ID_NO_PREDECESSOR,
					SecureListSort.ID_NO_PREDECESSOR);
		}
		
		int index = 0;
		String[][] strg_title = new String[length][2 + 1];
		ls_timeItems.toFirst(SecureListSort.ID_NO_PREDECESSOR,
				SecureListSort.ID_NO_PREDECESSOR);
		while (!ls_timeItems.isBehind() && !ls_timeItems.isEmpty()) {
			strg_title[index][0] = "" + ls_timeItems.getItem().getIdentifier();
			strg_title[index][1] = ls_timeItems.getItem().getStrg_title();

			int days = (int) (ls_timeItems.getItem().getTime() / 60 / 60 / 24);
			int hour = ((int) (ls_timeItems.getItem().getTime() / 60 / 60) 
					% 24);
			int min =  ((int) (ls_timeItems.getItem().getTime() / 60)) % 60;
			int sec =  ls_timeItems.getItem().getTime() % 60;
			
			String myTime = "";
			if (days != 0) {
				myTime = days + "Days, " + hour + "Hours, " + min 
						+ "min, " + sec + "sec";
			} else if (hour != 0) {
				myTime = hour + "Hours, " + min + "min, " + sec + "sec";
				
			} else if (min  != 0) {
				myTime = min + "min, " + sec + "sec";
				
			} else if (sec != 0) {
				myTime = sec + "sec";
				
			}
			
				
					
			strg_title[index][2] = "" + myTime;
			ls_timeItems.next(SecureListSort.ID_NO_PREDECESSOR, 
					SecureListSort.ID_NO_PREDECESSOR);
			index++;
		}
		
		return strg_title;
	}
	
	

	
	/**
	 * Add a new time-item.
	 * @param _title	the title of the new time item.
	 */
	public final void addTimeItem(final String _title) {
		MTime mt = new MTime(_title);
		ls_timeItems.insertSorted(mt, mt.getIdentifier(),
				SecureListSort.ID_NO_PREDECESSOR);
	}
	
	
	/**
	 * Get time - entry identified by its index.
	 * @param _index 	the index.
	 * @return			the entry identified by the index. If not found null.
	 */
	public final MTime getPerIndex(final int _index) {
		ls_timeItems.findSorted(_index, SecureListSort.ID_NO_PREDECESSOR,
				SecureListSort.ID_NO_PREDECESSOR);
		ls_timeItems.previous(SecureListSort.ID_NO_PREDECESSOR,
				SecureListSort.ID_NO_PREDECESSOR);
		MTime found = ls_timeItems.getItem();
		if (found.getIdentifier() != _index) {
			System.err.println("Fatal error");
		}
		
		return found;
	}
	

	/**
	 * Save the time log.
	 * @param _loc	the location where to save.
	 */
	public final void save(final String _loc) {

		try {
			System.out.println(_loc);
			FileOutputStream fos = new FileOutputStream(new File(_loc));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			oos.flush();
			oos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 
	 * @param _loc
	 * @return the loaded MTimeLog.
	 */
	public final MTimeLog load(final String _loc) {

		try {
			FileInputStream fos = new FileInputStream(new File(_loc));
			ObjectInputStream oos = new ObjectInputStream(fos);
			MTimeLog p = 
			(MTimeLog) oos.readObject();

			oos.close();
			fos.close();
			return p;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
