package control.forms.tabs;
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.util.Util;
import control.ControlPaint;


/**
 * Controller class for Print Tab.
 * 
 * @author Julius Huelsmann
 * @version %I%, %U%
 */
public class CTabPrint implements ActionListener {


	/**
	 * Instance of the main controller.
	 */
	private ControlPaint cp;
	
	
	/**
	 * Constructor: save main controller.
	 * @param _cp instance of main controller 
	 */
	public CTabPrint(final ControlPaint _cp) {
		this.cp = _cp;
	}
	


	/**
	 * {@inheritDoc}
	 */
	public final void actionPerformed(final ActionEvent _event) {
		if (_event.getSource().equals(
				cp.getView().getTabs().getTab_print().getTb_print()
				.getActionCause())) {
        	Util.print(cp.getPicture());
		}
	}

}
