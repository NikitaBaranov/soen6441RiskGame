package soen6441;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import mapeditor.Continent;
import mapeditor.Editor;
import mapeditor.IContinent;
import mapeditor.IEditor;
import mapeditor.ILoadedMap;
import mapeditor.IMapLoader;
import mapeditor.ITerritory;
import mapeditor.IVerification;
import mapeditor.LoadedMap;
import mapeditor.MapLoader;
import mapeditor.Territory;
import mapeditor.Verification;

public class TestMapEditor {
	static String path;
	static String mapName;
	
	IMapLoader mapLoaderObj;
	ILoadedMap loadedMapObj;
	
	@BeforeClass
	public static void startup() {
		path = "D:\\app_test\\";
		mapName = "abbbb.map";
	}
	
	public void initDummyMap() {
		String authorName = "test";
		
		String continentName = "usa";
		Integer continentControlValue = 40;
		IContinent continent = new Continent();
		continent.setContinentName(continentName);
		continent.setControlValue(continentControlValue);
		
		String continentName2 = "canada";
		Integer continentControlValue2 = 40;
		IContinent two_continent = new Continent();
		two_continent.setContinentName(continentName2);
		two_continent.setControlValue(continentControlValue2);
		
		String territoryName = "usa1";
		Integer X = 900;
		Integer Y = 900;
		ArrayList<String> adjacents = new ArrayList<String>();
		adjacents.add("usa2");
		String continent1 = "usa";
		
		String territoryName2 = "usa2";
		Integer X2 = 900;
		Integer Y2 = 900;
		ArrayList<String> adjacents2 = new ArrayList<String>();
		adjacents2.add("usa1");
		String continent2 = "usa";
		
		String territoryName3 = "usa3";
		Integer X3 = 900;
		Integer Y3 = 900;
		ArrayList<String> adjacents3 = new ArrayList<String>();
		adjacents2.add("usa2");
		String continent3 = "canada";
		
		ITerritory terr = new Territory(territoryName, X, Y, continent1, adjacents);
		ITerritory terr2 = new Territory(territoryName2, X2, Y2, continent2, adjacents2);
		ITerritory terr3 = new Territory(territoryName3, X3, Y3, continent3, adjacents3);
		
		mapLoaderObj = new MapLoader(path + mapName, 0);
		loadedMapObj = mapLoaderObj.getLoadedMap();
		loadedMapObj.setAuthor(authorName);
		loadedMapObj.addContinent(continent);
		loadedMapObj.addContinent(two_continent);
		loadedMapObj.addTerritory(terr);
		loadedMapObj.addTerritory(terr2);
		loadedMapObj.addTerritory(terr3);
		try {
			File file = new File(path + mapName); 
	        file.delete();
			loadedMapObj.saveMapToFile(path + mapName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Before
	public void setup() {
		initDummyMap();
	}
	
	@Test
	public void testCreateMap() {
		String authorName = "test";
		
		String continentName = "usa";
		Integer continentControlValue = 40;
		IContinent continent = new Continent();
		continent.setContinentName(continentName);
		continent.setControlValue(continentControlValue);
		
		String territoryName = "usa1";
		Integer X = 900;
		Integer Y = 900;
		ArrayList<String> adjacents = new ArrayList<String>();
		adjacents.add("usa2");
		String continent1 = "usa";
		
		String territoryName2 = "usa2";
		Integer X2 = 900;
		Integer Y2 = 900;
		ArrayList<String> adjacents2 = new ArrayList<String>();
		adjacents2.add("usa1");
		String continent2 = "usa";
		
		
		ITerritory terr = new Territory(territoryName, X, Y, continent1, adjacents);
		ITerritory terr2 = new Territory(territoryName2, X2, Y2, continent2, adjacents2);
		
		
		mapLoaderObj = new MapLoader(path + mapName, 0);
		loadedMapObj = mapLoaderObj.getLoadedMap();
		loadedMapObj.setAuthor(authorName);
		loadedMapObj.addContinent(continent);
		loadedMapObj.addTerritory(terr);
		loadedMapObj.addTerritory(terr2);
		try {
			File file = new File(path + mapName); 
	        file.delete();
			loadedMapObj.saveMapToFile(path + mapName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File file = new File(path + mapName);
		assertTrue(file.exists());
	}
	
	@Test
	public void testReadMap() {
		
		this.mapLoaderObj = new MapLoader(path + mapName, 1);
		this.loadedMapObj = mapLoaderObj.getLoadedMap();
		
		ArrayList<String> listOfContinents = loadedMapObj.getListOfContinents();
		ArrayList<String> listOfTerritories = loadedMapObj.getListOfTerritories();
		String mapAuthor = loadedMapObj.getAuthor();
		
		assertNotNull(listOfContinents);
		assertNotNull(listOfTerritories);
		assertNotNull(mapAuthor);
		assertTrue(listOfContinents.size() > 0);
		assertTrue(listOfTerritories.size() > 1);
	}
	
	@Test
	public void testChangeAuthor() {
		mapLoaderObj = new MapLoader(path + mapName, 0);
		loadedMapObj = mapLoaderObj.getLoadedMap();
		String authorName = "testAuthor";
		loadedMapObj.setAuthor(authorName);
		assertTrue(loadedMapObj.getAuthor().equals(authorName));
	}
	
	@Test
	public void testAddContinent() {
		mapLoaderObj = new MapLoader(path + mapName, 0);
		loadedMapObj = mapLoaderObj.getLoadedMap();
		String continentName = "canada";
		Integer continentControlValue = 4;
		IContinent continent = new Continent();
		continent.setContinentName(continentName);
		continent.setControlValue(continentControlValue);
		loadedMapObj.addContinent(continent);
		assertNotNull(loadedMapObj.getContinent(continentName));
		assertTrue(loadedMapObj.getContinent(continentName).getControlValue() == continentControlValue);
	}
	
	@Test
	public void testAddTerritory() {
		mapLoaderObj = new MapLoader(path + mapName, 1);
		loadedMapObj = mapLoaderObj.getLoadedMap();
		String territoryName = "terr1";
		Integer X = 900;
		Integer Y = 900;
		ArrayList<String> adjacents = new ArrayList<String>();
		adjacents.add("terr2, usa1");
		String continent = "usa";
		
		String territoryName2 = "terr2";
		Integer X2 = 900;
		Integer Y2 = 900;
		ArrayList<String> adjacents2 = new ArrayList<String>();
		adjacents2.add("terr1");
		String continent2 = "usa";
		
		
		ITerritory terr = new Territory(territoryName, X, Y, continent, adjacents);
		ITerritory terr2 = new Territory(territoryName2, X2, Y2, continent2, adjacents2);
		loadedMapObj.addTerritory(terr);
		loadedMapObj.addTerritory(terr2);
		
		assertNotNull(loadedMapObj.getTerritory(territoryName));
		assertNotNull(loadedMapObj.getTerritory(territoryName2));
	}
	
	@Test
	public void testSaveMapToFile() {
		String authorForTestSave = "TestSaveMap";
		mapLoaderObj = new MapLoader(path + mapName, 1);
		loadedMapObj = mapLoaderObj.getLoadedMap();
		loadedMapObj.setAuthor(authorForTestSave);
		try {
			File file = new File(path + mapName); 
	        file.delete();
			loadedMapObj.saveMapToFile(path + mapName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mapLoaderObj = new MapLoader(path + mapName, 1);
		loadedMapObj = mapLoaderObj.getLoadedMap();
		assertTrue(loadedMapObj.getAuthor().equals(authorForTestSave.toLowerCase()) == true);
	}
	

	@Test
	public void testRemoveContinent() {
		IContinent continent = loadedMapObj.getContinent("canada");
		loadedMapObj.deleteContinent(continent);
		assertTrue(loadedMapObj.getListOfContinents().contains("canada") == false);
	}
	
	@Test
	public void testRemoveTerritory() {
		ITerritory territory = loadedMapObj.getTerritory("usa3");
		loadedMapObj.deleteTerritory(territory);
		assertTrue(loadedMapObj.getListOfTerritories().contains("usa3") == false);
	}
	
	@Test
	public void testFalseMapNoContinent() {
		IContinent continent = loadedMapObj.getContinent("canada");
		loadedMapObj.deleteContinent(continent);
		Verification verificationObj = new Verification();
		verificationObj.map = loadedMapObj;
		assertTrue(verificationObj.checkContinentExistence() == false);
	}
	
	@Test
	public void testFalseMapEmptyContinent() {
		ITerritory territory = loadedMapObj.getTerritory("usa3");
		loadedMapObj.deleteTerritory(territory);
		Verification verificationObj = new Verification();
		verificationObj.map = loadedMapObj;
		assertTrue(verificationObj.checkEmptyContinents() == false);
	}
	
	@Test
	public void testFalseMapAdjacency() {
		ITerritory territory = loadedMapObj.getTerritory("usa2");
		territory.removeAdjacent("usa1");
		Verification verificationObj = new Verification();
		verificationObj.map = loadedMapObj;
		assertTrue(verificationObj.checkTerritoryAdjacencyRelation() == false);
	}
	
	@Test
	public void testFalseMapConnectivity() {
		ITerritory territory = loadedMapObj.getTerritory("usa2");
		loadedMapObj.deleteTerritory(territory);
		Verification verificationObj = new Verification();
		verificationObj.map = loadedMapObj;
		assertTrue(verificationObj.checkTerritoryConnectivity() == false);
	}
}
