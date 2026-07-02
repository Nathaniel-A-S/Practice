class Solution {

    public boolean findSafeWalk(List<List<Integer>> grid, int health) {

        int m = grid.size();          // Number of rows
        int n = grid.get(0).size();   // Number of columns

        // dis[i][j] stores the minimum health lost to reach cell (i, j).
        // -1 means the cell hasn't been finalized yet.
        int[][] dis = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(dis[i], -1);
        }

        // Four possible movement directions:
        // Right, Down, Up, Left
        int[][] dirs = {
            {0, 1},
            {1, 0},
            {-1, 0},
            {0, -1}
        };

        // Min-heap (Priority Queue)
        // Each element is:
        // [totalHealthLost, row, column]
        PriorityQueue<int[]> pq = new PriorityQueue<>(
            Comparator.comparingInt(a -> a[0])
        );

        // Start from the top-left cell.
        // If grid[0][0] is unsafe (1), we immediately lose 1 health.
        pq.offer(new int[]{
            grid.get(0).get(0),
            0,
            0
        });

        // Dijkstra's Algorithm
        while (!pq.isEmpty()) {

            int[] cur = pq.poll();

            int cost = cur[0];   // Total health lost so far
            int row = cur[1];
            int col = cur[2];

            // If we've already found the minimum cost for this cell,
            // skip this duplicate entry.
            if (dis[row][col] != -1) {
                continue;
            }

            // Finalize the minimum health loss for this cell.
            dis[row][col] = cost;

            // Explore all four neighboring cells.
            for (int[] d : dirs) {

                int nr = row + d[0];
                int nc = col + d[1];

                // Ignore cells outside the grid.
                if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                    continue;
                }

                // Skip cells that already have their minimum cost finalized.
                if (dis[nr][nc] != -1) {
                    continue;
                }

                // Moving into a safe cell (0) adds 0 health loss.
                // Moving into an unsafe cell (1) adds 1 health loss.
                int newCost = cost + grid.get(nr).get(nc);

                pq.offer(new int[]{
                    newCost,
                    nr,
                    nc
                });
            }
        }

        // We survive only if the total health lost is strictly less than the available health.
        // Example:
        // health = 3
        // health lost = 2
        // remaining health = 1 -> survive
        // health = 2
        // health lost = 2
        // remaining health = 0 -> fail
        return dis[m - 1][n - 1] < health;
    }
}