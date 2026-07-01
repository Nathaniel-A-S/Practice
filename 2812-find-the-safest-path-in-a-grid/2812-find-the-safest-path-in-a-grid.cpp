class Solution {
    // Four possible movement directions:
    // Down, Right, Up, Left
    static constexpr int dirs[4][2] = {
        {1, 0},
        {0, 1},
        {-1, 0},
        {0, -1}
    };

public:
    int maximumSafenessFactor(vector<vector<int>>& grid) {

        // If the start or end cell contains a thief,
        // the safeness factor is automatically 0.
        if (grid[0][0] || grid.back().back())
            return 0;

        int n = grid.size();

        // Step 1: Multi-Source BFS
        // Compute the distance from every cell to its nearest thief.

        queue<pair<int, int>> q;

        // Push every thief into the queue.
        // Thief cells already contain value 1.
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1)
                    q.push({i, j});
            }
        }

        // Perform BFS simultaneously from every thief.
        while (!q.empty()) {

            auto [i, j] = q.front();
            q.pop();

            // Current cell value.
            // It represents (distance from nearest thief + 1).
            int value = grid[i][j];

            for (auto& d : dirs) {

                int x = i + d[0];
                int y = j + d[1];

                // Skip cells outside the grid.
                if (min(x, y) < 0 || max(x, y) >= n)
                    continue;

                // Only visit cells that haven't been assigned
                // a distance yet (currently equal to 0).
                if (grid[x][y] == 0) {

                    // Neighbor is one level farther away.
                    grid[x][y] = value + 1;

                    q.push({x, y});
                }
            }
        }

       
        // Step 2: Maximum Bottleneck Path
        // We want the path whose minimum safety value is as large as possible.
       

        // (current bottleneck safety, row, col)
        priority_queue<tuple<int, int, int>> pq;

        // Start from the top-left corner.
        pq.push({grid[0][0], 0, 0});

        // Mark the starting cell as visited.
        grid[0][0] *= -1;

        while (!pq.empty()) {

            auto [safeFactor, i, j] = pq.top();
            pq.pop();

            // Once the destination is removed from the max heap,
            // we've found the best possible path.
            if (i == n - 1 && j == n - 1)
                return safeFactor - 1; // Convert back to actual distance.

            for (auto& d : dirs) {

                int x = i + d[0];
                int y = j + d[1];

                // Skip cells outside the grid.
                if (min(x, y) < 0 || max(x, y) >= n)
                    continue;

                // Positive values mean "not visited".
                if (grid[x][y] > 0) {

                    // The bottleneck safety along this new path
                    // is the smaller of:
                    // 1. current bottleneck
                    // 2. neighbor's safety value
                    pq.push({
                        min(safeFactor, grid[x][y]),
                        x,
                        y
                    });

                    // Mark as visited.
                    grid[x][y] *= -1;
                }
            }
        }

        // This line should never be reached because a path always exists.
        return 0;
    }
};