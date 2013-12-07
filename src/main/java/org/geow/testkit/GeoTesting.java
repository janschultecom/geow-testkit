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

import org.geow.key.KeyGenerator;

public class GeoTesting {

	public static double createLon() {
		return Math.random() * 360 - 180;
	}

	public static double createLat() {
		return Math.random() * 180 - 90;
	}

	public static double[] createLonLat(){
		return new double[]{createLon(),createLat()};
	}
	
	public static double[] createLonLatInRange(double minLon, double maxLon, double minLat, double maxLat){
		double lon = 0.0;
		double lat = 0.0;
		
		lon = minLon + (maxLon - minLon) * Math.random();
		lat = minLat + (maxLat - minLat) * Math.random();
		
		return new double[]{ lon,lat};
	}
	
	public static double[] createLonLatInNeighbouringBB(Long bb,
			KeyGenerator keyGen) {

		double[] lonlat = keyGen.decodeParallel(bb);
		double longitude = lonlat[0];
		double latitude = lonlat[1];
		Double[] boundingBox = keyGen.getBoundingBox(bb);

		double lon = (longitude - boundingBox[0])
				+ (Math.random() * (boundingBox[0] * 2));
		double lat = (latitude - boundingBox[1])
				+ (Math.random() * (boundingBox[1] * 2));
		return new double[] { lon, lat };

	}

	public static double[] createLonLatInBB(long bb, KeyGenerator bbGen,
			KeyGenerator pointGen) {

		long pointHash = createLonLatInBB(bb, bbGen);
		
		double[] point = pointGen.decodeParallel(pointHash);
		
		return point;

	}

	public static long createLonLatInBB(long bb, KeyGenerator bbGen) {
		long maxNumber = bbGen.getMaxNumber();
		long pointHash = bb | (long) (maxNumber * Math.random());
		return pointHash;
	}

}
