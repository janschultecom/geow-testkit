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

import static org.geow.testkit.GeneralTesting.likelihood;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.geow.model.IOsmElement;
import org.geow.model.IOsmMember;
import org.geow.model.IOsmNd;
import org.geow.model.IOsmNode;
import org.geow.model.IOsmRelation;
import org.geow.model.IOsmTag;
import org.geow.model.IOsmWay;
import org.geow.model.impl.OsmElementImpl;
import org.geow.model.impl.OsmMemberImpl;
import org.geow.model.impl.OsmNdImpl;
import org.geow.model.impl.OsmNodeImpl;
import org.geow.model.impl.OsmRelationImpl;
import org.geow.model.impl.OsmTagImpl;
import org.geow.model.impl.OsmWayImpl;
import org.geow.model.util.OsmTagMapImpl;


public class OsmTesting {

	private static final long MAX_NDS = 100L;
	private static final long MAX_TAGS = 10L;
	private static String timestamp;
	
	static{
		try {
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTimeInMillis(System.currentTimeMillis());
			XMLGregorianCalendar xgc = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(gc);
			 timestamp = xgc.normalize().toXMLFormat();
		} catch (DatatypeConfigurationException e) {
			 timestamp = "2013-01-01T09:00:00";
		}
	}
	
	private static OsmTagMapImpl tagMap = new OsmTagMapImpl();
		
	public static BigInteger generateOsmVersion() {
		return BigInteger.valueOf(GeneralTesting.createId(10L));
	}

	public static String generateOsmUser() {
		return "user-" + GeneralTesting.createId();
	}

	public static BigInteger generateOsmUid() {
		return BigInteger.valueOf(GeneralTesting.createId(10000L));
	}

	public static BigDecimal generateOsmLon() {
		return BigDecimal.valueOf(GeoTesting.createLon());
	}

	public static BigDecimal generateOsmLat() {
		return BigDecimal.valueOf(GeoTesting.createLat());
	}

	public static BigInteger generateOsmId() {
		return BigInteger.valueOf(GeneralTesting.createId());
	}

	public static BigInteger generateOsmChangeset() {
		return BigInteger.valueOf(GeneralTesting.createUpperLimit(1000000L));
	}

	public static String generateXMLTimestamp() {
		return timestamp;
	}
	
	public static IOsmNode generateOsmNode() {
		IOsmNode node = new OsmNodeImpl();
		generateProperties(node);
		node.setLat(generateOsmLat());
		node.setLon(generateOsmLon());
		return node;
	}
	
	
	public static IOsmNode generateOsmNodeWithTags() {
		IOsmNode node = generateOsmNode();
		List<IOsmTag> tags = node.getTag();
		
		for (int j = 0; j < GeneralTesting.createUpperLimit(10L); j++) {			
			IOsmTag tag = generateTag();
			if (!tags.contains(tag)) {
				tags.add(tag);
			}
		}
		return node;
	}

	public static IOsmWay generateOsmWay() {
		IOsmWay way = new OsmWayImpl();
		generateProperties(way);
		return way;
	}

	private static void generateProperties(IOsmElement element) {
		element.setChangeset(generateOsmChangeset());
		element.setId(generateOsmId());
		element.setTimestamp(generateXMLTimestamp());
		element.setUid(generateOsmUid());
		element.setUser(generateOsmUser());
		element.setVersion(generateOsmVersion());
	}
	
	public static IOsmWay generateOsmWayWithTags() {
		IOsmWay way = generateOsmWay();
		List<IOsmTag> tags = way.getTag();
		
		for (int j = 0; j < GeneralTesting.createUpperLimit(MAX_TAGS); j++) {			
			IOsmTag tag = generateTag();
			if (!tags.contains(tag)) {
				tags.add(tag);
			}
		}
		return way;
	}
	
	public static IOsmWay generateOsmWayWithTagsAndNds() {
		IOsmWay way = generateOsmWayWithTags();
		List<IOsmNd> nds = way.getNd();
		
		for (int j = 0; j < GeneralTesting.createUpperLimit(MAX_NDS); j++) {			
			IOsmNd nd = generateNd();
			if (!nds.contains(nd)) {
				nds.add(nd);
			}
		}
		return way;
	}
	
	public static IOsmRelation generateOsmRelation() {
		IOsmRelation relation = new OsmRelationImpl();
		generateProperties(relation);
		return relation;
	}
	
	public static IOsmRelation generateOsmRelationWithTags() {
		IOsmRelation relation = generateOsmRelation();
		List<IOsmTag> tags = relation.getTag();
		
		for (int j = 0; j < GeneralTesting.createUpperLimit(MAX_TAGS); j++) {			
			IOsmTag tag = generateTag();
			if (!tags.contains(tag)) {
				tags.add(tag);
			}
		}
		return relation;
	}
	
	public static IOsmRelation generateOsmRelationWithTagsAndMembers() {
		IOsmRelation relation = generateOsmRelationWithTags();
		List<IOsmMember> members = relation.getMember();
		
		for (int j = 0; j < GeneralTesting.createUpperLimit(MAX_NDS); j++) {			
			IOsmMember nd = generateMember();
			if (!members.contains(nd)) {
				members.add(nd);
			}
		}
		return relation;
	}
	
	public static IOsmNode generateModifiedNode(IOsmNode original){
		IOsmNode revised = new OsmNodeImpl(original.getId());
		
		modifyNodeProperties(original, revised);		
		modifyTags(original, revised);
		
		return revised;
	}
	
	public static IOsmNode generateModifiedNodeWithoutTags(IOsmNode original){
		IOsmNode revised = new OsmNodeImpl(original.getId());
		
		modifyNodeProperties(original, revised);
		List<IOsmTag> revisedTags = revised.getTag();
		for(IOsmTag tag : original.getTag()){
			revisedTags.add(tag);
		}
		
		return revised;
	}
	
	public static IOsmWay generateModifiedOsmWay(IOsmWay original) {
		IOsmWay revised = new OsmWayImpl(original.getId());
		
		modifyWayProperties(original, revised);		
		modifyTags(original, revised);
		modifyNds(original, revised);
		return revised;
	}

	public static IOsmRelation generateModifiedOsmRelation(IOsmRelation original) {
		IOsmRelation revised = new OsmRelationImpl(original.getId());
		
		modifyRelationProperties(original, revised);		
		modifyTags(original, revised);
		modifyMembers(original, revised);
		return revised;
	}
	
	private static void modifyTags(IOsmElement original, IOsmElement revised) {
		Iterator<IOsmTag> it = original.getTag().iterator();
		List<IOsmTag> tags = revised.getTag();
		while(it.hasNext()){
			IOsmTag tag = it.next();
			if(likelihood(0.1)){
				tags.add(generateTag());
			}else{
				if(likelihood(0.9)){
					tags.add(tag);
				}
			}
			
		}
	}
	
	private static void modifyNds(IOsmWay original, IOsmWay revised) {
		Iterator<IOsmNd> it = original.getNd().iterator();
		List<IOsmNd> nds = revised.getNd();
		while(it.hasNext()){
			IOsmNd nd = it.next();
			if(likelihood(0.1)){
				nds.add(generateNd());
			}else{
				if(likelihood(0.9)){
					nds.add(nd);
				}
			}
			
		}
	}

	private static void modifyMembers(IOsmRelation original, IOsmRelation revised) {
		Iterator<IOsmMember> it = original.getMember().iterator();
		List<IOsmMember> members = revised.getMember();
		while(it.hasNext()){
			IOsmMember member = it.next();
			if(likelihood(0.1)){
				members.add(generateMember());
			}else{
				if(likelihood(0.9)){
					members.add(member);
				}
			}
			
		}
	}
	
	public static void modifyNodeProperties(IOsmNode original, IOsmNode revised) {
		modifyProperties(original, revised);
		
		if(likelihood(0.1)){
			revised.setLat(generateOsmLat());
		}else{
			revised.setLat(original.getLat());
		}
		
		if(likelihood(0.1)){
			revised.setLon(generateOsmLon());
		}else{
			revised.setLon(original.getLon());
		}
	}

	private static void modifyProperties(IOsmElement original, IOsmElement revised) {
		if(likelihood(0.1)){
			revised.setChangeset(generateOsmChangeset());
		}else{
			revised.setChangeset(original.getChangeset());
		}
		
		if(likelihood(0.1)){
			revised.setTimestamp(generateXMLTimestamp());
		}else{
			revised.setTimestamp(original.getTimestamp());
		}
		
		if(likelihood(0.1)){
			revised.setUid(generateOsmUid());
		}else{
			revised.setUid(original.getUid());
		}
		
		if(likelihood(0.1)){
			revised.setUser(generateOsmUser());
		}else{
			revised.setUser(original.getUser());
		}
		
		if(likelihood(0.1)){
			revised.setVersion(generateOsmVersion());
		}else{
			revised.setVersion(original.getVersion());
		}
	}
	
	private static void modifyWayProperties(IOsmWay original, IOsmWay revised) {
		modifyProperties(original, revised);
	}
	
	private static void modifyRelationProperties(IOsmRelation original, IOsmRelation revised) {
		modifyProperties(original, revised);
	}
	
	private static String randomKey() {
		String randomKey = "highway";
		Set<Entry<Short, String>> entrySet = tagMap.getTagKeys();
		int size = entrySet.size();
		int key = (int) (Math.round((Math.random() * size)) - 1);
		if (key >= 0 && key < size) {
			randomKey = ((Entry<Integer,String>)entrySet.toArray()[key]).getValue();
		}

		return randomKey;
	}

	private static String randomValue() {
		String randomValue = "tertiary";
		Set<Entry<Short, String>> entrySet = tagMap.getTagValues();
		int size = entrySet.size();
		int value = (int) (Math.round((Math.random() * size)) - 1);
		if (value >= 0 && value < size) {
			randomValue = ((Entry<Integer,String>)entrySet.toArray()[value]).getValue();
		} 
		return randomValue;
	}
	
	public static IOsmTag generateTag() {		
		return new OsmTagImpl(randomKey(), randomValue());
	}
	
	public static IOsmNd generateNd() {		
		return new OsmNdImpl(GeneralTesting.createId());
	}
	
	public static IOsmMember generateMember() {		
		BigInteger ref = BigInteger.valueOf(GeneralTesting.createId());
		String role = likelihood(0.5) ? "inner" : "outer";
		String type;
		if(likelihood(0.33)){
			type = "node";
		}else{
			if(likelihood(0.5)){
				type = "way";
			}else{
				type = "relation";
			}
		}
		return new OsmMemberImpl(ref,role,type);
	}

}
