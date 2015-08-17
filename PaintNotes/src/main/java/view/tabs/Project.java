package view.tabs;


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


import javax.swing.JFrame;

import view.forms.Help;

/**
 * Panel for the Tab.
 * @author Julius Huelsmann
 * @version %I%, %U%
 */
@SuppressWarnings("serial")
public class Project extends Tab {

    
    
    
    /**
     * Constructor. Call super-constructor with the amount of sections the 
     * project tab contains.
     */
    public Project() {
        super(0);
    }
	/*
	 * Tab
	 */
	//name
	//default page values
	//backup settings

	@Override
	public void initializeHelpListeners(JFrame _jf, Help _c) {
		// TODO Auto-generated method stub
		
	}
	
	//pencil selection | pencil size selection | pencil color selection | 
	//select somethings | special items like graphc etc | copy-paste | 
	//change color of seleciton?
}
