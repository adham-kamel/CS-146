package sjsu.kamel.cs146.project1;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * This class makes several test cases from the PrisonerGame class to see
 * if prisoners were rounded up and eliminated correctly
 * 
 * @author adham_kamel
 *
 */
public class PrisonerGameTest {
	
	PrisonerGame prisoners; // instance of the class I made PrisonGame
	
	/**
	 * Creates the instance of the prison game
	 */
	@Before
	public void setUp(){
		prisoners = new PrisonerGame();
	}
	
	/**
	 * Tests for elimination of a lineup of 6 prisoners every 2 prisoners
	 */
	@Test
	public void test(){
		prisoners.roundEmUp(6);
		assertEquals(1,prisoners.eliminatePrisoner(2));
	}
	
	/**
	 * Tests for elimination of a lineup of 1 prisoner every 9 prisoners
	 */
	@Test
	public void test2(){
		prisoners.roundEmUp(1);
		assertEquals(1,prisoners.eliminatePrisoner(9));
	}
	
	/**
	 * Tests for elimination of a lineup of 7 prisoners every 7 prisoners
	 */
	@Test
	public void test3(){
		prisoners.roundEmUp(7);
		assertEquals(4,prisoners.eliminatePrisoner(7));
	}
	
	/**
	 * Tests for elimination of a lineup of 2 prisoners every 8 prisoners
	 */
	@Test
	public void test4(){
		prisoners.roundEmUp(2);
		assertEquals(2,prisoners.eliminatePrisoner(8));
	}
	
	/**
	 * Tests for elimination of a lineup of 5 prisoners every 1 prisoners
	 */
	@Test
	public void test5(){
		prisoners.roundEmUp(5);
		assertEquals(3,prisoners.eliminatePrisoner(1));
	}
}
