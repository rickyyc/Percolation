public class Percolation {
    // each site may be blocked, open+empty, full. 'full' implies open
    private final static int BLOCKED = 1;
    private final static int OPEN = 2;
    private final static int CONNECTED_TOP = 4;
    private final static int CONNECTED_BOTTOM = 8;
    
    private int[][] grid; // the grid of sites.
    private WeightedQuickUnionUF ufObj; // lib used to perform union-find operations
    private int size;
    private int ufSize;
    private boolean isPercolated = false;
    
    private class Point {
        private int x;
        private int y;
        
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public void setX(int x) {
            this.x = x;
        }
        
        public int getX() {
            return this.x;
        }
        
        public void setY(int y) {
            this.y = y;
        }
         
        public int getY() {
            return this.y;
        }
    }
    
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must be > 0");
        }
        
        /**
         * UF object contains two additional points - starting point and end point. Starting point is
         * 0 and connects to first row of the grid. End point is last point and connects to last row of the grid.
         */
        size = N;
        ufSize = size * size;
        ufObj = new WeightedQuickUnionUF(ufSize);
        
        // create N-by-N grid, with all sites blocked
        grid = new int[N][N];
        
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                grid[x][y]=this.BLOCKED;
                if (y == 0) {
                    grid[x][y] = grid[x][y] | this.CONNECTED_TOP;
                }
                else if (y == N-1) {
                    grid[x][y] = grid[x][y] | this.CONNECTED_BOTTOM;
                }
            }
        }
        
    }
    
    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        System.out.println("Opening "+i+","+j);
        
        validate(i, j);
        // convert 1-based row column to 0-based 2D array
        int x = i-1;
        int y = j-1;
        
        if (this.OPEN == (grid[x][y] & this.OPEN)){
            return;
        }
        
        // set it to OPEN
        grid[x][y] = grid[x][y] | this.OPEN;
        grid[x][y] = grid[x][y] & (~this.BLOCKED);
        
        // look around and connect all open neighbouring sites
        int statuses = 0;
        Point current = new Point(x, y);
        
        // combine all the statuses from previous roots of all connected neighbouring sites
        statuses = statuses | scanSite(current, new Point(x, y-1));
        statuses = statuses | scanSite(current, new Point(x, y+1));
        statuses = statuses | scanSite(current, new Point(x-1, y));
        statuses = statuses | scanSite(current, new Point(x+1, y));
        
        // to the new root of the site being opened
        Point currentRoot = ufToGrid(ufObj.find(gridToUf(x, y)));
        grid[currentRoot.x][currentRoot.y] = grid[currentRoot.x][currentRoot.y] | statuses;
        
        if (this.CONNECTED_TOP == (this.CONNECTED_TOP&statuses) && 
            this.CONNECTED_BOTTOM == (this.CONNECTED_BOTTOM&statuses)) {
            this.isPercolated = true;
        }
    }
    
    // scan the grid site and its neighbour p and return combined status of roots if open.
    private int scanSite(Point origin, Point p) {
        // check for cases when origin is on a border and p cannot exist
        if (p.x<0 || p.x>=size)
            return 0;
        if (p.y<0 || p.y>=size)
            return 0;
        
        // if p is not open, then get out
        boolean isPOpen = isOpen(p.x + 1, p.y + 1);
        if (!isPOpen) {
            return 0;
        }
        
        // retrieve the status of p's root
        Point pRoot = ufToGrid(ufObj.find(gridToUf(p.x, p.y)));
        int pRootStatus = grid[pRoot.x][pRoot.y];
        // union origin and p
        ufObj.union(gridToUf(origin.x, origin.y),gridToUf(p.x, p.y));
        // return pRoot status
        return pRootStatus;
    }
    
    
    
    private int gridToUf(int x, int y) {
        return y * size + x;
    }
    
    private Point ufToGrid(int index) {
        int y = index / size;
        int x = index % size;
        return new Point(x, y);
    }
    
    /**
     * This throws IndexOutOfBoundsException if i and j are not valid.d
     */
    private void validate(int i, int j) {
        boolean isInvalid = false;
        StringBuilder sb = new StringBuilder();
        
        if (i < 1 || i > size) {
            sb.append("i must be >= 1 and <= N. ");
            isInvalid = true;
        }
        
        if (j < 1 || j > size) {
            sb.append("j must be >= 1 and <= N. ");
            isInvalid = true;
        }
        
        if (isInvalid) {
            throw new java.lang.IndexOutOfBoundsException(sb.toString());
        }
    }
    
    public boolean isOpen(int i, int j) {    // is site (row i, column j) open?
        validate(i, j);
        int x = i-1;
        int y = j-1;
        return (grid[x][y] & this.OPEN) == this.OPEN;
    }
    
    public boolean isFull(int i, int j) {    // is site (row i, column j) full?
        validate(i, j);
        
        int x = i-1;
        int y = j-1;
        int root = ufObj.find(gridToUf(x, y));
        Point rootSite = ufToGrid(root);
        
        System.out.println("root is "+rootSite.x+", "+rootSite.y+" with status "+grid[rootSite.x][rootSite.y]);
        
        // if the root is connected to top and this is open, then water can reach here
        if ((this.CONNECTED_TOP & grid[rootSite.x][rootSite.y]) == this.CONNECTED_TOP  ) {
            return isOpen(i, j);
        }    
        return false;
    }
    
    public boolean percolates() {            // does the system percolate?
        return isPercolated;
    }
    
    public void dump() {
        System.out.println("Grid dump");
        for (int j = 0; j < size; j++ ) {
            StringBuilder sb = new StringBuilder();
            for (int i=0; i < size; i++) {
                sb.append(grid[i][j]);
                sb.append(' ');
            }
            System.out.println(sb.toString());
        }
    }
    
    public static void main(String[] args) {   // test client (optional)
        Percolation test = new Percolation(2);
        test.open(1,1);
        test.open(2,2);
        test.open(1,2);
        System.out.println(test.percolates()?"True":"False");
        test.dump();
    }
}