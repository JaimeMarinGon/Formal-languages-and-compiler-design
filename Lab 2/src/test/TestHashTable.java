package test;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import model.HashTable;

public class TestHashTable {

	@Test
	public void test1() {
		HashTable tabla = new HashTable(13);
		System.out.println(tabla.toString());
		
		assertEquals("-,-,-,-,-,-,-,-,-,-,-,-,- [Size: 13 Num.Elems 0]",tabla.toString());
		
		// Insert null
		assertEquals(-2,tabla.insert(null));
		
		// Insert elements
		assertEquals(0,tabla.insert("92"));
		assertEquals(0,tabla.insert("j"));
		System.out.println(tabla.toString());
		assertEquals("-,-,j,-,-,-,-,-,-,-,92,-,- [Size: 13 Num.Elems 2]",tabla.toString());
		
		assertEquals(0,tabla.insert("#"));
		System.out.println(tabla.toString());
		assertEquals("-,-,j,-,-,-,-,-,-,#,92,-,- [Size: 13 Num.Elems 3]",tabla.toString());
		
		// Insert elements with collisions
		assertEquals(0,tabla.insert("91"));
		System.out.println(tabla.toString());	
		assertEquals("-,-,j,-,-,-,-,-,-,#,92,91,- [Size: 13 Num.Elems 4]",tabla.toString());
		assertEquals(0,tabla.insert("93"));
		System.out.println(tabla.toString());	
		assertEquals("-,-,j,-,-,-,-,-,-,#,92,91,93 [Size: 13 Num.Elems 5]",tabla.toString());	
		// Insert elements
		assertEquals(0,tabla.insert("1"));
		assertEquals(0,tabla.insert("12"));
		assertEquals(0,tabla.insert("13"));
		assertEquals(0,tabla.insert("76"));
		assertEquals(0,tabla.insert("990"));
		assertEquals(0,tabla.insert("15"));
		assertEquals(0,tabla.insert("2"));
		assertEquals(0,tabla.insert("84"));
		System.out.println(tabla.toString());
		assertEquals("1,12,j,13,76,990,15,2,84,#,92,91,93 [Size: 13 Num.Elems 13]",tabla.toString());
	

	}
	

}

