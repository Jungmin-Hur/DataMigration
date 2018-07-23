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
		sourceInfo.setValidationQuery(data[3]);
		sourceInfo.setTargetInfo(targetInfo);

		targetInfo.setId(CommonUtil.generateUniqueId());
		targetInfo.setTableName(data[4]);
		targetInfo.setColumnName(data[5]);
		targetInfo.setColumnType(data[6]);
		targetInfo.setValidationQuery(data[7]);
		targetInfo.setMappingDefinition(data[8]);
		targetInfo.setMappingLimitation(data[9]);
		targetInfo.setSourceInfo(sourceInfo);
		
		return sourceInfo;
	}
}
