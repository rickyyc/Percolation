public class PercolationStats {
    private double[] results;
    private int size;
    
    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        StringBuilder sb = new StringBuilder();
        if (N<=0) {
            sb.append("N must be > 0. ");
        }
        
        if (T<=0) {
            sb.append("T must be > 0. ");
        }
        
        if (sb.length()>0) {
            throw new java.lang.IllegalArgumentException(sb.toString());
        }
        
        size = N;
        
        // run the experiment T times and record the results
        double site_count = size*size;
        results = new double[T];
        for (int i=0; i<T; i++) {
            results[i] = run()/site_count;
        }
    }
    
    /**
     * Performs the experiment once and returns the number of open site.
     */
    private int run() {
        Percolation percolation = new Percolation(size);
        
        int count = 0;
        boolean isPercolated = false;
        
        while (!isPercolated && count++ < (3*size*size)) {
            percolation.open(StdRandom.uniform(size)+1, StdRandom.uniform(size)+1);
            isPercolated = percolation.percolates();
        }
        
        if (!isPercolated) {
            percolation.dump();
            throw new java.lang.IndexOutOfBoundsException("System never percolated after "+(count-1)+" sites are opened.");
        }
        return countOpenSites(percolation);
    }
    
    private int countOpenSites(Percolation percolation) {
        int count = 0;
        for (int i=1; i<=size; i++) {
            for (int j=1; j<=size; j++) {
                if (percolation.isOpen(i, j)) {
                    count++;
                }
            }
        }

        return count;
    }
    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }
    
    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }
    
    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96*stddev()/Math.sqrt(size));
    }
    
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96*stddev()/Math.sqrt(size));
    }
    
    // test client (described below)
    public static void main(String[] args) {
        int N=0;
        int T=0;
        
        if (args.length>0) {
            N = Integer.parseInt(args[0]);
            T = Integer.parseInt(args[1]);
        }
        
        PercolationStats stats = new PercolationStats(N, T);
        System.out.println("mean = "+ stats.mean());
        System.out.println("stddev = "+ stats.stddev());
        System.out.println("95% confidence interval = "+ stats.confidenceLo() + ", "+stats.confidenceHi());
    }
}