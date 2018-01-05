package com.eco.file_parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PersistenceXmlParser {
	
	public static void main(String[] args)  {
		
		File outPersisAdmin = new File(GlobalVars.BASE_PATH + "persisOutputAdmin.txt");
		File outPersisOsrp = new File(GlobalVars.BASE_PATH + "persisOutput.txt");
		
		File inPersisAdmin = new File(GlobalVars.BASE_PATH + "persistenceAdmin.xml");
		File inPersisOsrp = new File(GlobalVars.BASE_PATH + "persistence.xml");
		
		List<String> adminClassList = new ArrayList<>();
		List<String> osrpClassList = new ArrayList<>();
		
		
		
			
		parsePersistence(adminClassList,inPersisAdmin);
		parsePersistence(osrpClassList, inPersisOsrp);
		writeListToFile(adminClassList,outPersisAdmin);
		writeListToFile(osrpClassList, outPersisOsrp);
	
		List<String> compositeList = compareLists(osrpClassList,adminClassList );
		writeListToFile(compositeList,new File(GlobalVars.BASE_PATH + "compositeList.txt"));
	}

	private static <T> void writeListToFile(Collection<T> classList, File file ) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
			for (T string : classList) {
				writer.write(string.toString());
				writer.newLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static List<String> parsePersistence(List<String> classList, File file) {		
		try(BufferedReader br = new BufferedReader(new FileReader(file))){
			String s;
			while((s = br.readLine()) != null){
				if(s.contains("<class>")){
					String temp2 = s.substring(s.lastIndexOf(".")+1, s.indexOf("/")-1);
					temp2 = temp2 + ".java";
					classList.add(temp2);
				}
				
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		classList.sort((a,b) -> a.compareTo(b));
		return classList;
	}
	
	
	
	public static <T> List<T> compareLists(List<T> list1, List<T> list2){
		
		Set<T> composite = new HashSet<>();
		List<T> comp = new ArrayList<>();
		for (T t : list1) {
			composite.add(t);
		}
		for (T t : list2) {
			composite.add(t);
		}
		comp.addAll(composite);
		comp.sort((a,b) -> a.toString().compareTo(b.toString()) );
		return comp;
		
	}

}
