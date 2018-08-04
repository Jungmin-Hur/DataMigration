package main.common.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum RelationType {
	ManyToOne("ManyToOne","N:1"),
	OneToMany("OneToMany","1:N"),
	ManyToMnay("ManyToMany","N:N"),
	OneToOne("OneToOne","1:1"),
	NoneToOne("NoneToOne","0:1"),
	OneToNone("OneToNone","1:0");
	
	private String name;
	private String desc;
	
	RelationType(String name, String desc){
		this.name = name;
		this.desc = desc;
	}
	
	private static final Map<String, String> relationTypes = new HashMap<>();
	
	static {
		for(RelationType r: EnumSet.allOf(RelationType.class)) {
			relationTypes.put(r.name, r.desc);
		}
	}
	
	public static RelationType get(String name) {
		return RelationType.get(name);
	}
}
