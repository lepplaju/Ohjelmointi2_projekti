package luokat.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import luokat.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2021.03.25 13:49:23 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class LajiTest {



  // Generated by ComTest BEGIN
  /** testRekisteroi31 */
  @Test
  public void testRekisteroi31() {    // Laji: 31
    Laji nyrkkeily = new Laji(); 
    Laji laskettelu = new Laji("lasketteluuuuuuuuuu"); 
    assertEquals("From: Laji line: 34", 0, nyrkkeily.getLajiId()); 
    assertEquals("From: Laji line: 35", 0, laskettelu.getLajiId()); 
    nyrkkeily.rekisteroi(); 
    laskettelu.rekisteroi(); 
    assertEquals("From: Laji line: 38", 1, nyrkkeily.getLajiId()); 
    assertEquals("From: Laji line: 39", 2, laskettelu.getLajiId()); 
    assertEquals("From: Laji line: 40", "lasketteluuuuuuuuuu", laskettelu.toString()); 
  } // Generated by ComTest END
}