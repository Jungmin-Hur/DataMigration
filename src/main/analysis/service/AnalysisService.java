package main.analysis.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import main.analysis.biz.ConvertSchemaBiz;
import main.analysis.biz.ValidationSchemaBiz;
import main.analysis.model.Constants;
import main.analysis.model.SourceInfo;

public class AnalysisService implements IAnalysisService {

	public Map<String,SourceInfo> loadSchemaInfoFromFile(String filename) {
		
		System.out.println("loadSchemaInfoFromFile started!!");
		
		ConvertSchemaBiz convertSchemaBiz = new ConvertSchemaBiz();
		Map<String, SourceInfo> sourceInfoMap = new HashMap<>();

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			int lineCount = 0;
			String line = br.readLine(); // The first line is ignored
			
			while(line != null) {
				if(!line.startsWith(Constants.SHARP)) { // #로 시작하는 경우 읽지 않음(주석처리) 

					String str[] = line.split(Constants.DELIMINATOR);
					
					convertSchemaBiz.isValidLine(lineCount+1, str); //TODO 멈추던지 뭔가 처리 있어야 함, Need more logging
	
					SourceInfo sourceInfo = convertSchemaBiz.makeSchemaInfo(str);
					String mapKey = convertSchemaBiz.makeMapKey(sourceInfo);
					if(convertSchemaBiz.isExistMap(sourceInfoMap, mapKey)) {
						System.out.println("exist duplicat definition");
					}else {
						sourceInfoMap.put(mapKey, sourceInfo);
					}
				}
				lineCount++;
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println((sourceInfoMap.size()) + " sourceInfoMaps are loaded.");
		System.out.println("loadSchemaInfoFromFile finished!!");

		return sourceInfoMap;
	}
	
	public boolean isConvertableBetweenAsisAndTobe(Map<String, SourceInfo> sourceInfoMap) {
		ValidationSchemaBiz validationSchemaBiz = new ValidationSchemaBiz();
		boolean result = true;
		Iterator<Entry<String, SourceInfo>> iter = sourceInfoMap.entrySet().iterator();
		while(iter.hasNext()) {
			Entry<String, SourceInfo> entry = iter.next();
			SourceInfo sourceInfo = entry.getValue();
			
			// N/A 항목은 전환 가능 여부에 대해 판단하지 않음 
			if(Constants.NOT_APPLICABLE.equals(sourceInfo.getColumnType()) 
					|| Constants.NOT_APPLICABLE.equals(sourceInfo.getTargetInfo().getColumnType())) {
				continue;
			}
			
			boolean itemResult = validationSchemaBiz.isAvailableConverting(sourceInfo.getColumnType(), sourceInfo.getTargetInfo().getColumnType());
			if(!itemResult) {
				System.out.println("couldn't converting columns"); //TODO Need more logging
				result = false;
			}
		}
		
		return result; //전체 결과
	}
	
	public boolean validationAsisDefinition(List<SourceInfo> SourceInfoList){
		return true;
	}
	
	public boolean anaysisReport(){
		return true;
	}
}
