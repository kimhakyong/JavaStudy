package net.jackbauer.enumtype;

import lombok.Getter;

/**
 * https://woowabros.github.io/tools/2017/07/10/java-enum-uses.html
 */
@Getter
public enum TableStatus {
	Y("1", true), 
	N("0", false);

	private final String table1Value;
	private final boolean table2Value;
	
	private TableStatus(String table1Value, boolean table2Value) {
		this.table1Value = table1Value;
		this.table2Value = table2Value;
	}
}
