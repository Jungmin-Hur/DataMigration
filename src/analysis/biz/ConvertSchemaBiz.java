package analysis.biz;

import java.util.Map;

import analysis.model.SourceInfo;
import analysis.model.TargetInfo;
import utils.CommonUtil;

public class ConvertSchemaBiz {

	public final String POINT = ".";

	//TODO data[] 구조 개선 필요..
	public boolean isValidLine(int lineNum, String data[]) {
		if(data.length != 8) { // 빈줄은 data.length가 1로 들어있어서 여기서 걸림
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
		sb.append(sourceInfo.getTableName()).append(POINT)
			.append(sourceInfo.getColumnName()).append(POINT)
			.append(sourceInfo.getTargetInfo().getTableName()).append(POINT)
			.append(sourceInfo.getTargetInfo().getColumnName()).append(POINT);
		return sb.toString();
	}
	
	public SourceInfo makeSchemaInfo(String data[]){
		SourceInfo sourceInfo = new SourceInfo();
		sourceInfo.setId(CommonUtil.generateUniqueId());
		sourceInfo.setTableName(data[0]);
		sourceInfo.setColumnName(data[1]);
		sourceInfo.setValidationQuery(data[2]);
		
		TargetInfo targetInfo = new TargetInfo();
		targetInfo.setId(CommonUtil.generateUniqueId());
		targetInfo.setTableName(data[3]);
		targetInfo.setColumnName(data[4]);
		targetInfo.setValidationQuery(data[5]);
		targetInfo.setMappingDefinition(data[6]);
		targetInfo.setMappingLimitation(data[7]);
		
		sourceInfo.setTargetInfo(targetInfo);
		targetInfo.setSourceInfo(sourceInfo);
		
		return sourceInfo;
	}
}
