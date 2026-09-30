function maxDepthAfterSplit(seq: string): number[] {
    const n = seq.length;

    let maxDepth = 0;
    let depth = 0;
    for (const c of seq) {
        if (c === ')') {
            depth--;
            continue;
        }
        depth++;
        if (depth > maxDepth) maxDepth = depth;
    }

    const r: number[] = new Array(n).fill(0);
    const half = maxDepth >> 1;

    depth = 0;
    for (let i = 0; i < n; i++) {
        const c = seq[i];
        if (c === ')') {
            // Close A first if A has open
            if (depth > 0) {
                depth--;
                continue;
            }
            r[i] = 1;
            continue;
        }
        // A is full, rest goes to B
        if (depth >= half) {
            r[i] = 1;
            continue;
        }
        depth++;
    }
    return r;
};