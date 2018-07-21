package analysis.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import analysis.biz.ConvertSchemaBiz;
import analysis.biz.ValidationSchemaBiz;
import analysis.model.Constants;
import analysis.model.SourceInfo;

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
				String str[] = line.split(Constants.DELIMINATOR);
				
				convertSchemaBiz.isValidLine(lineCount+1, str); //TODO Exception 추가 필요

				SourceInfo sourceInfo = convertSchemaBiz.makeSchemaInfo(str);
				String mapKey = convertSchemaBiz.makeMapKey(sourceInfo);
				if(convertSchemaBiz.isExistMap(sourceInfoMap, mapKey)) {
					System.out.println("exist duplicat definition");
				}else {
					sourceInfoMap.put(mapKey, sourceInfo);
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
		
		System.out.println((sourceInfoMap.size()-1) + " sourceInfoMaps are loaded.");
		System.out.println("loadSchemaInfoFromFile finished!!");

		return sourceInfoMap;
	}
	
	public boolean isConvertableBetweenAsisAndTobe(List<SourceInfo> SourceInfoList) {
		ValidationSchemaBiz validationSchemaBiz = new ValidationSchemaBiz();
		boolean result = true;
		for(SourceInfo sourceInfo : SourceInfoList) {
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
