package com.javers.books.backend.ejemplos.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

public class CalculadoraTest {
	Calculadora calc;

	
	@BeforeAll
	public static void primero() {
		System.out.println("Primero");
	}
	
	@AfterAll
	public static void ultimo() {
		System.out.println("Ultimo");
	}
	
	@BeforeEach
	public void instanciaObjeto() {
		 calc = new Calculadora();
			System.out.println("BeforeEach");

	}
	
	@AfterEach
	public void despuesTest() {
		System.out.println("  AfterEaach");
	}
	
	@Test
	@DisplayName("Prueba que ocupa assertEquals")
	//@Disabled("Esta prueba no se ejecutarat")
	public void calculadoraAssertEqualsTest() {
 		assertEquals(2, calc.sumar(1, 1));
		assertEquals(3, calc.restar(4, 1));
		assertEquals(9, calc.multiplicar(3, 3));
		assertEquals(2, calc.dividir(4, 2));
		System.out.println("Prueba Equeals");

	}
	
	@Test
	public void calculadoraTrueFalse() {
		assertTrue(calc.sumar(1, 1) == 2);
		assertTrue(calc.restar(4, 1) == 3);
		assertFalse(calc.multiplicar(4, 1) == 3);
		assertFalse(calc.dividir(4, 2) == 1);
		System.out.println("Prueba TrueFalse");
	
	}

}
