class Solution {

    public int findMaxPathScore(int[][] edges, boolean[] online, long k) {

        // Number of nodes in the graph
        int n = online.length;

        // Adjacency list where each edge is stored as {destination, weight}
        List<List<int[]>> g = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            g.add(new ArrayList<>());
        }

        // l = minimum edge weight among valid edges
        // r = maximum edge weight among valid edges
        // These become the binary search range.
        int l = Integer.MAX_VALUE;
        int r = 0;

        // Build the graph using only nodes that are online. If either endpoint is offline, ignore that edge completely.
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];

            if (!online[u] || !online[v]) {
                continue;
            }

            g.get(u).add(new int[]{v, w});

            // Track smallest and largest edge weights.
            l = Math.min(l, w);
            r = Math.max(r, w);
        }

        // If there were no usable edges, no path exists.
        if (l == Integer.MAX_VALUE) {
            return -1;
        }

        // If even allowing every edge cannot reach the destination within the budget k, immediately return -1.
        if (!check(g, l, k, n)) {
            return -1;
        }

        // Binary search for the largest minimum edge weight that still allows a valid path with total cost <= k.
        while (l <= r) {
            int mid = (l + r) >> 1;

            // If a path exists using only edges with weight >= mid, try increasing the minimum allowed edge weight.
            if (check(g, mid, k, n)) {
                l = mid + 1;
            }
            // Otherwise, lower the required minimum edge weight.
            else {
                r = mid - 1;
            }
        }

        // r stores the maximum valid minimum edge weight.
        return r;
    }

    // Returns true if there is a path from node 0 to node n-1 such that:
    // 1. Every edge has weight >= mid.
    // 2. Total path cost is <= k.
    private boolean check(List<List<int[]>> g, int mid, long k, int n) {

        // dis[i] = shortest distance from node 0 to node i.
        long[] dis = new long[n];
        Arrays.fill(dis, Long.MAX_VALUE);

        // Min-heap storing {currentDistance, currentNode}. Dijkstra always processes the closest node first.
        PriorityQueue<long[]> pq =
            new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));

        // Start from node 0 with distance 0.
        dis[0] = 0;
        pq.offer(new long[]{0, 0});

        while (!pq.isEmpty()) {

            long[] cur = pq.poll();

            long d = cur[0];
            int u = (int) cur[1];

            // Ignore outdated entries because a shorter path to this node has already been found.
            if (d > dis[u]) {
                continue;
            }

            // Since distances only increase from here,
            // paths exceeding the budget are useless.
            if (d > k) {
                continue;
            }

            // Destination reached within budget.
            if (u == n - 1) {
                return true;
            }

            // Explore every outgoing edge.
            for (int[] edge : g.get(u)) {

                int v = edge[0];
                int w = edge[1];

                // Ignore edges whose weight is below the
                // minimum weight currently being tested.
                if (w < mid) {
                    continue;
                }

                // New distance if we travel through this edge.
                long nd = d + w;

                // Relaxation step: If this path is shorter than the previously known path, update it.
                if (nd < dis[v]) {
                    dis[v] = nd;
                    pq.offer(new long[]{nd, v});
                }
            }
        }

        // No valid path satisfying all conditions was found.
        return false;
    }
}