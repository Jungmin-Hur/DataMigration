package analysis.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import analysis.model.SchemaInfo;

public class AnalysisService implements IAnalysisService {
	
	public List<SchemaInfo> loadSchemaInfoFromFile(String filename) {
		System.out.println("loadSchemaInfoFromFile started!!");

		List<SchemaInfo> schemaInfoList = new ArrayList<>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			List<SchemaInfo> list = makeSchemaInfo(""); //TODO file read ���� �߰�
			schemaInfoList.addAll(list);
			in.close();
			System.out.println("file read finish");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(schemaInfoList.size() + "schemaInfoList are loaded.");
		System.out.println("loadSchemaInfoFromFile finished!!");
		
		return schemaInfoList;
	}
	
	private List<SchemaInfo> makeSchemaInfo(String data){
		//TODO
		//split data
		//generate key 2�� : UniqueIdUtil �Ἥ...
		//mapping schemainfo 2�� model (�� ��ü�� ���� key���� �����ϱ�)
		return null;
	}
	
	public boolean isConvertableBetweenAsisAndTobe(List<SchemaInfo> schemaInfoList) {
		return true;
	}
	
	public boolean validationAsisDefinition(List<SchemaInfo> schemaInfoList){
		return true;
	}

	public boolean anaysisReport(){
		return true;
	}
}
