package main.analysis.biz;

import java.util.Map;

import main.analysis.model.Constants;
import main.analysis.model.SourceInfo;
import main.analysis.model.TargetInfo;
import main.utils.CommonUtil;

public class ConvertSchemaBiz {

	//TODO data[] 구조 개선 필요..
	public boolean isValidLine(int lineNum, String data[]) {
		if(data.length != 10) { // 빈줄은 data.length가 1로 들어있어서 여기서 걸림
			System.out.println("line:" + lineNum + "; invalid data format.; data length:" + data.length);
			return false;
		}
		return true;
	}
	
	//TODO Template으로 나중에 변경할 것...
	public boolean isExistMap(Map<String, SourceInfo> sourceInfoMap, String mapKey) {
		return sourceInfoMap.get(mapKey) == null ? false : true;
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
		sourceInfo.setId(CommonUtil.generateUniqueId());
		sourceInfo.setTableName(data[0]);
		sourceInfo.setColumnName(data[1]);
		sourceInfo.setColumnType(data[2]);
		sourceInfo.setValidationQuery(data[3]);
		
		TargetInfo targetInfo = new TargetInfo();
		targetInfo.setId(CommonUtil.generateUniqueId());
		targetInfo.setTableName(data[4]);
		targetInfo.setColumnName(data[5]);
		targetInfo.setColumnType(data[6]);
		targetInfo.setValidationQuery(data[7]);
		targetInfo.setMappingDefinition(data[8]);
		targetInfo.setMappingLimitation(data[9]);
		
		sourceInfo.setTargetInfo(targetInfo);
		targetInfo.setSourceInfo(sourceInfo);
		
		return sourceInfo;
	}
}
