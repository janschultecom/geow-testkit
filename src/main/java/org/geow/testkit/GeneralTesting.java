/*******************************************************************************
 * Copyright 2013 Jan Schulte
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.geow.testkit;

import org.apache.commons.lang3.RandomStringUtils;

public class GeneralTesting {

	private static final Long DEFAULT_MAX_ID = Long.MAX_VALUE;
	private static final int DEFAULT_MAX_STRING = 100;
	
	public static long createId(){
		return createId(DEFAULT_MAX_ID);
	}
	public static long createId(Long maximum){
		return Math.round(Math.random()*maximum);
	}
	
	public static int createId(int maximum){
		return (int) Math.round(Math.random()*maximum);
	}
	
	public static long createUpperLimit(long maximum){
		return Math.round(Math.random()*maximum);
	}
	
	public static int createRandomUpperLimit(int maximum){
		return (int) Math.round(Math.random()*maximum);
	}
	
	public static String createRandomString(int count){
		return RandomStringUtils.randomAlphabetic(count);
	}
	
	public static String createRandomString(){
		return createRandomString(DEFAULT_MAX_STRING);
	}
	
	public static boolean likelihood(Double probability) {
		return Math.random() < probability ? true : false;
	}
}
