import junit.framework.TestCase;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class PercolationTest extends TestCase {
        
    public void test2x2() {
        Percolation testPercolation = new Percolation(2);
        // Should not percolates when all are blocked
        assertFalse(testPercolation.percolates());
        
        // Open (1,1)
        testPercolation.open(1,1);
        assertTrue(testPercolation.isOpen(1,1));
        assertTrue(testPercolation.isFull(1,1));
        assertFalse(testPercolation.isOpen(1,2));
        assertFalse(testPercolation.isFull(1,2));
        assertFalse(testPercolation.isOpen(2,1));
        assertFalse(testPercolation.isFull(2,1));
        assertFalse(testPercolation.isOpen(2,2));
        assertFalse(testPercolation.isFull(2,2));
        
        // Should not percolate
        assertFalse(testPercolation.percolates());

        // Open (2,2)
        testPercolation.open(2,2);
        assertTrue(testPercolation.isOpen(2,2));
        assertFalse(testPercolation.isOpen(1,2));
        assertFalse(testPercolation.isFull(1,2));
        assertFalse(testPercolation.isOpen(2,1));
        assertFalse(testPercolation.isFull(2,1));
        

        // Should not percolate
        assertFalse(testPercolation.percolates());
        
        // Open (1,2)
        testPercolation.open(1,2);
        assertTrue(testPercolation.isOpen(1,2));
        
        // Should percolate
        assertTrue(testPercolation.percolates());
        testPercolation.dump();
    }
    
    public void test3x3() {
        Percolation testPercolation = new Percolation(3);
        // Should not percolates when all are blocked
        assertFalse(testPercolation.percolates());
        
        // Open (1,1)
        testPercolation.open(1,1);
        assertTrue(testPercolation.isOpen(1,1));
        assertTrue(testPercolation.isFull(1,1));
        assertFalse(testPercolation.isOpen(1,2));
        assertFalse(testPercolation.isFull(1,2));
        assertFalse(testPercolation.isOpen(1,3));
        assertFalse(testPercolation.isFull(1,3));
        assertFalse(testPercolation.isOpen(2,1));
        assertFalse(testPercolation.isFull(2,1));
        assertFalse(testPercolation.isOpen(2,2));
        assertFalse(testPercolation.isFull(2,2));
        assertFalse(testPercolation.isOpen(2,3));
        assertFalse(testPercolation.isFull(2,3));
        assertFalse(testPercolation.isOpen(3,1));
        assertFalse(testPercolation.isFull(3,1));
        assertFalse(testPercolation.isOpen(3,2));
        assertFalse(testPercolation.isFull(3,2));
        assertFalse(testPercolation.isOpen(3,3));
        assertFalse(testPercolation.isFull(3,3));
        
        // Should not percolate
        assertFalse(testPercolation.percolates());

        // Open (2,2)
        testPercolation.open(2,2);
        testPercolation.open(3,3);
        assertTrue(testPercolation.isOpen(1,1));
        assertTrue(testPercolation.isFull(1,1));
        assertFalse(testPercolation.isOpen(1,2));
        assertFalse(testPercolation.isFull(1,2));
        assertFalse(testPercolation.isOpen(1,2));
        assertFalse(testPercolation.isFull(1,2));
        assertFalse(testPercolation.isOpen(1,3));
        assertFalse(testPercolation.isFull(1,3));
        assertFalse(testPercolation.isOpen(2,1));
        assertFalse(testPercolation.isFull(2,1));
        assertTrue(testPercolation.isOpen(2,2));
        assertFalse(testPercolation.isFull(2,2));
        assertFalse(testPercolation.isOpen(2,3));
        assertFalse(testPercolation.isFull(2,3));
        assertFalse(testPercolation.isOpen(3,1));
        assertFalse(testPercolation.isFull(3,1));
        assertFalse(testPercolation.isOpen(3,2));
        assertFalse(testPercolation.isFull(3,2));
        assertTrue(testPercolation.isOpen(3,3));
        assertFalse(testPercolation.isFull(3,3));
        

        // Should not percolate
        assertFalse(testPercolation.percolates());
        
        testPercolation.open(1,2);
        testPercolation.open(3,2);
        // Should percolate
        assertTrue(testPercolation.percolates());
    }
    
    public void test3x3Backwater() {
        Percolation testPercolation = new Percolation(3);
        // Should not percolates when all are blocked
        assertFalse(testPercolation.percolates());
        
        // Open (1,1)
        testPercolation.open(3,1);
        testPercolation.open(3,2);
        testPercolation.open(3,3);
               
        // Should percolate
        assertTrue(testPercolation.percolates());
        testPercolation.dump();

        // Open sites to potentially allow backwater
        testPercolation.open(1,2);
        testPercolation.open(1,3);
        
        assertTrue(testPercolation.isOpen(1,2));
        assertFalse(testPercolation.isFull(1,2));
        assertTrue(testPercolation.isOpen(1,3));
        assertFalse(testPercolation.isFull(1,3));
    }
}
