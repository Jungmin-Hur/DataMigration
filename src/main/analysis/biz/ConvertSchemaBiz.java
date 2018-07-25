package main.analysis.biz;

import java.util.Map;

import main.analysis.model.Constants;
import main.analysis.model.SourceInfo;
import main.analysis.model.TargetInfo;
import main.common.utils.CommonUtil;

public class ConvertSchemaBiz {

	public boolean isDuplicatedSchemaInfo(Map<String, SourceInfo> sourceInfoMap, String mapKey) {
		boolean isDuplicatedSchemaInfo = false;
		
		if(sourceInfoMap.get(mapKey) != null) {
			String str[] = mapKey.split("\\" + Constants.POINT);
			if(str.length == 4) {
				System.out.println("Exist Duplicated Schema Info Input. Source (" + str[0] + "/" + str[1] + ") Target (" + str[2] + "/" + str[3] + ")");
			} else {
				System.out.println("Exist Duplicated Schema Info Input.");
			}
			isDuplicatedSchemaInfo = true;
		}

		return isDuplicatedSchemaInfo;
	}

	public String makeMapKey(SourceInfo sourceInfo) {
		StringBuffer sb = new StringBuffer();
		sb.append(sourceInfo.getTableName()).append(Constants.POINT)
			.append(sourceInfo.getColumnName()).append(Constants.POINT)
			.append(sourceInfo.getTargetInfo().getTableName()).append(Constants.POINT)
			.append(sourceInfo.getTargetInfo().getColumnName()).append(Constants.POINT);
		
		return sb.toString();
	}
	
	public SourceInfo makeSchemaInfo(String data[]){
		SourceInfo sourceInfo = new SourceInfo();
		TargetInfo targetInfo = new TargetInfo();
		
		sourceInfo.setId(CommonUtil.generateUniqueId());
		sourceInfo.setTableName(data[0]);
		sourceInfo.setColumnName(data[1]);
		sourceInfo.setColumnType(data[2]);
		sourceInfo.setColumnSize(data[3]);
		sourceInfo.setValidationQuery(data[4]);
		sourceInfo.setTargetInfo(targetInfo);

		targetInfo.setId(CommonUtil.generateUniqueId());
		targetInfo.setTableName(data[5]);
		targetInfo.setColumnName(data[6]);
		targetInfo.setColumnType(data[7]);
		targetInfo.setColumnSize(data[8]);
		targetInfo.setValidationQuery(data[9]);
		targetInfo.setMappingDefinition(data[10]);
		targetInfo.setMappingLimitation(data[11]);
		targetInfo.setSourceInfo(sourceInfo);
		
		isValidSchemaInfo(sourceInfo);

		return sourceInfo;
	}
	
	private boolean isValidSchemaInfo(SourceInfo sourceInfo) {
		boolean result = true;
		
		if(!Constants.NOT_APPLICABLE.equals(sourceInfo.getColumnSize()) && 
				!CommonUtil.isStringNumber(sourceInfo.getColumnSize())) {
			System.out.println("입력된 ColumnSize데이터에 숫자가 아닌 데이터가 존재합니다.");
			result = false;
		} 
		if(!Constants.NOT_APPLICABLE.equals(sourceInfo.getTargetInfo().getColumnSize()) && 
				!CommonUtil.isStringNumber(sourceInfo.getTargetInfo().getColumnSize())) {
			System.out.println("입력된 ColumnSize데이터에 숫자가 아닌 데이터가 존재합니다.");
			result = false;
		} 
		return result;
	}
}
