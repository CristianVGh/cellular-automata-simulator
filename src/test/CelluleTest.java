package test;


import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Cellule;
import model.Etat;
	

class CelluleTest {
	Cellule c;
	int etat = 0;
	
	@BeforeEach
	void setup() {
		c = new Cellule(etat);
	}
	
	@Test
	void test() {
		assertEquals(c.getEtat(), etat);
	}
	
	@Test
	void testSetEtat() {
		int e = 2;
		c.setEtat(e);
		assertEquals(e, c.getEtat());
		c.setEtat(e+1);
		assertEquals(e+1, c.getEtat());
		c.setEtat(e + 20);
		assertEquals(e+20, c.getEtat());
	}
	

}
