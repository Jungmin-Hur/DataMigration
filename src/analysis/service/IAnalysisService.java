package analysis.service;

import java.util.List;
import analysis.model.SchemaInfo;

public interface IAnalysisService {

	/**
	 * ���� Read -> Model Converting
	 * @param filename
	 * @return List<SchemaInfo>
	 */
	public List<SchemaInfo> loadSchemaInfoFromFile(String filename);
	
	/**
	 * as-is, to-be ��Ű�� ����
	 * ����� file write��
	 * @param schemaInfoList
	 * @return
	 */
	public boolean isConvertableBetweenAsisAndTobe(List<SchemaInfo> schemaInfoList);
	
	/**
	 * AS-IS ������ ���� ���� (Ŭ��¡ ������ ���� ����)
	 * �ǵ����Ϳ� Ȯ���ؾ� ��
	 * @return
	 */
	public boolean validationAsisDefinition(List<SchemaInfo> schemaInfoList);

	/**
	 * file read�Ͽ� stdout�� ��� �����ֱ�
	 * @return
	 */
	public boolean anaysisReport();
}
