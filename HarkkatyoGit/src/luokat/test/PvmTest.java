package luokat.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import luokat.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2021.03.25 13:12:29 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class PvmTest {



  // Generated by ComTest BEGIN
  /** testRekisteroi44 */
  @Test
  public void testRekisteroi44() {    // Pvm: 44
    Pvm tammi = new Pvm(11,1,2021); 
    Pvm loka = new Pvm(13,10,2021); 
    assertEquals("From: Pvm line: 47", 0, tammi.getTunnusNro()); 
    assertEquals("From: Pvm line: 48", 0, loka.getTunnusNro()); 
    tammi.rekisteroi(); 
    loka.rekisteroi(); 
    assertEquals("From: Pvm line: 51", 2, loka.getTunnusNro()); 
    assertEquals("From: Pvm line: 52", "13.10.2021", loka.toString()); 
  } // Generated by ComTest END
}