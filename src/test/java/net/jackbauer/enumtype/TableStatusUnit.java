package net.jackbauer.enumtype;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TableStatusUnit {

	@Test
	public void originValueConvert() {
		TableStatus origin = TableStatus.Y;
		
		String table1Value = origin.getTable1Value();
		boolean table2Value = origin.isTable2Value();
		
		assertEquals(origin, TableStatus.Y);
		assertEquals(table1Value, "1");
		assertEquals(table2Value, true);
	}

}
